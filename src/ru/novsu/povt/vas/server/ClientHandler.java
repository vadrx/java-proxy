package ru.novsu.povt.vas.server;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class ClientHandler extends Thread implements Runnable {

	private static Socket clientDialog;

	ClientHandler(Socket client){ ClientHandler.clientDialog = client; }

	@Override
	public void run() {
				// инициируем каналы общения в сокете, для сервера
		try (BufferedReader in = new BufferedReader(
					new InputStreamReader(clientDialog.getInputStream()));

			PrintWriter out =  new PrintWriter(
					clientDialog.getOutputStream(), true))
			{
			
			String urlStr = in.readLine();
			System.out.println("Accepted request: " + urlStr);

			urlStr = urlStr.substring(urlStr.indexOf(' ')+1, urlStr.length()-8);
			System.out.println("URL: "+ urlStr);

			URLConnection urlConnection = new URL(urlStr).openConnection();

			BufferedReader webReader = new BufferedReader(
						new InputStreamReader(urlConnection.getInputStream()));
			String webPage;

			while ((webPage = webReader.readLine()) != null)
			{
				out.println(webPage);
				out.flush();
			}
			out.println("End!");

			// закрываем сначала каналы сокета !
			in.close();
			out.close();
			// потом закрываем сокет общения с клиентом в нити моносервера
			clientDialog.close();

			System.out.println("Closing connections & channels - DONE.");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
