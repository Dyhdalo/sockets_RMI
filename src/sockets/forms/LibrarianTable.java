package sockets.forms;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import sockets.server.Order;

@SuppressWarnings("serial")
public class LibrarianTable extends AbstractTableModel{

	private ArrayList<Order> allOrders;
	private String[] columnNames = {"Студент","Назва книги","Автор книги"};
	
	public LibrarianTable(ArrayList<Order> order) {
		allOrders = order;
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}
	
	@Override
	public int getRowCount() {
		return allOrders.size();
	}
	
	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch(arg1) {
		case 0 :
	        return allOrders.get(arg0).getUserName();
		case 1 :
	        return allOrders.get(arg0).getBookName();
	    case 2 :
	        return allOrders.get(arg0).getBookAuthor();	
	    }
		return null;
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
}
}
