import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
//import org.junit.Ignore;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;

public class JUnitTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Test
	public void testFibRecursiveFirst5Values() {
	   // Tests
	   assertEquals("fibRecursive(1) failure - ", 1, Fibonacci.fibRecursive(1));
	   assertEquals("fibRecursive(2) failure - ", 1, Fibonacci.fibRecursive(2));
	   assertEquals("fibRecursive(3) failure - ", 2, Fibonacci.fibRecursive(3));
	   assertEquals("fibRecursive(4) failure - ", 3, Fibonacci.fibRecursive(4));
	   assertEquals("fibRecursive(5) failure - ", 5, Fibonacci.fibRecursive(5));
	} 
	
	@Test
	public void testFibRecursive2LargeValues() {
	   // Tests
	   assertEquals("fibRecursive(20) failure - ", 6765, Fibonacci.fibRecursive(20));
	   assertEquals("fibRecursive(30) failure - ", 832040, Fibonacci.fibRecursive(30));
	}
	
	@Test
	public void testFibImplementationPerformance() {
	   // Tests
	   int number = 1000000000;
       long startTime = System.nanoTime();
       Fibonacci.fibRecursive(10);
       long elapsedTime = System.nanoTime() - startTime;
	   assertTrue("fibRecursive(10) took to long :" + elapsedTime, elapsedTime < 10000);
	   	   
	   startTime = System.nanoTime();
       Fibonacci.fibIterative(number);
       elapsedTime = System.nanoTime() - startTime;
	   assertTrue("fibIterative(100) took to long :" + elapsedTime, elapsedTime < 1000000000);	   	   
	   
	   startTime = System.nanoTime();
       Fibonacci.fibMemorization(number);
       elapsedTime = System.nanoTime() - startTime;
	   assertTrue("fibMemorization(100) took to long :" + elapsedTime, elapsedTime < 1000000000);	   
	} 
}
