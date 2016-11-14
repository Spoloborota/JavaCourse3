package exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TryWithRes {

	static String readFirstLineFromConsole(String path) throws IOException { 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try { 
			return br.readLine(); 
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally { 
			if (br != null) {
				br.close();
			}
		} 
	}

	static String readFirstLineFromConsole2(String path) throws IOException { 
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) { 
			return br.readLine(); 
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
