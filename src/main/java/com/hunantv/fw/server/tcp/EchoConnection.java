package com.hunantv.fw.server.tcp;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;

import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.BlockingCallback;
import org.eclipse.jetty.util.BufferUtil;

public class EchoConnection extends AbstractConnection {
	public static final int BUF_MAX_LEN = 4096;
	final ByteBufferPool _bufferPool;
	ByteBuffer _buffer = BufferUtil.allocate(BUF_MAX_LEN);
	BlockingCallback _callback = new BlockingCallback();

	public EchoConnection(ByteBufferPool pool, EndPoint endp, Executor executor) {
		super(endp, executor);
		_bufferPool = pool;
		if (_bufferPool == null)
			_buffer = BufferUtil.allocate(BUF_MAX_LEN);
	}

	@Override
	public void onOpen() {
		super.onOpen();
		fillInterested();
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	@Override
	public void onFillable() {
		try {
			if (_buffer == null)
				_buffer = _bufferPool.acquire(BUF_MAX_LEN, false);

			while (true) {
				// 每一个循环，代表了响应了客户端的一个请求

				EndPoint endp = getEndPoint();
				int len = endp.fill(_buffer); // len 代表了 客户端发送过来的字节的长度
				System.out.println("Accept : " + new String(_buffer.array()) + " : " + _buffer.toString());
				if (len < 0)
					endp.close();

				if (len <= 0)
					break;

				if (_buffer.hasRemaining()) {
					endp.write(_callback, _buffer);
					_callback.block();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (_bufferPool != null && _buffer != null) {
			_bufferPool.release(_buffer);
			_buffer = null;
		}
		fillInterested();
	}

}