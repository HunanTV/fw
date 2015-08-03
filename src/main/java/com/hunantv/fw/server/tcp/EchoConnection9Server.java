package com.hunantv.fw.server.tcp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class EchoConnection9Server
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server();
     
        ServerConnector connector = new ServerConnector(server,new EchoConnectionFactory());
        connector.setPort(8080);
        server.addConnector(connector);
        server.start();
        server.join();
    }
}