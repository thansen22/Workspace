//import java.io.*;
import java.awt.List;
import java.awt.RenderingHints;
import java.util.*;
import java.util.function.IntPredicate;

import org.yaml.snakeyaml.tokens.ValueToken;
//import org.apache.commons.jxpath.util.ValueUtils;

public class Practice
{    
    static void findIndex(int[] numArray, int value)
    {
    	for (int i = 0; i < numArray.length; i++)
    	{
    		if (numArray[i] == value)
    		{
    			System.out.print(i);
    			break;
    		}
    	}
    }

    static void insertionSort1(int[] numArray)
    {    	
    	// start with the next value. from the input constraints we could have a single value so nothing needs to be done. Otherwise we'll get out of bounds ex
    	if (numArray.length > 1)
    	{
    		int sortValue = numArray[numArray.length - 1];
	    	int index = numArray.length - 2;
	    	while (index >= 0 && sortValue < numArray[index])
	    	{
	    		numArray[index + 1] = numArray[index];
	    		printArray(numArray);   
	    		index--;
	    	}
	    	numArray[index + 1] = sortValue;
    	}
    	printArray(numArray);        
    }
    
    static void insertionSort2(int[] numArray)
    {    	
    	// start with the next value. from the input constraints we could have a single value so nothing needs to be done. Otherwise we'll get out of bounds ex
    	if (numArray.length > 1)
    	{    		
	    	for (int i = 1; i < numArray.length; i++)
	    	{
	    		for (int j = i; j > 0; j--)
	    		{
	    			if (numArray[j] < numArray[j-1])
	    			{
	    				int temp = numArray[j-1];
	    				numArray[j-1] = numArray[j];
	    				numArray[j] = temp;
	    			}
	    		}
	    		printArray(numArray);
	    	}	    
    	}
    	else {
    		printArray(numArray);
		}
    }
    
    public static void insertionSortError(int[] A){
        for(int i = 1; i < A.length; i++){
            int value = A[i];
            int j = i - 1;
            while(j >= 0 && A[j] > value){
                A[j + 1] = A[j];
                j = j - 1;
            }
            A[j + 1] = value;
        }

        printArray(A);
    }
    
    public static void insertionSortCount(int[] A){
    	int count = 0;
        for(int i = 1; i < A.length; i++){
            int value = A[i];
            int j = i - 1;
            while(j >= 0 && A[j] > value){
            	count++;
                A[j + 1] = A[j];
                j = j - 1;
            }
            A[j + 1] = value;
        }
        System.out.println(count);
    }
    
    public static void quickSort1Partition(int[] numArray){
    	ArrayList<Integer> right = new ArrayList<>();
    	ArrayList<Integer> left = new ArrayList<>();
    	
    	int value = numArray[0];
    	
    	for (int i = 1; i < numArray.length; i++)
    	{
    		if (numArray[i] < value)
    		{
    			right.add(numArray[i]);
    		}
    		else {
    			left.add(numArray[i]);
			}
    	}
    	
    	for (int r : right) {
    		System.out.print(r+" ");
    	}
    	System.out.print(value+" ");
    	for (int l : left) {
    		System.out.print(l+" ");
    	}
    		
    }
    
    static void printArray(int[] numArray) 
    {
        for(int n: numArray)
        {
            System.out.print(n+" ");
        }
		System.out.println("");	
    }
    
    public static void main(String[] args) 
    {
        Scanner in = new Scanner(System.in);
        //int v = in.nextInt();        
        int n = in.nextInt();
        int[] numArray = new int[n];
        for(int i=0;i<n;i++)
        {
            numArray[i]=in.nextInt();
        }
        
        // finished with scanner
        in.close();
        //findIndex(numArray, v);
        //insertionSort1(numArray);
        //insertionSort2(numArray);
        //insertionSortError(numArray);
        //insertionSortCount(numArray);
        quickSort1Partition(numArray);
    }
}
