package equality_and_wrappers.wrappers;

class Wrap { 
	public static void main(String args[]) { 

		Integer iOb = new Integer(100);  

		int i = iOb.intValue(); 

		System.out.println(i + " " + iOb); // displays 100 100 
		
		Integer x = null;
		
		x++;
	} 
}
