package collections.generics;

class BoxPrinter2<T> { 
	private T val; 

	public BoxPrinter2(T arg) { 
		val = arg; 
	} 

	public String toString() { 
		return "{" + val + "}"; 
	} 

	public T getValue() { 
		return val; 
	} 
} 

public class Test2 { 
	public static void main(String[] args) { 
		BoxPrinter2<Integer> value1 = new BoxPrinter2<Integer>(new Integer(10)); 
		System.out.println(value1); 
		Integer intValue1 = value1.getValue(); 
		BoxPrinter2<String> value2 = new BoxPrinter2<String>("Hello world"); 
		System.out.println(value2); 

		// Здесь повторяется ошибка предыдущего фрагмента кода 
		//Integer intValue2 = value2.getValue(); 
	} 
} 
