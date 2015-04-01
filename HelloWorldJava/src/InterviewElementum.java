
public class InterviewElementum {

	public static void main(String[] args) {
		String s = "[([]){}]";
		System.out.println(asdf(s));
	}

	private static Boolean asdf (String s) {
		if (s.length() % 2 != 0) {
			return false;		
		}
		else {
			while (s.length() >= 2)
			{
				if (s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']' ||
						s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')' ||
						s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}') {
					s = s.substring(1, s.length() - 1);
				}
				else {
					return false;
				}
			}
			return true;	
		}
	}
	/*
	 * To execute Java, please define "static void main" on a class
	 * named Solution.
	 *
	 * If you need more classes, simply define them inline.
	 */
	
	  static String reverse(String src) {
	      String output = "";
	      for (int i = 0; i < src.length(); i++) {
	        if (src.charAt(i) == ' ') {
	          
	        }
	        else {
	          do {
	            
	          }
	          while (src.charAt(i) != ' ');
	        }
	      }
	      return output;
	  }
}

	// abc def
	// a
//	     a
	/* 
	Your previous C# content is preserved below:

	using System;

	// To execute C#, please define "static void Main" on a class
	// named Solution.

	class Solution {
	  static void Main(string[] args) {
	    for (var i = 0; i < 5; i++) {
	      System.Console.WriteLine("Hello, World");
	    }
	  }
	    
	  //src=abc def
	  //return=def abc
	  static string reverse(string src) {
	      
	  }
	}

	 */
