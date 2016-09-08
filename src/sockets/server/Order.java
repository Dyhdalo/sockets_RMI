package sockets.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable{

	private int idOfOrder;
	private String userName;
	private String bookAuthor;
	private String bookName;
	
	@SuppressWarnings("unused")
	private Order(){};
	
	public Order(int idOfOrder, String userName, String bookAuthor, String bookName) {
		this.idOfOrder = idOfOrder;
		this.userName = userName;
		this.bookAuthor = bookAuthor;
		this.bookName = bookName;
	}

	public int getIdOfOrder() {
		return idOfOrder;
	}

	public String getUserName() {
		return userName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public String getBookName() {
		return bookName;
	}
	
	public int getOrderId() {
		return idOfOrder;
	}

	@Override
	public String toString() {
		return userName+" orders book \""+bookName+"\" by "+bookAuthor;
	}
	
}
