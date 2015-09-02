package org.imploder.beans;

import java.io.Serializable;

/**
 *
 * @author Jorge
 */
public class CustomFile implements Serializable {

	private String name;
	private byte[] bytes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
}
