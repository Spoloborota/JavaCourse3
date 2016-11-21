package io;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileOutputStreamS {
	public static byte getlnput()[] throws Exception {

		byte buffer[] = new byte[12];

		for (int i=0; i<12; i++) {

			buffer[i] = (byte) System.in.read();

		}

		return buffer;

	}

	public static void main(String args[]) throws Exception {

		byte buf[] = getlnput();

		OutputStream f0 = new FileOutputStream("file1.txt");

		OutputStream f1 = new FileOutputStream("file2.txt");

		OutputStream f2 = new FileOutputStream("file3.txt");

		for (int i=0; i < 12; i += 2) {

			f0.write(buf[i]);

		}

		f0.close();

		f1.write(buf);

		f1.close();

		f2.write(buf, 12/4, 12/2);

		f2.close();

	}
}
