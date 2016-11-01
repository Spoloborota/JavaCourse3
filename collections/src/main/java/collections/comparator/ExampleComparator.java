package collections.comparator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;


class Comp2 implements Comparator<String> {

	@Override
	public int compare(String str1, String str2) {
		// поиск пробелов, для сортировки по фамилии
		int k = str1.substring(str1.indexOf(" ")).compareTo(str2.substring(str2.indexOf(" ")));
		if(k == 0) {
			return str1.compareTo(str2);
		}
		else {
			return k;
		}
	}

}

public class ExampleComparator {

	public static void main(String[] args) {
		TreeSet<String> ex = new TreeSet<String>(new Comp2());
		ex.add(new String("Stive Global"));
		ex.add(new String("Stive Cooper"));
		ex.add(new String("Nancy Summer"));
		ex.add(new String("Aaron Eagle"));
		ex.add(new String("Barbara Smith"));

		Iterator<String> i = ex.iterator();

		while(i.hasNext()) {
			String ts = i.next();
			System.out.println("Str: " + ts);
		}
	}

}