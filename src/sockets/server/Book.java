package sockets.server;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Book implements Serializable{

	private int idOfBook;
	private String name;
	private String author;
	private String description;
	
	@SuppressWarnings("unused")
	private Book(){};
	
	public Book(int id, String name, String author, String description) {
		this.idOfBook = id;
		this.name = name;
		this.author = author;
		this.description = description;
	}

	public int getIdOfBook() {
		return idOfBook;
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public String getDescription() {
		return description;
	}
	
	public int getId() {
		return idOfBook;
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", author=" + author + ", description=" + description + "]";
	}
	
}
