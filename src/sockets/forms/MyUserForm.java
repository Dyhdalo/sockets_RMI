package sockets.forms;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class MyUserForm extends JFrame{

	private JPanel allBooksPanel;
	private JPanel orderedBooksPanel;
	private JTable allBooksTable;
	private JTable orderedBooksTable;
	private JScrollPane allBooksPane;
	private JScrollPane orderedBooksPane;
	private JButton makeOrder;
	private JTabbedPane tabbedPane;
	private JTextArea allBooksTextArea;
	private JTextArea orderedBooksTextArea;
	
	public MyUserForm(String user){
		this.setSize(950,500);
		this.setTitle("Сторінка користувача: "+user);
		
		allBooks();
		orderedBooks();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setSize(800,300);
		tabbedPane.addTab("Всі книги",allBooksPanel);
		tabbedPane.addTab("Мої замовлення",orderedBooksPanel);
		
		this.add(tabbedPane);
		
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				allBooksTable.clearSelection();
				orderedBooksTable.clearSelection();
				}
			});
	}

	private void orderedBooks() {
		
		allBooksTable = new JTable();
		allBooksTable.setPreferredScrollableViewportSize(new Dimension(700,100));
		allBooksTable.setSize(800, 300);
		
		allBooksTextArea = new JTextArea(15,80);
		allBooksTextArea.setEditable(false);
		//allBooksTextArea.setSize(750, 300);
		
		allBooksPane = new JScrollPane(allBooksTable);
		allBooksPane.setPreferredSize(new Dimension(700,100));
		allBooksPane.setSize(800, 300);
		
		makeOrder = new JButton("Зробити замовлення");
		
		allBooksPanel = new JPanel();
		allBooksPanel.setSize(800,400);
		allBooksPanel.add(allBooksPane);
		allBooksPanel.add(allBooksTextArea);
		allBooksPanel.add(makeOrder);
	}

	private void allBooks() {
		
		orderedBooksTable = new JTable();
		orderedBooksTable.setPreferredScrollableViewportSize(new Dimension(700,100));
		orderedBooksTable.setSize(800, 300);
		
		orderedBooksTextArea = new JTextArea(15,80);
		orderedBooksTextArea.setEditable(false);
		
		orderedBooksPane = new JScrollPane(orderedBooksTable);
		orderedBooksPane.setPreferredSize(new Dimension(700,100));
		orderedBooksPane.setSize(800, 300);
		
		orderedBooksPanel = new JPanel();
		orderedBooksPanel.add(orderedBooksPane);
		orderedBooksPanel.add(orderedBooksTextArea);
	}
	
	public JTable getAllBooksTable(){
		return this.allBooksTable;
	}
	
	public JTable getOrderedBooksTable(){
		return this.orderedBooksTable;
	}
	
	public JButton getMakeOrderButton(){
		return this.makeOrder;
	}
	public JTextArea getAllBooksTextArea(){
		return this.allBooksTextArea;
	}
	public JTextArea getOrderedBooksTextArea(){
		return this.orderedBooksTextArea;
	}
	
}
