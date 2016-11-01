package collections.comparator;

import java.util.TreeSet;

class Comp1 implements Comparable<Comp1> {

	String str;
	int number;

	Comp1(String str, int number) {
		this.str = str;
		this.number = number;
	}

	@Override
	public int compareTo(Comp1 obj) {
		Comp1 entry = (Comp1) obj;

		int result = str.compareTo(entry.str);
		if(result != 0) {
			return result;
		}

		result = number - entry.number;
		if(result != 0) {
			return (int) result / Math.abs( result );
		}
		return 0;
	}

}

public class ExampleComparable {

	public static void main(String[] args) {
		TreeSet<Comp1> ex = new TreeSet<Comp1>();
		ex.add(new Comp1("Stive Global", 121));
		ex.add(new Comp1("Stive Global", 221));        
		ex.add(new Comp1("Nancy Summer", 3213));
		ex.add(new Comp1("Aaron Eagle", 3123));
		ex.add(new Comp1("Barbara Smith", 88786));

		for(Comp1 e : ex) {
			System.out.println("Str: " + e.str + ", number: " + e.number);
		}
	}

}
