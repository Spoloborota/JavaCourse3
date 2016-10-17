package inheritance;

public class CompositionInheritanceDelegating {
	
	class A {
		public void doSomethingA(){
			System.out.println("A class method");}}
	
	class B {
		public void doSomethingB(){
			System.out.println("B class method");}}
	
	class Composition {
		A a;
		B b;
		
		public void doSomethingComposition() {
			/*
			 * any code here
			 */
			a.doSomethingA();
			/*
			 * any code here
			 */
			b.doSomethingB();
			/*
			 * any code here
			 */
			a.doSomethingA();
		}
	}
	
	class Inheritance extends A {
		
		public void doSomethingInheritance() {
			/*
			 * any code here
			 */
			super.doSomethingA();
			/*
			 * any code here
			 */
		}
	}
	
}
