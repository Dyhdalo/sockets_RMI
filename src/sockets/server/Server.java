package sockets.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args)  {
		DAO.createConnection();
		
		try (ServerSocket servsock = new ServerSocket(Integer.parseInt(args[0]))) {
			System.out.println("Server started!");
			
			while(true){
				Socket socket = servsock.accept();
				new Thread(new MyThread(socket)).start();
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
