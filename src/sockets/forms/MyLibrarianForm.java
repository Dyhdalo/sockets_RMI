package sockets.forms;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class MyLibrarianForm extends JFrame{

	private JPanel mainPanel;
	private JTable allOrdersTable;
	private JScrollPane allOrdersPane;
	private JButton delete;
	
	public MyLibrarianForm(String user){
		this.setSize(950,500);
		this.setTitle("Сторінка бібліотекаря: "+user);
		
		allOrdersTable = new JTable();
		allOrdersTable.setPreferredScrollableViewportSize(new Dimension(700,100));
		allOrdersTable.setSize(800, 300);
		
		delete = new JButton("Видалити");

		allOrdersPane = new JScrollPane(allOrdersTable);
		allOrdersPane.setPreferredSize(new Dimension(700,200));
		allOrdersPane.setSize(800, 300);
		
		mainPanel = new JPanel();
		mainPanel.add(new JLabel("Всі замовлення"));
		mainPanel.add(allOrdersPane);
		mainPanel.add(delete);
		
		this.add(mainPanel);
	}
	
	public JTable getTable(){
		return this.allOrdersTable;
	}
	
	public JButton getDeleteButton(){
		return this.delete;
	}
	
}
