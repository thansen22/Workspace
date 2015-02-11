
public class Numbers {
	public static void main(String[] args) {
		// add the digits of a number using recursion
		int number = 17;
		int sum = sumUpRecursion(number);
		System.out.println(String.format("Sum of digits in %d is %d", number, sum));
		
		// See if number is prime or not
		System.out.println(String.format("Is %d prime?: %s", number, Numbers.isPrime(number)));
		
		// See if number is Armstrong or not
		System.out.println(String.format("Is %d Armstrong?: %s", number, Numbers.isArmstrong(number)));
		
		// Sum of prime numbers
		System.out.println(String.format("Sum of prime numbers up to %d is: %s", number, Numbers.sumPrime(number)));
	}
	
	private static int sumUpRecursion(int number) {
		if (number == 0)
			return 0;
		int sum = number % 10;
		number = number / 10;
		sum += sumUpRecursion(number);
		return sum;
	}
	
	private static boolean isPrime(int number) {
		for (int i = 2; i <= number / 2; i++) {
			if (number % i == 0)
				return false;
		}
		return true;
	}
	
	private static boolean isArmstrong(int number) {
		int digits = String.valueOf(number).length();
		int sum = 0;
		int tempNumber = number;
		for (int i = 1; i <= digits; i++) {
			sum += Math.pow((tempNumber % 10), digits);
			tempNumber /= 10;
		}
		if (sum == number)
			return true;
		else
			return false;
	}
	
	private static int sumPrime(int number) {
		int sum = 0;
		for (int i = 1; i < number; i++) {
			if (isPrime(i)) {
				sum += i;
			}
		}
			
		return sum;
	}
}
