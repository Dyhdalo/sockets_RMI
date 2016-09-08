import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.naming.*;
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

import java.util.ArrayList;

public class BookClient {

	private static MyUserForm userForm;
	private static MyLibrarianForm libForm;
	private static MyLoginForm loginForm;
	
	private static int idOfUser;
	private static String roleOfUser;
	private static ArrayList<Book> books;
	private static ArrayList<Book> orders;
	private static ArrayList<User> users;
	private static ArrayList<Order> allOrders;
	
	public static final String SERVER_NAME = "Server";
	
	public static void main(String[] args) throws RemoteException, NamingException, MalformedURLException, NotBoundException{
		
		Registry registry = LocateRegistry.getRegistry("localhost", 8888);
		final BookService hello = (BookService) registry.lookup(SERVER_NAME);
		hello.createConnection();
			
			loginForm = new MyLoginForm();
			loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			loginForm.setVisible(true);
			loginForm.getButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String login = loginForm.getLoginField().getText();
					@SuppressWarnings("deprecation")
					String password = loginForm.getPasswordField().getText();
			
					try {
						BookClient.users = hello.getUsers();
					} catch (RemoteException e3) {
						e3.printStackTrace();
					}
					
					idOfUser = getUserId(users, login, password);
					roleOfUser = getUserRole(users, login, password);
					
					if(roleOfUser==null) JOptionPane.showMessageDialog(null, "Неправильний логін/пароль!!!", "", JOptionPane.INFORMATION_MESSAGE);
					
					else
					if(roleOfUser.equals("user") && loginForm.getRole().getSelectedItem().equals("user")){
						loginForm.setVisible(false);
						userForm = new MyUserForm(login);
						userForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						userForm.setVisible(true);
						
						//взяти всі книги
						try {
							BookClient.books = hello.getBooks();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						userForm.getAllBooksTable().setModel(new UserTable(BookClient.books));
						
						//взяти всі книги, замовлені даним користувачем
					    try {
							BookClient.orders = hello.getBooksOfUser(idOfUser);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						userForm.getOrderedBooksTable().setModel(new UserTable(BookClient.orders));
						
						//нажаття кнопки "Зробити замовлення"
						userForm.getMakeOrderButton().addActionListener(new ActionListener() {

							/*зробити замовлення - всі, книги, які вибрані користувачем будуть оформлені
							у вигляді замовлення (яке збережеться в базі даних)
							*/
							@Override
							public void actionPerformed(ActionEvent e) {
								int[] rows = userForm.getAllBooksTable().getSelectedRows();
								
								if (rows.length > 0) {
									String str = ""+idOfUser;
									for (int i = 0; i < rows.length; i++) {
										str += " "+ BookClient.books.get(rows[i]).getId();
									}
									
									try {
										hello.makeOrder(str.split(" "));
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
								}
							
								// після оформлення замовлення потрібно редагувати список книг, що замовлені користувачем
								    try {
										BookClient.orders =  hello.getBooksOfUser(idOfUser);
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
									userForm.getOrderedBooksTable().setModel(new UserTable(BookClient.orders));
							}
						});
						
						//коли користувач вибрав книгу, показати опис цієї книги
						userForm.getAllBooksTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							@Override
							public void valueChanged(ListSelectionEvent e) {
								int selectRow = userForm.getAllBooksTable().getSelectedRow();
								if(selectRow!=-1){
									Book s = BookClient.books.get(selectRow);
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
									Book s = BookClient.orders.get(selectRow);
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
									Book s = BookClient.orders.get(selectRow);
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
						try {
							BookClient.allOrders = hello.getOrders();
						} catch (RemoteException e2) {
							e2.printStackTrace();
						}
						libForm.getTable().setModel(new LibrarianTable(allOrders));
						
						//нажаття кнопки "Видалити" - видалення замовлення
						libForm.getDeleteButton().addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								int[] rows = libForm.getTable().getSelectedRows();
								
								if (rows.length > 0) {
									String str = "";
									for (int i = 0; i < rows.length; i++) {
										str +=  allOrders.get(rows[i]).getOrderId()+" ";
									}
									
									try {
										BookClient.allOrders = hello.deleteOrders(str.split(" "));
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
									libForm.getTable().setModel(new LibrarianTable(allOrders));
								}
							}	
					});
					}else{
						JOptionPane.showMessageDialog(null, "Неправильний логін/пароль!!!", "", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}

			});
		
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
