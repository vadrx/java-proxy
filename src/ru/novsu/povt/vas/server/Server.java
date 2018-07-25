package ru.novsu.povt.vas.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		int port = 3345;

		try (ServerSocket serverSocket = new ServerSocket(port)){
			while (!serverSocket.isClosed())
			{
				System.out.println("Server started! Port: " +                   						serverSocket.getLocalPort());

				Socket clientDialog = serverSocket.accept();

				System.out.println("New connection! " + serverSocket.getInetAddress() +
									"port " + clientDialog.getPort());

				System.out.print("Connection accepted.");

				new ClientHandler(clientDialog).start();
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("Wrong open server socket!");
		}
	}
}