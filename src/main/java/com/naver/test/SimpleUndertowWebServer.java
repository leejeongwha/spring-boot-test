package com.naver.test;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class SimpleUndertowWebServer {
	public static void main(String[] args) {
		Undertow server = Undertow.builder().addHttpListener(8280, "localhost").setHandler(new HttpHandler() {
			@Override
			public void handleRequest(final HttpServerExchange exchange) throws Exception {
				exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
				exchange.getResponseSender().send("Welcome to Undertow Web Server");
			}
		}).build();
		server.start();
	}
}