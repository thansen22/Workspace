
public class SingletonClass {
	private static SingletonClass singleObject;
	
	static {
		singleObject = new SingletonClass();
	}
	
	public SingletonClass()	{
	
	}
	
	public static SingletonClass getInstance()
	{
		return singleObject;
	}
	
	public void test() {
		System.out.println("asdf");
	}
}
