package org.imploder.beans;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jorge
 */
public class CustomFolder implements Serializable {

	private String name;
	private List<CustomFile> files;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CustomFile> getFiles() {
		return files;
	}

	public void setFiles(List<CustomFile> files) {
		this.files = files;
	}
		
}
