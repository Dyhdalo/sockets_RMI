package sockets.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{

	private int idOfUser;
	private String role;
	private String name;
	private String password;
	
	@SuppressWarnings("unused")
	private User(){};
	
	public User(int idOfUser, String role, String name, String password) {
		this.idOfUser = idOfUser;
		this.role = role;
		this.name = name;
		this.password = password;
	}
	
	public int getIdOfUser() {
		return idOfUser;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return idOfUser;
	}

	@Override
	public String toString() {
		return "User [role=" + role + ", name=" + name + ", password="
				+ password + "]";
	}

	@Override
	public boolean equals(Object obj) {
		User u = (User)obj;
		
		return this.name.equals(u.getName())&&this.password.equals(u.getPassword())&&this.role.equals(u.getRole());
	}
	
}
