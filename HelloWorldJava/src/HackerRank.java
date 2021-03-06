//import java.io.*;
import java.util.*;
//import org.apache.commons.jxpath.util.ValueUtils;

public class HackerRank
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
    
    public static void utopianTree(int[] numArray) {
    	for (int time : numArray) {
    		int output = 1;
    		for (int i = 0; i < time; i++) {
    			if (i % 2 == 0) {
    				output *= 2;
    			}
    			else {
    				output += 1;
    			}
    		}
    		System.out.println(output);
    	}
    }
    
    public static void iceCreamParlor(int money, int[] numArray) {    	
    	for (int i = 0; i < numArray.length - 1; i++) {
    		int item1 = numArray[i];    		
    		for (int j = i + 1; j < numArray.length; j++) {
    			int item2 = numArray[j];
    			if (item1 + item2 == money) {
    				System.out.println((i+1)+" "+(j+1));
    			}
    		}
    	}
    }
    
    public static void encryption(String s) {
    	int originalLength = s.length();
    	int rows = (int) Math.sqrt(originalLength);
    	int columns = rows;
    	if (!(columns * rows == originalLength)) {
    		columns++;
    	}
    	
    	String output = "";
    	for (int n = 0; n < columns; n++) {
    		for (int m = n; m < s.length(); m += columns) {
    			output += s.charAt(m);
    		}
    		output += " ";
    	}
    	System.out.println(output);
    	//System.out.println(columns + " " + rows);
    }
    
    public static void handshake(int[] numArray) {
    	for (int input : numArray) {    		
			int total = 0;
			for (int shakes = input - 1; shakes > 0; shakes--) {
				total += shakes;
			}
			System.out.println(total);    		
    	}
    }
    
    public static void maximumSubArray(int[] numArray) {
    	int bestContiguousSum = numArray[0];
    	int bestNonContiguousSum = numArray[0]; 
    	int sum = numArray[0];
    	
    	for (int i = 1; i < numArray.length; i++) {
    		int value = numArray[i] + bestContiguousSum;
    		if (value > 0) {    			    			
    			sum += numArray[i];    				
    		}
    		else {
    			sum = 0;    		
    		}
    		if (sum != 0 && sum > bestContiguousSum) {
    			bestContiguousSum = sum;
        	}
    		
    		if (numArray[i] > 0) {
    			bestNonContiguousSum += numArray[i];
    		}
    	}    	    	
    	
    	System.out.println(bestContiguousSum + " " + bestNonContiguousSum);    	
    }
    
    public static void connectingTowns(int[] numArray) {
    	int total = 1;
    	for (int i : numArray) {
    		total = total * i % 1234567;
    	}
    	System.out.println(total);
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
        //String encode = in.nextLine();
        //int v = in.nextInt();        
        int n = in.nextInt();
        int m = in.nextInt();
        //int[] numArray = new int[n];
        
        int newConnection = in.nextInt();
        int value = in.nextInt();
        Tree t = new Tree(value, newConnection);
        
        for(int i=1;i<m;i++) {
        	newConnection = in.nextInt();
            value = in.nextInt();
            
            t.addNode(value, newConnection);
        //for(int i=0;i<n;i++) {
        	//numArray[i]=in.nextInt();
        	//for iceCreamParlor        	
        	//int money = in.nextInt();
        	//int items = in.nextInt();
        	//int[] numArray = new int[items - 1];
        	
        	//for iceCreamParlor
//        	for (int j = 0; j < items - 1; j++) {
//        		numArray[j]=in.nextInt();
//        	}
        	//iceCreamParlor(money, numArray);
        	//maximumSubArray(numArray);
        	//connectingTowns(numArray);
        }
      
        // finished with scanner
        in.close();
              
        t.getHead().countChops();
        //handshake(numArray);        
        //findIndex(numArray, v);
        //insertionSort1(numArray);
        //insertionSort2(numArray);
        //insertionSortError(numArray);
        //insertionSortCount(numArray);
        //quickSort1Partition(numArray);
        //utopianTree(numArray);    
        //encryption(encode);        
    }
}

class Tree
{
    private Node _root;

    public Tree()
    {

    }
    
    public Tree(int value, int newConnection)
    {
        this._root = new Node();
        this._root.setValue(value);
        
        Node n = new Node();
        n.setValue(newConnection);
        
        this._root.setConnection(n);
    }
    
    public void addNode(int value, int newConnection) {
    	Node n = _root.findValue(value);
    	if (n == null) {
    		System.out.println("error, couldn't find node to add connection to");
    	}
    	else {
    		n.setConnection(new Node(newConnection));
    	}
    }

    public Node getHead() {
    	return _root;
    }   
}

class Node
{
    private int _value;
    private LinkedList<Node> _nodes;

    public Node()
    {
    	this._nodes = new LinkedList<>();
    }
    
    public Node(int value)
    {
//    	//First value, or maybe they do this out of order
//    	if (findValue(value) == null && findValue(connectedTo) == null) {
//    		
//    	}
    	this._nodes = new LinkedList<>();    		
    	this._value = value;    	
    }
    
    public void setConnection(Node newConnection) {
    	this._nodes.add(newConnection);
//    	Node n = findValue(connectedTo);
//    	if (n == null) {
//    		System.out.println("error, cannot find connected node.  Have to create one?");
//    	}
//    	else {
//    		this._nodes.add(n);
//    	}
    }
    
    public void countChops() {
    	int chops = 0;
    	
    	LinkedList<Node> q = new LinkedList<>();
    	q.addAll(this._nodes);
    	
    	while (!q.isEmpty()) {    		
    		Node n = q.remove();
    		if (n.childCount() % 2 == 1) {
    			chops++;
    		}
    		q.addAll(n._nodes);
    	}
    	
    	System.out.println(chops);
    }
    
    public int childCount() {
    	int children = 0;
    	
    	LinkedList<Node> q = new LinkedList<>();
    	q.addAll(this._nodes);
    	
    	while (!q.isEmpty()) {
    		children++;
    		Node n = q.remove();    		
    		q.addAll(n._nodes);
    	}
    	
    	return children;
    }

    public int getValue()
    {
        return _value;
    }

    public void setValue(int _info)
    {
        this._value = _info;
    }

    public Node findValue(int value) {
    	if (this._value == value) {
    		return this;
    	}
//    	else {
//    		for (Node n : this._nodes) {
//    			return n.findValue(value);
//    		}
//    	}
    	
    	LinkedList<Node> q = new LinkedList<>();
    	q.add(this);
    	
    	while (!q.isEmpty()) {
    		Node n = q.remove();
    		if (n._value == value) {
    			return n;
    		}
    		
    		q.addAll(n._nodes);
    	}
    	
    	return null;
    }
}
