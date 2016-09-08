package sockets.forms;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import sockets.server.Book;

@SuppressWarnings("serial")
public class UserTable extends AbstractTableModel{

	private ArrayList<Book> allBooks;
	private String[] columnNames = {"Назва","Автор"};
	
	public UserTable(ArrayList<Book> book) {
		allBooks = book;
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}
	
	@Override
	public int getRowCount() {
		return allBooks.size();
	}
	
	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch (arg1){
		case 0 :
	        return allBooks.get(arg0).getName();
	    case 1 :
	        return allBooks.get(arg0).getAuthor();
		}
		
		return null;
	}
	
	@Override
	public String getColumnName(int column) {
				return columnNames[column];
	}
}
