package io;

import java.io.FileInputStream;
import java.io.InputStream;

public class FileInputStreamS {
	public static void main(String args[]) throws	Exception {

		int size;

		InputStream f1 = new FileInputStream("pom.xml");

		size = f1.available();

		System.out.println("Total Available Bytes: " + size);

		System.out.println("First 1/4 of the file: read()");

		for (int i=0; i < size/4; i++) {

			System.out.print((char) f1.read());

		}

		System.out.println("\nTotal Still Available: " + f1.available());

		System.out.println("Reading the next 1/8: read(b[])");

		byte b[] = new byte[size/8];

		if (f1.read(b) != b.length) {

			System.err.println("Something bad happened");

		}

		String tmpstr = new String(b, 0, 0, b.length);

		System.out.println(tmpstr);

		System.out.println("Still Available: " + f1.available());

		System.out.println("Skipping another 1/4: skip()");

		f1.skip(size/4);

		System.out.println( "Still Available: " + f1.available());

		System.out.println("Reading 1/16 into the end of array");

		if (f1.read(b, b.length-size/16, size/16) != size/16) {

			System.err.println("Something bad happened");

		}

		System.out.println("Still Available: " + f1.available());

		f1.close();

	}
}
