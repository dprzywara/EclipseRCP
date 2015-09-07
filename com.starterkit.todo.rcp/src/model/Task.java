package model;

import java.beans.PropertyChangeSupport;

public class Task extends ModelObject {

	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public static final String FIELD_ID = "id";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_DESCRIPTION = "description";
	public static final String FIELD_DONE = "done";
	public static final String FIELD_DUEDATE = "dueDate";
	public static final String FIELD_PRIORITY = "priority";

	private int id;
	private String name;
	private int priority;
	private boolean done;
	private Data dueDate;
	private String description;

	public Task(int id, String name, int priority, boolean state,
			String description, Data date) {
		super();
		this.id = id;
		this.name = name;
		this.priority = priority;
		this.done = state;
		this.description = description;
		this.dueDate = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		firePropertyChange(FIELD_ID, this.id, this.id = id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		firePropertyChange(FIELD_NAME, this.name, this.name = name);
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		firePropertyChange(FIELD_PRIORITY, this.priority,
				this.priority = priority);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		firePropertyChange(FIELD_DESCRIPTION, this.description,
				this.description = description);
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean isDone) {
		firePropertyChange(FIELD_DONE, this.done, this.done = isDone);
	}

	public Data getDueDate() {
		return dueDate;
	}

	public void setDueDate(Data dueDate) {
		changes.firePropertyChange(FIELD_DUEDATE, this.dueDate,
				this.dueDate = dueDate);
	}
	
	
	 @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + (int) (id ^ (id >>> 32));
	    return result;
	  }

	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    Task other = (Task) obj;
	    if (id != other.id)
	      return false;
	    return true;
	  }

	  @Override
	  public String toString() {
	    return "Task [id=" + id + ", name=" + name + "]";
	  }

	  public Task copy() {
	    return new Task(id, name,priority, done,description, dueDate);
	  }
	  
	  

}
