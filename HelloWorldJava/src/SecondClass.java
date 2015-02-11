
public class SecondClass {
	// must be static to be visible to nested class
	private final static int modifier = 1;
	public String name;
	
	public SecondClass (String n)
	{
		name = n;
	}
	
	public void calculate(int input)
	{
		System.out.println("Here's your number modified by SecondClass: " + (input + modifier));
	}
	
	public static class StaticNestedClass {
		// polymorphism kicks in here and overrides the parent class usage, even though it would otherwise be visible with static modifier
		private final static int modifier = 2;
		
		public static void calculate(int input)
		{
			System.out.println("Here's your number modified by StaticNestedClass: " + (input + modifier));
		}
	}
}
