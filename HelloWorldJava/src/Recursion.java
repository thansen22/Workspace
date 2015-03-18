
public class Recursion {

	public static void main(String[] args) {
		
		System.out.println(greatestCommonDivisor(6, 6));
		System.out.println(towersOfHanoi(6));
	}
	
	//Integers x, y such that x >= y and y > 0
	private static int greatestCommonDivisor(int x, int y) {
		if (y == 0) {
			return x;
		}
		else {
			return greatestCommonDivisor(y, x%y);
		}		
	}
	/* Iterative:
	1. create new variable called remainder
    2. begin loop
          1. if y is zero, exit loop
          2. set remainder to the remainder of x/y
          3. set x to y
          4. set y to remainder
          5. repeat loop

    3. return x 
	 */
	
	private static int towersOfHanoi(int n) {
		if (n == 1) {
			return 1;
		}
		else {
			return 2 * towersOfHanoi(n-1) + 1;
		}
	}

}
