package io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderWriter {

	public static void main(String[] args) throws IOException {
		FileReader frr = new FileReader(".project");
		FileWriter fwr = new FileWriter(".project_copy");
		while(frr.ready()) {
			fwr.append((char)frr.read());
		}
		frr.close();
		fwr.close();
	}
	
	

}
