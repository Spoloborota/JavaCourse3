package exceptions;

public class NewClass {
	public static void main(String[] args) throws MyExceptionNew { 
		int result = 0; 
		try{ 
			result = getAreaValue(-1, 100); 
		}catch(IllegalArgumentException e){ 
			throw new MyExceptionNew(e); 
		} 
		System.out.println("Result is : "+result); 
	} 

	public static int getAreaValue(int x, int y){ 
		if(x < 0 || y < 0) throw new IllegalArgumentException("value of 'x' or 'y' is negative: x="+x+", y="+y); 
		return x*y; 
	} 

}

class MyExceptionNew extends Exception{ 
	public MyExceptionNew(Throwable e) { 
		initCause(e); 
	} 
} 
