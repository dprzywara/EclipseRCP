package model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;

public class ModelProvider {

	private WritableList tasks;
	private WritableList archive;
	private List<Task> tasksList;
	private List<Task> archiveTasksList;
	private WritableList current;
	private List<Task> currentTasksList;

	private static ModelProvider modelProvider = new ModelProvider();

	private ModelProvider() {
		tasksList = new ArrayList<Task>();
		archiveTasksList = new ArrayList<Task>();
		currentTasksList = new ArrayList<Task>();

		tasksList.add(new Task(1, "kicker", 10, false, "zagrac w kickera",
				new Data(2004, 2, 3)));
		tasksList.add(new Task(2, "gocz", 9, false, "zniszczyc gocza",
				new Data(2011, 1, 1)));
		tasksList.add(new Task(3, "fsf", 9, false, "fsdafasd", new Data(2007,
				5, 4)));
		currentTasksList.add(new Task(6, "current", 9, false, "fsdafasd",
				new Data(2015, 9, 7)));

		tasks = new WritableList(tasksList, Task.class);
		archive = new WritableList(archiveTasksList, Task.class);
		current = new WritableList(currentTasksList, Task.class);
	}

	public WritableList getTasks() {
		return tasks;
	}

	public WritableList getArchiveTasks() {
		return archive;
	}

	public WritableList getCurrentTasks() {
		return current;
	}

	public void moveToArchiv(Task task) {
		tasks.remove(task);
		archive.add(task);
	}

	public void addTask(Task task) {
		tasks.add(task);

	}

	public void addCurrent(Task task) {
		current.add(task);

	}

	public void deleteTask(Task task) {
		tasks.remove(task);
	}

	public void setDone(Task task) {

		for (Task todo : tasksList) {
			if (todo.getId() == task.getId()) {
				todo.setDone(true);
				tasks.set(tasks.indexOf(todo), todo);
			}
		}

	}

	public void editTask(Task task) {

		for (Task todo : tasksList) {
			if (todo.getId() == task.getId()) {
				todo.setDescription(task.getDescription());
				todo.setDone(task.isDone());
				todo.setDueDate(task.getDueDate());
				todo.setPriority(task.getPriority());
				todo.setName(task.getName());
				tasks.set(tasks.indexOf(todo), todo);
			}
		}
	}

	public Task getTaskById(long id) {
		for (Task todo : tasksList) {
			if (todo.getId() == id) {
				return todo;
			}
		}
		return null;
	}

	public static ModelProvider getInstance() {
		return modelProvider;
	}

}