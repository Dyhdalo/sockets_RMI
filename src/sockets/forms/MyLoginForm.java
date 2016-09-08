package sockets.forms;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MyLoginForm extends JFrame{

	JPanel panel;
	JTextField loginField;
	JPasswordField passwordField;
	JComboBox role;
	JButton ok;
	final String[] roles = {"librarian", "user"};
	
	public MyLoginForm(){
		
		this.setSize(300, 150);
		this.setLocation(200,200);
		setTitle("јвторизац≥€");
		
		panel = new JPanel(new GridLayout(4, 2));
		loginField = new JTextField(10);
		passwordField = new JPasswordField(10);
		role = new JComboBox(roles);
		ok = new JButton("OK");
		
		panel.add(new JLabel("¬вед≥ть лог≥н: "));
		panel.add(loginField);
		panel.add(new JLabel("¬вед≥ть пароль: "));
		panel.add(passwordField);
		
		panel.add(new JLabel("¬ибер≥ть роль: "));
		panel.add(role);
		this.add(panel);
		this.add(ok, BorderLayout.PAGE_END);
	}
	
	public JButton getButton(){
		return this.ok;
	}
	public JPasswordField getPasswordField(){
		return this.passwordField;
	}
	public JTextField getLoginField(){
		return this.loginField;
	}
	public JComboBox getRole(){
		return this.role;
	}
	
}
