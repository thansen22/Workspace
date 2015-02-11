import javax.swing.JOptionPane;

public class ReferencesAndClasses {

	public static void main(String[] args) {	
		// Get input
		try
		{
			// use a dialog for input
			int userInput = Integer.parseInt(JOptionPane.showInputDialog("Please enter a number."));
			SecondClass sc = new SecondClass("A");
			sc.calculate(userInput);
			SecondClass.StaticNestedClass.calculate(userInput);						
	    } catch (final NumberFormatException e) {
	        System.err.println("ERROR: Invalid input. Please type in a numerical value.");
	    }		
		
		// Nested classes
		SecondClass sc = new SecondClass("A");
		changeName(sc);
		System.out.println("Original reference changed in changeName: " + sc.name);	
		
		// SingletonClass
		SingletonClass singleC = SingletonClass.getInstance();
		singleC.test();
		
		// Swap values without a temp var
		int a = 10;
		int b = 20;
		
		System.out.println(String.format("a: %d, b: %d", a, b));
		a = a + b;
		b = a - b;
		a = a - b;
		System.out.println(String.format("a: %d, b: %d", a, b));
	}

	public static void changeName(SecondClass sc)
	{
		// modifying the original reference passed into the function
		sc.name = "B";
		// creating a completely new object
		sc = new SecondClass("C");
		// modifying the new object
		sc.name = "D";
		System.out.println("New SecondClass name changed locally: " + sc.name);
	}

}


