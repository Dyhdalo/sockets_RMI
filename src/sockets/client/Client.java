package sockets.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sockets.forms.LibrarianTable;
import sockets.forms.MyLibrarianForm;
import sockets.forms.MyLoginForm;
import sockets.forms.MyUserForm;
import sockets.forms.UserTable;
import sockets.server.Book;
import sockets.server.Order;
import sockets.server.User;

public class Client {

	private static PrintWriter pw; 
	private static ObjectInputStream ois; 
	private static Socket socket;
	private static MyUserForm userForm;
	private static MyLibrarianForm libForm;
	private static MyLoginForm loginForm;
	
	private static int idOfUser;
	private static String roleOfUser;
	private static ArrayList<Book> books;
	private static ArrayList<Book> orders;
	private static ArrayList<User> users;
	private static ArrayList<Order> allOrders;
	
	public static void main(String[] args){
		
		try{
			
			socket = new Socket(args[0], Integer.parseInt(args[1]));
			pw= new PrintWriter(socket.getOutputStream(),true);
			ois = new ObjectInputStream(socket.getInputStream());
			
			loginForm = new MyLoginForm();
			loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			loginForm.setVisible(true);
			loginForm.getButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String login = loginForm.getLoginField().getText();
					String password = loginForm.getPasswordField().getText();
					pw.println("getUsers");
					
					try {
						users = (ArrayList<User>) ois.readObject(); //забираємо всіх користувачів з бд
					
					idOfUser = getUserId(users, login, password); //забираємо порядковий номер користувача з бд
					roleOfUser = getUserRole(users, login, password); //забираємо роль користувача
					
					if(roleOfUser==null) JOptionPane.showMessageDialog(null, "Неправильний логін/пароль!!!", "", JOptionPane.INFORMATION_MESSAGE);
					//роль - студент
					else
					if(roleOfUser.equals("user") && loginForm.getRole().getSelectedItem().equals("user")){
						loginForm.setVisible(false);
						userForm = new MyUserForm(login);
						userForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						userForm.setVisible(true);
						
						//взяти всі книги
						pw.println("getBooks");
						Client.books = (ArrayList<Book>) ois.readObject();
						userForm.getAllBooksTable().setModel(new UserTable(Client.books));
						
						//взяти всі книги, замовлені даним користувачем
					    pw.println("getUserOrders" + idOfUser);
					    Client.orders = (ArrayList<Book>) ois.readObject();
						userForm.getOrderedBooksTable().setModel(new UserTable(Client.orders));
						
						//нажаття кнопки "Зробити замовлення"
						userForm.getMakeOrderButton().addActionListener(new ActionListener() {

							/*зробити замовлення - всі, книги, які вибрані користувачем будуть оформлені
							у вигляді замовлення (яке збережеться в базі даних)
							*/
							@Override
							public void actionPerformed(ActionEvent e) {
								int[] rows = userForm.getAllBooksTable().getSelectedRows();
								
								if (rows.length > 0) {
									String str = "insert" + idOfUser;
									for (int i = 0; i < rows.length; i++) {
										str += " "+ Client.books.get(rows[i]).getId();
									}
									
									pw.println(str);
								}
							
								// після оформлення замовлення потрібно редагувати список книг, що замовлені користувачем
								try {
									Client.orders =  (ArrayList<Book>) ois.readObject();
									userForm.getOrderedBooksTable().setModel(new UserTable(Client.orders));
								} catch (ClassNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						});
						
						//коли користувач вибрав книгу, показати опис цієї книги
						userForm.getAllBooksTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							@Override
							public void valueChanged(ListSelectionEvent e) {
								int selectRow = userForm.getAllBooksTable().getSelectedRow();
								if(selectRow!=-1){
									Book s = Client.books.get(selectRow);
									userForm.getAllBooksTextArea().setText(s.getDescription());
								}
							}	
							
						});
						
						//коли користувач вибрав книгу, показати опис цієї книги
						userForm.getOrderedBooksTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							@Override
							public void valueChanged(ListSelectionEvent e) {
								int selectRow = userForm.getOrderedBooksTable().getSelectedRow();
								if(selectRow!=-1){
									Book s = Client.orders.get(selectRow);
									userForm.getOrderedBooksTextArea().setText(s.getDescription());
								}
							}
						});
						
						// коли змінилися замовлення користувача - оновити їх
						userForm.getOrderedBooksTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							@Override
							public void valueChanged(ListSelectionEvent e) {

								int selectRow = userForm.getOrderedBooksTable().getSelectedRow();
								if(selectRow!=-1){
									Book s = Client.orders.get(selectRow);
									userForm.getOrderedBooksTextArea().setText(s.getDescription());
									}
								}
							});
						//далі - роль бібліотекаря
					}else if(roleOfUser.equals("librarian") && loginForm.getRole().getSelectedItem().equals("librarian")){
						loginForm.setVisible(false);
						libForm = new MyLibrarianForm(login);
						libForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						libForm.setVisible(true);
						
						// взяти всі замовлення
						pw.println("getOrders");
						allOrders = (ArrayList<Order>) ois.readObject();
						libForm.getTable().setModel(new LibrarianTable(allOrders));
						
						//нажаття кнопки "Видалити" - видалення замовлення
						libForm.getDeleteButton().addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								int[] rows = libForm.getTable().getSelectedRows();
								
								if (rows.length > 0) {
									String str = "delete";
									for (int i = 0; i < rows.length; i++) {
										str +=  allOrders.get(rows[i]).getOrderId()+" ";
									}
									
									pw.println(str);
									try {
										allOrders = (ArrayList<Order>) ois.readObject();
										libForm.getTable().setModel(new LibrarianTable(allOrders));
									} catch (ClassNotFoundException e1) {
										e1.printStackTrace();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							}	
					});
					}else{
						JOptionPane.showMessageDialog(null, "Неправильний логін/пароль!!!", "", JOptionPane.INFORMATION_MESSAGE);
					}
					
				    } catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});
			
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static int getUserId(ArrayList<User> users, String login, String password) {
		for (User u : users) {
			if (u.getName().equals(login) && u.getPassword().equals(password))
				return u.getId();
		}
		return -1;
	}
	
	private static String getUserRole(ArrayList<User> users, String login, String password) {
		for (User u : users) {
			if (u.getName().equals(login) && u.getPassword().equals(password))
				return u.getRole();
		}
		return null;
	}
	
}
