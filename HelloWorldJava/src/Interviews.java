import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeMap;

public class Interviews {

	public static void main(String[] args) {
		//Yahoo!
		yahooExpandUrls("www.yahoo.com", 3);
		ArrayList<Integer> a = new ArrayList<>();
		ArrayList<Integer> b = new ArrayList<>();
		for (int i = 0; i < 5; i++) { a.add(i); }		
		for (int i = 3; i < 8; i++) { b.add(i); }
		b.add(10);
		yahooXoRLists(a, b);
		
		//A9.com
//		a9UrlQueryStringParams("/home/timmay/workspace/HelloWorldJava/xyz.txt");		
//		a9StackQueue q = new a9StackQueue();
//		a9StackQueue.test(q);		
//		a9StringFactorial("", "abcd");
		a9NextIntPalindrome(1342);
		a9NextIntPalindrome(-1342);
		a9NextIntPalindrome(Integer.MAX_VALUE - 1);

		//Groupon
//		String s2 = "abc def ghi";
//		System.out.println(grouponReverseWords(s2));
		
		//Elementum
//		String s1 = "[([]){}]";
//		System.out.println(elementumNestedBlocks(s1));
		
		//Apple
		appleNLongestWords("abcd a ab cdef xyz", 3); 
		
	}
	
	static void yahooExpandUrls(String prefix, int d) {
		if (d == 0) {
			System.out.println(prefix);
		}
		else {
			for (String sub : yahooGetUrls()) {
				yahooExpandUrls(prefix + "/" + sub, d - 1);
			}
		}			
	}
	
	//just a dummy function
	static ArrayList<String> yahooGetUrls() {
		ArrayList<String> output = new ArrayList<>();
		output.add("d1");
		output.add("d2");
		return output;
	}
	
	static void yahooXoRLists(ArrayList<Integer> a, ArrayList<Integer> b) {
		HashMap<Integer, Integer> hm = new HashMap<>();
		for (int aNumber : a) {
			if (!hm.containsKey(aNumber)) {
				hm.put(aNumber, 1);
			}
			else {
				hm.replace(aNumber, hm.get(aNumber) + 1);
			}
		}
		for (int bNumber : b) {
			if (!hm.containsKey(bNumber)) {
				hm.put(bNumber, 1);
			}
			else {
				hm.replace(bNumber, hm.get(bNumber) + 1);
			}
		}
		for (int key : hm.keySet()) {
			if (hm.get(key) == 1) {
				System.out.print(key + ", ");
			}
		}
	}
	
    static void a9UrlQueryStringParams(String fileName) {
    	final String patternId = "id=";
    	final String patternName = "name=";
    	TreeMap<String, String> count = new TreeMap<>();
    	try {	
	    	BufferedReader br = new BufferedReader(new FileReader(fileName));
	    	try {    		
	    		String line = null;
	    		int lineNumber = 1;
	    		while ((line = br.readLine()) != null) {
	    			String id = line.substring(line.indexOf(patternId) + patternId.length(), line.indexOf(patternId)  + patternId.length() + 4);
	    			String name = line.substring(line.indexOf(patternName) + patternName.length(), line.indexOf(patternName)  + patternName.length() + 3);
	    			if (!count.containsKey(id + " " + name)) {
	    				count.put(id + " " + name, lineNumber + ", ");
	    			}
	    			else {
	    				count.replace(id + " " + name, count.get(id + " " + name) + lineNumber + ", ");
	    			}
	    			lineNumber++;
	    		}
	    		System.out.println(count);
	    	}
	    	catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	    	} 	    	
	    	finally {
	    		if (br != null) {
	    			br.close();
	    		}
	    	}
    	}
    	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
    
    static void a9StringFactorial(String prefix, String s) {    	
    	int n = s.length();
    	if (n == 1) {
    		System.out.println(prefix + s);
    	}
    	else {
    		for (int i = 0; i < n; i++) {
    			a9StringFactorial(prefix + s.charAt(i), s.substring(0, i) + s.substring(i + 1, n));    			
    		}    		 
    	}
    }
    
    static void a9NextIntPalindrome(int i) {
    	for (i++; i < Integer.MAX_VALUE; i++) {
    		if (isPalindrome(i)) {
    			System.out.println(i);
    			return;
    		}
    	}
    	System.out.println("next palindrome not found");
    }
    
    static Boolean isPalindrome(int i) {
    	return i == reverseNumber(i) ? true : false;
    }
    
    static int reverseNumber(int i) {
    	int output = 0;    	
    	do {
    		output = (output * 10) + i % 10;
    		i /= 10;
    	} while (i != 0);
    	return output;
    }
    static String grouponReverseWords(String str)
    {
	    int word_length = 0;
	    StringBuilder result = new StringBuilder("");
	    for (int i=0; i<str.length(); i++)
	    {
	        if (str.charAt(i) == ' ')
	        {
	            result.insert(0, " ");
	            word_length = 0;
	        } else 
	        {
	            result.insert(word_length, str.charAt(i));
	            word_length++;
	        }
	    }
	    return result.toString();   
    }    

	private static Boolean elementumNestedBlocks(String s) {
		if (s.length() % 2 != 0) {
			return false;		
		}
		else {
			Stack<Character> blocks = new Stack<>();
			for (int i = 0; i < s.length(); i++)
			{
				Character c = s.charAt(i);
				if (c == '[' || c == '{' || c == '(') {
					blocks.push(c);
				}
				else if (c == ']') {
					if (blocks.peek() == '[') {
						blocks.pop();
					}
					else {
						return false;
					}
				}
				else if (c == '}') {
					if (blocks.peek() == '{') {
						blocks.pop();
					}
					else {
						return false;
					}
				}
				else if (c == ')') {
					if (blocks.peek() == '(') {
						blocks.pop();
					}
					else {
						return false;
					}
				}
			}
			return true;	
		}
	}	

	static void appleNLongestWords(String s, int n) {	
		Comparator<String> lengthCompare = new Comparator<String>() {
			@Override public int compare(String a, String b) { return a.length() - b.length(); }
		};
		PriorityQueue<String> pq = new PriorityQueue<>(lengthCompare);
		String[] words = s.split("\\s+");
		int i = 0;
		if (n > 0) {			
			while (i < n) {
				pq.add(words[i]);
				i++;
			}
		}
		
		for (; i < words.length; i++) {
			if (words[i].length() >= pq.peek().length()) {
				pq.remove();
				pq.add(words[i]);
			}
		}				
		System.out.println(pq);
	}
}

class a9StackQueue {
	private Stack<Integer> queue;
	private Stack<Integer> internalStack;
	
	public a9StackQueue() {
		queue = new Stack<>();
    	internalStack = new Stack<>();
	}
	
	public void printQ () {
		System.out.println(queue);
	}
	
	static public void test (a9StackQueue q) {
		q.add(1);
		q.add(2);
		q.add(3);
		q.remove();		
		q.add(4);
		q.printQ();
		q.remove();
		q.remove();
		q.printQ();
		q.remove();
		q.printQ();
	}
	public void add (int i) {
		queue.push(i);
	}
	
	public void remove() {
		while (!queue.isEmpty()) {
			internalStack.push(queue.pop());
		}
		internalStack.pop();
		while (!internalStack.isEmpty()) {
			queue.push(internalStack.pop());
		}
	}
	
	public int peek() {
		while (!queue.isEmpty()) {
			internalStack.push(queue.pop());
		}
		int output = internalStack.peek();
		internalStack = new Stack<>();
		return output;
	}
}