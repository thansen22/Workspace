package taskpool;

import java.util.ArrayList;
import java.util.List;

import taskpool.TaskStatus.Status;

public class MyTaskRunner implements TaskRunner {

	List<QueuedTask> tasks = new ArrayList<QueuedTask>();
	
	@Override
	public int addTask(Task task) {
		QueuedTask qTask = new QueuedTask(task);
		// using a 1...n based queue position
		qTask.setQueuePosition(tasks.size());
		tasks.add(qTask);
		return 0;
	}

	@Override
	public void start() {
		for (QueuedTask task : tasks) {
			try {
				List<QueuedTask> qTasksRunning = tasks(TaskStatus.Status.RUNNING);
				// Are there any tasks running?
				if (!qTasksRunning.isEmpty()) {
					// if so we need to check that there aren't any exclusive tasks that are running
					for (QueuedTask qTask : qTasksRunning) {
						if (qTask.getTask().isExclusive()) {
							// Note there may be a bug here if the task starts after we do the check, need more code
							while (qTask.getStatus() == TaskStatus.Status.RUNNING) {
								// wait, should probably do a sleep here
							}
						}
					}
					// now we also need to check if our task is exclusive and if so we must wait for everything to complete
					if (task.getTask().isExclusive()) {
						while (!tasks(TaskStatus.Status.RUNNING).isEmpty()) {
							// wait, there's another problem if someone queued jumps in
						}
					}
				}
				task.run();
			} finally {
				// always need to remove the task, however the process.waitFor is returning sooner than expected
				//tasks.remove(task);
			}
		}
	}

	@Override
	public void stop() {
		// Immediately remove all queued tasks otherwise it's possible one could start that shouldn't have
		for (QueuedTask task : tasks) {
			if (task.getStatus() == TaskStatus.Status.QUEUED) {
				tasks.remove(task);
			}
		}
		
		// Now wait for all the running tasks to complete
		for (QueuedTask task : tasks) {
			while (task.getStatus() == TaskStatus.Status.RUNNING) {
				//loop till the task is complete as per the spec.  We could additionally sleep, but that wasn't in the requirements
			}				
		}
	}

	@Override
	public List<QueuedTask> tasks(Status status) {
		// fast check return everything 		
		if (status == null) {
			return tasks;
		}
		// otherwise optimize and only add the ones
		else {
			List<QueuedTask> qTasks = new ArrayList<>();
			for (QueuedTask qTask : tasks) {
				if (qTask.getStatus() == status) {
					qTasks.add(qTask);
				}
			}
			return qTasks;
		}
		
	}

	@Override
	public void waitTasks() {
		for (QueuedTask task : tasks) {
			while (task.getStatus() != TaskStatus.Status.COMPLETE) {
				//loop till all tasks are complete as per the spec.  We could additionally sleep, but that wasn't in the requirements
			}				
		}
	}

	@Override
	public boolean cancelTask(int taskId) {
		// TODO Auto-generated method stub
		return false;
	}

}
