package taskpool;

import java.util.ArrayList;
import java.util.List;

public class TestMyTaskRunner {

	public static void main(String[] args) {
		// TODO: add some commands, or rewrite as a test framework if time permitting		
		MyTaskRunner runner = new MyTaskRunner();
		List<String> commands = new ArrayList<>();
		commands.add("ping www.google.com");
		Task task1 = new Task(commands);
		task1.setCaptureOutput(true);
		runner.addTask(task1);
		
		commands.remove(0);
		commands.add("ping www.bing.com");
		Task task2 = new Task(commands);
		runner.addTask(task2);
		
		// only appears to be outputting the first task
		runner.start();
	}
}
