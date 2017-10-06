package com.groceriescoach;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class ServletInitializer extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(ServletInitializer.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		if (applicationIsOnline()) {
			logger.info("Application is online.");
			application.profiles("online");
		} else {
			logger.info("Application is offline.");
			application.profiles("offline");
		}

		return application.sources(GroceriesCoachApplication.class);
	}

	private static boolean applicationIsOnline() {
		Socket socket = new Socket();
		InetSocketAddress address = new InetSocketAddress("google.com", 80);
		try {
			socket.connect(address, 3000);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				socket.close();
			} catch (IOException ignored) {

			}
		}
	}
}