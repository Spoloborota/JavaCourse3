package exceptions;

public class RuntimeMethod {
	static void subroutine() { 
		int d = 0; 
		int a = 10 / d; 
	} 
	public static void main(String args[]) { 
		RuntimeMethod.subroutine(); 
	}
}
