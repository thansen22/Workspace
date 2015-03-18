
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
}
