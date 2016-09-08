package sockets.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MyThread implements Runnable{

	private Socket socket;
	private BufferedReader inStream;
	private ObjectOutputStream outStream;
	
	public MyThread(Socket socket) {

		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		try{
			
		inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outStream = new ObjectOutputStream(socket.getOutputStream());
		
		while (true){
			String input = inStream.readLine();
			
			if (input != null){
				if (input.equals("getBooks")) {
					ArrayList<Book> allb = (ArrayList<Book>) DAO.getBooks();
					outStream.writeObject(allb);
					outStream.flush();
				}else if (input.equals("getUsers")) {
					ArrayList<User> allu = (ArrayList<User>) DAO.getUsers();
					outStream.writeObject(allu);
					outStream.flush();
				}else if (input.equals("getOrders")) {
					ArrayList<Order> allo = (ArrayList<Order>) DAO.getOrders();
					outStream.writeObject(allo);
					outStream.flush();
				}else if (input.substring(0, 6).equals("insert")) {
					String[] indexes = input.substring(6).split(" ");
					DAO.makeOrder(indexes);
					ArrayList<Book> allb = (ArrayList<Book>) DAO.getBooksOfUser(Integer.parseInt(indexes[0]));
					outStream.writeObject(allb);
					outStream.flush();
				}else if(input.substring(0, 6).equals("delete")){
					String[] words = input.substring(6).split(" ");
					ArrayList<Order> allO = DAO.deleteOrders(words);
					outStream.writeObject(allO);
					outStream.flush();
				}else if (input.substring(0, 13).equals("getUserOrders")) {
					String[] indexes = input.substring(13).split(" ");
					ArrayList<Book> alb = (ArrayList<Book>) DAO.getBooksOfUser(Integer.parseInt(indexes[0]));
					outStream.writeObject(alb);
					outStream.flush();
				}
			}
		}
		
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
