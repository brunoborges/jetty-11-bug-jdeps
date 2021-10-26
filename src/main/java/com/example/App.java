package com.example;

import java.io.IOException;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Hello world! GET http://localhost:8080/hello
 *
 */
public class App {
    public static void main(String[] args) {
        Server server = new Server();

        // The HTTP configuration object.
        HttpConfiguration httpConfig = new HttpConfiguration();
        // Configure the HTTP support, for example:
        httpConfig.setSendServerVersion(false);

        // The ConnectionFactory for HTTP/1.1.
        HttpConnectionFactory http11 = new HttpConnectionFactory(httpConfig);

        // Create the ServerConnector.
        ServerConnector connector = new ServerConnector(server, http11);
        connector.setPort(8080);

        server.addConnector(connector);

        // set handlers
        class HelloWorldHandler extends AbstractHandler {
            @Override
            public void handle(String target, Request jettyRequest, HttpServletRequest request,
                    HttpServletResponse response) throws IOException {
                jettyRequest.setHandled(true);
                response.setStatus(200);
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().print("Hello world!");
            }
        }

        // Create a ContextHandler with contextPath.
        ContextHandler context = new ContextHandler();
        context.setContextPath("/hello");
        context.setHandler(new HelloWorldHandler());

        server.setHandler(context);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
