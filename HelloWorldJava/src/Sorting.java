import java.util.TreeSet;


public class Sorting {

	public static void main(String[] args) {
		int[] myNumbers = Collections.unsortedIntArray;
		
		// Insertion sort
		for (int i = 1; i < myNumbers.length; i++) {
			for (int j = i - 1; j >= 0; j--) {
				if (myNumbers[j + 1] < myNumbers[j]) {
					int temp = myNumbers[j + 1];
					myNumbers[j + 1] = myNumbers[j];
					myNumbers[j] = temp;
				}
				for (int num : myNumbers)
					System.out.print(num + ", ");
				System.out.println("");
			}
		}
		
		myNumbers = Collections.unsortedIntArray;
		// Bubble sort
		for (int i = 0; i < myNumbers.length; i++) {
			for (int j = 0; j < myNumbers.length; j++) {
				if (myNumbers[i] > myNumbers[j]) {
					int temp = myNumbers[j];
					myNumbers[j] = myNumbers[i];
					myNumbers[i] = temp;
				}
				for (int num : myNumbers)
					System.out.print(num + ", ");				
				System.out.println("");
			}
		}
		
		// Quick sort
		
		
		
		
	}

}
