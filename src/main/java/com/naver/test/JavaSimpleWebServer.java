package com.naver.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;

/**
 * @author NAVER 자바로 구현한 웹서버
 */
public class JavaSimpleWebServer {
	private static final int fNumberOfThreads = 100;
	private static final Executor fThreadPool = Executors.newFixedThreadPool(fNumberOfThreads);

	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(8180);
		while (true) {
			final Socket connection = socket.accept();
			Runnable task = () -> HandleRequest(connection);
			fThreadPool.execute(task);
		}
	}

	private static void HandleRequest(Socket socket) {
		BufferedReader in;
		PrintWriter out;
		String request;

		try {
			String webServerAddress = socket.getInetAddress().toString();
			System.out.println("New Connection:" + webServerAddress);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			request = in.readLine();
			System.out.println("--- Client request: " + request);

			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("HTTP/1.0 200");
			out.println("Content-type: text/html");
			out.println("Server-name: myserver");
			String response = "Welcome to Java Simple Web Server";
			out.println("Content-length: " + response.length());
			out.println("");
			out.println(response);
			out.flush();
			out.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Failed respond to client request: " + e.getMessage());
		} finally {
			IOUtils.closeQuietly(socket);
		}
		return;
	}
}
