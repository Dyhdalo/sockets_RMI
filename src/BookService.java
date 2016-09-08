import java.util.ArrayList;
import java.rmi.*;

import sockets.server.Book;
import sockets.server.Order;
import sockets.server.User;

public interface BookService extends Remote{
	
	final static String derbyProtocol = "jdbc:derby://localhost:1527/";
	final static String dbName = "sample";
	final static String jdbcURL = derbyProtocol + dbName;
	final static String derbyDriver = "C:\\Program Files\\Java\\jdk1.7.0_11\\db\\lib\\derby.jar";

	public void createConnection() throws RemoteException;
	
	public ArrayList<Book> getBooks() throws RemoteException;
	
	public ArrayList<Book> getBooksOfUser(int idOfUser) throws RemoteException;
	
	public ArrayList<User> getUsers() throws RemoteException;
	
	public int getNextOrderId() throws RemoteException;
	
	public void makeOrder(String[] index) throws RemoteException;
	
	public ArrayList<Order> getOrders() throws RemoteException;
	
	public ArrayList<Order> deleteOrders(String[] words) throws RemoteException;
	
}
