package com.hunantv.fw.server.tcp;

import org.eclipse.jetty.io.ArrayByteBufferPool;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.AbstractConnectionFactory;
import org.eclipse.jetty.server.Connector;

public class EchoConnectionFactory extends AbstractConnectionFactory {
	public EchoConnectionFactory() {
		super("echo");
	}

	@Override
	public Connection newConnection(Connector connector, EndPoint endPoint) {
		ArrayByteBufferPool bufferPool = new ArrayByteBufferPool();
		return new EchoConnection(bufferPool, endPoint, connector.getExecutor());
	}
}
