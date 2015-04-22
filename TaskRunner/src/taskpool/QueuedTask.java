package taskpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class QueuedTask implements Runnable {
	private int taskId;
	private TaskStatus.Status status;
	private TaskStatus taskStatus;
	private Task task;
	private int queuePosition;
	
	public QueuedTask() {
		taskStatus = new TaskStatus();
	}
	
	public QueuedTask(Task task) {
		taskStatus = new TaskStatus();
		this.task = task;
	}
	
	public int getTaskId() {
		return taskId;
	}
	
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	public TaskStatus.Status getStatus() {
		return status;
	}
	
	public void setStatus(TaskStatus.Status status) {
		this.status = status;
	}
	
	public Task getTask() {
		return task;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public int getQueuePosition() {
		return queuePosition;
	}
	
	public void setQueuePosition(int queuePosition) {
		this.queuePosition = queuePosition;
	}
	
	@Override
	public void run() {		
		for (String command : task.getCommands()) {
			try {				
				Process child = Runtime.getRuntime().exec(command);
				taskStatus.setStatus(TaskStatus.Status.RUNNING);
				
				getOuput(child);
				
				// wait for the command to complete
				taskStatus.setResult(child.waitFor());
				taskStatus.setStatus(TaskStatus.Status.COMPLETE);
							
				// if anything command doesn't return 0 we are supposed to stop executing more commands
				if (taskStatus.getResult() != TaskStatus.Result.OK) {
					break;
				}	
				
			} catch (IOException e) {
				if (task.getCaptureOutput()) {
					e.printStackTrace();
				}
				// if anything goes wrong we are supposed to stop executing more commands
				break;
			} catch (InterruptedException e) {
				if (task.getCaptureOutput()) {
					e.printStackTrace();
				}
				// if anything goes wrong we are supposed to stop executing more commands
				break;
			} finally {
				// TODO: may have some other cleanup				
			}
		}
	}

	private void getOuput(Process child) throws IOException {
		if (task.getCaptureOutput()) {
			// TODO: need to put these into TaskStatus
			InputStream stderr = child.getErrorStream();
			InputStream stdout = child.getInputStream();
			BufferedReader readerOut = new BufferedReader (new InputStreamReader(stdout));
			BufferedReader readerErr = new BufferedReader (new InputStreamReader(stderr));
			
			String line;
			
			String stdoutStr = "";
			while ((line = readerOut.readLine ()) != null) {
				// TODO: remove this as we can use the TaskStatus interface instead, but I don't see the need for the abstraction    
				System.out.println ("Stdout: " + line);
				stdoutStr += line + "\n";
			}
			// TODO: setting this, but not utilizing it yet elsewhere
			taskStatus.setStdout(stdoutStr);
		
			String stderrStr = "";
			while ((line = readerErr.readLine ()) != null) {
				// TODO: remove this as we can use the TaskStatus interface instead, but I don't see the need for the abstraction
				System.out.println ("Stdout: " + line);
				stderrStr += line + "\n";
			}
			// TODO: setting this, but not utilizing it yet elsewhere
			taskStatus.setStderr(stderrStr);
			
			child.getErrorStream().close();
			child.getInputStream().close();
		}
	}
}
