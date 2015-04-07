import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


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
				
		// TreeSet tests 
		TreeSet<Integer> ts = new TreeSet<>();
		ts.add(1);
		ts.add(2);
		ts.add(3);
		ts.add(4);
		ts.add(5);
		ts.remove(ts.first());
		System.out.println(ts.first());		
		System.out.println(ts.last());

	}
	
	public static int[] fibArray = new int[]{ 1, 2, 3, 5, 8, 13, 21, 34 };
	public static int[] unsortedIntArray = new int[]{ 39, 2, 22, 5, 8, 59, 1, 34 };
}
