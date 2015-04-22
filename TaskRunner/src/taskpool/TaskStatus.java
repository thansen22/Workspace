package taskpool;

public class TaskStatus {
	
	// TODO: create constructor with the normal fields
	
	enum Status {
		COMPLETE, QUEUED, RUNNING
	}
	
	enum Result {
		OK, FAIL
	}
	
	/**
	* The status of the given task (use enum constants)
	*/
	private Status status;
	
	/**
	* The result of the given task if completed (use enum constants)
	*/
	private Result result;
	
	/**
	* The stdout output if capture was requested.
	*/
	private String stdout;
	
	/**
	* The stderr output if capture was requested.
	*/
	private String stderr;
	
	/**
	* A string of return code and command that failed if command
	* failed.
	*/
	private String info;
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Result getResult() {
		return result;
	}
	
	public void setResult(int result) {
		if (result == 0) {
			this.result = Result.OK;
		}
		else {
			this.result = Result.FAIL;
		}
	}
	
	public String getStdout() {
		return stdout;
	}
	
	public void setStdout(String stdout) {
		this.stdout = stdout;
	}
	
	public String getStderr() {
		return stderr;
	}
	
	public void setStderr(String stderr) {
		this.stderr = stderr;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
}
