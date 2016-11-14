package exceptions;

public class MultiNest {
	static void procedure() { 
		try { 
			int c[] = { 1 }; 
			c[42] = 99; 
		} 
		catch(ArrayIndexOutOfBoundsException e) { 
			System.out.println("array index oob: " + e); 
		}
	} 
	public static void main(String args[]) { 
		try { 
			int a = args.length; 
			System.out.println("a = " + a); 
			int b = 42 / a; 
			procedure(); 
		} 
		catch (ArithmeticException e) { 
			System.out.println("div by 0: " + e); 
		} 
	}
}
