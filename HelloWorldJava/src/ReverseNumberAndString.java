import java.util.Hashtable;
import java.util.Scanner;

public class ReverseNumberAndString {
	public static void main(String[] args) {
		System.out.println("Enter a string to reverse: ");
		Scanner scan = new Scanner(System.in);
		String strInput = scan.nextLine();				
		String strOutput = "";
		for (int i = strInput.length() - 1; i >= 0; i--)
		{
			strOutput += strInput.charAt(i);
		}		
		System.out.println(strOutput);
		
		System.out.println("Now enter a number: ");
		long intInput = scan.nextLong();
		long intOutput = 0;		
		while (intInput != 0) {
			intOutput *= 10;
			intOutput += intInput % 10;
			intInput /= 10;
		}	
		System.out.println(intOutput);
		
		scan.close();
	}


}
