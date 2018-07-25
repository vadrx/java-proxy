package ru.novsu.povt.vas.client;

import java.io.*;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws InterruptedException {
		String url;
		String proxyAddress;
		int proxyPort;
		String ansfer;

		if (args.length < 3) {
			System.out.println("Not enough arguments:" + "1 - URL, " + 
									"2 - addr proxy, 3 - port proxy");
		}
		else {
			url = args[0];
			proxyAddress = args[1];
			proxyPort = Integer.parseInt(args[2]);

			try(Socket socket = new Socket(proxyAddress, proxyPort);
				BufferedReader in = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));

				PrintWriter out = new PrintWriter(
							socket.getOutputStream(), true))
			{
				System.out.println("Client connected to socket.");
				// Отправка URL
				out.println("GET "+ url + " HTTP/1.1");
				out.println();
				out.flush();
				System.out.println("Client sent url " + url + " to server.");

				// проверяем живой ли канал и работаем если живой
				while(!socket.isOutputShutdown()){
					ansfer = in.readLine();
					if (ansfer == null || ansfer.equalsIgnoreCase("End!") )
					{
						out.println("End!");
						out.flush();
						socket.close();
						break;
					}
					if (ansfer.equalsIgnoreCase("Page not found!")){
						out.println("Page not found!");
						out.flush();
						socket.close();
						break;
					}
					System.out.println(ansfer);
				}
				// на выходе из цикла общения закрываем свои ресурсы
				System.out.println("\nClosing connections " + 
								"& channels on client side - DONE.");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				System.out.println("Wrong proxy address or port!");
			}
		}
	}
}
