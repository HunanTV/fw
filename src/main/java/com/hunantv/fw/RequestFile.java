package com.hunantv.fw;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class RequestFile {
	public String fileName;
	public String fieldName;
	public byte[] content;
	public String contentType;
	public int size;

	public RequestFile saveTo(BufferedOutputStream out) throws IOException {
		out.write(content);
		return this;
	}

	public RequestFile saveTo(String fpath) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fpath));
		return this.saveTo(out);
	}

	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		strb.append("fieldName=").append(fieldName).append("; ");
		strb.append("fileName=").append(fileName).append("; ");
		strb.append("contentType=").append(contentType).append("; ");
		strb.append("size=").append(size).append("; ");
		return strb.toString();
	}
}
