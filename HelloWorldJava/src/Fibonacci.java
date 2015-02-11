import java.util.ArrayList;
import java.util.Scanner;

public class Fibonacci {	
	static ArrayList<Integer> fibCache = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		// get input from the console.  System.in.read() only reads one byte. 
		// can also use BufferedReader br = new BufferedReader (new InputStreamReader(System.in)); br.readLine() after importing import java.io.*;
		Scanner scan = new Scanner(System.in);
		int num = scan.nextInt();
		scan.close();
		
		for (int i = 1; i <= num; i++)
			System.out.print(fibRecursive(i) + " ");
		System.out.println("");
		
		for (int i = 1; i <= num; i++)
			System.out.print(fibIterative(i) + " ");
		System.out.println("");
		
		for (int i = 1; i <= num; i++)
			System.out.print(fibMemorization(i) + " ");
		System.out.println("");
	}	
	
	protected static int fibRecursive(int n)
	{
		if (n == 1 || n == 2)
			return 1;
		
		return fibRecursive(n-1) + fibRecursive(n-2);
	}

	protected static int fibIterative(int n)
	{
		if (n == 1 || n == 2)
			return 1;
		
		int fib1 = 1, fib2 = 1, fibResult = 0;
		for (int i = 2; i < n; i++)
		{
			fibResult = fib1 + fib2;
			fib1 = fib2;
			fib2 = fibResult;			
		}
		
		return fibResult;
	}
	
	protected static int fibMemorization(int n)
	{		
		Integer fibReturn;
		if (fibCache.size() >= n)
		{
			fibReturn = fibCache.get(n);
			return fibReturn;
		}
		
		fibReturn = fibIterative(n);
		fibCache.add(fibReturn);
		return fibReturn;
	}
	
}
