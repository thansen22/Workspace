import java.util.ArrayList;
import java.util.List;


public class Collections {

	public static void main(String[] args) {
		// ArrayList and foreach equiv 
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(1);
		intList.add(2);
		intList.add(3);
		intList.add(5);		
		
		StringBuffer temp = new StringBuffer();
		for (int value : intList)
		{
			temp.append(value + ", ");
		}
		temp.delete(temp.length()-2, temp.length());
		System.out.println(temp);
		
		// Same thing, but with just an array of int
		temp.delete(0, temp.length());
		
		for (int value : fibArray)
		{
			temp.append(value + ", ");
		}
		temp.delete(temp.length()-2, temp.length());
		System.out.println(temp);
	}
	
	public static int[] fibArray = new int[]{ 1, 2, 3, 5, 8, 13, 21, 34 };
	public static int[] unsortedIntArray = new int[]{ 39, 2, 22, 5, 8, 59, 1, 34 };
}
