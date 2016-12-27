package hibernate.test;

import org.hibernate.Session;

import hibernate.test.dto.EmployeeEntity;

public class TestHibernateSecond {

	public static void main(String[] args) {
		Session sessionOne = HibernateUtil.getSessionFactory().openSession();
	      sessionOne.beginTransaction();
	       
	      // Create new Employee object
	      EmployeeEntity emp = new EmployeeEntity();
	      emp.setFirstName("Lokesh");
	      emp.setLastName("Gupta");
	       
	      //Save employee
	      sessionOne.save(emp);
	      //store the employee id generated for future use
	      Integer empId = emp.getEmployeeId();
	      sessionOne.getTransaction().commit();
	       
	      /************************************************************************/
	       
	      //Let's open a new session to test load() methods
	      Session sessionTwo = HibernateUtil.getSessionFactory().openSession();
	      sessionTwo.beginTransaction();
	       
	      //first load() method example
	      EmployeeEntity emp1 = (EmployeeEntity) sessionTwo.load(EmployeeEntity.class, empId);
	      System.out.println(emp1.getFirstName() + " - " +emp1.getLastName());
	       
	      //Let's verify the entity name
	      System.out.println(sessionTwo.getEntityName(emp1));
	       
	      sessionTwo.getTransaction().commit();
	       
	      /************************************************************************/
	       
	      Session sessionThree = HibernateUtil.getSessionFactory().openSession();
	      sessionThree.beginTransaction();
	       
	      //second load() method example
	      EmployeeEntity emp2 = (EmployeeEntity) sessionThree.load("hibernate.test.dto.EmployeeEntity", empId);
	      System.out.println(emp2.getFirstName() + " - " +emp2.getLastName());
	       
	      sessionThree.getTransaction().commit();
	       
	      /************************************************************************/
	       
	      Session sessionFour = HibernateUtil.getSessionFactory().openSession();
	      sessionFour.beginTransaction();
	       
	      //third load() method example
	      EmployeeEntity emp3 = new EmployeeEntity();
	      sessionFour.load(emp3 , empId);
	      System.out.println(emp3.getFirstName() + " - " +emp3.getLastName());
	       
	      sessionFour.getTransaction().commit();
	       
	      HibernateUtil.shutdown();

	}

}
