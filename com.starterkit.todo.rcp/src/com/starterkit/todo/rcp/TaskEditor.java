package com.starterkit.todo.rcp;

import model.Data;
import model.ModelProvider;
import model.Task;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

public class TaskEditor extends EditorPart {
	
	public static final String ID = "com.starterkit.todo.rcp.taskEditor";
	
	private Task todo;
	  private TaskEditorInput input;
	  private boolean isDirty;
	  
	  
	  
	  private Text txtName;
	  private Text txtDescription;
	  private Spinner txtPriority;
	  private Button txtDone;
	  private DateTime txtData;

	  
	public TaskEditor() {
	}


	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		
		if (!(input instanceof TaskEditorInput)) {
		      throw new RuntimeException("Wrong input");
		    }

		    this.input = (TaskEditorInput) input;
		    setSite(site);
		    setInput(input);
		    todo = ModelProvider.getInstance().getTaskById(this.input.getId());
		    setPartName("Todo ID: " + todo.getId());

	}

	 @Override
	  public void createPartControl(Composite parent) {
	    GridLayout layout = new GridLayout();
	    layout.numColumns = 2;
	    parent.setLayout(layout);
	    new Label(parent, SWT.NONE).setText("Name");
	    txtName = new Text(parent, SWT.BORDER);
	    txtName.setText(todo.getName());
	    txtName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	    
	    new Label(parent, SWT.NONE).setText("Description");
	    txtDescription = new Text(parent, SWT.BORDER);
	    txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	    txtDescription.setText(todo.getDescription());
	    
	    new Label(parent, SWT.NONE).setText("Done");
	    txtDone = new Button(parent, SWT.CHECK);
	    txtDone.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	    txtDone.setSelection(todo.isDone());
	    
	    Label lbtDescription = new Label(parent, SWT.NONE);
		  lbtDescription.setText("priority");
		  GridData dataPriority = new GridData();
		  dataPriority.grabExcessHorizontalSpace = true;
		  dataPriority.horizontalAlignment = GridData.BEGINNING;
		  txtPriority = new Spinner(parent, SWT.NONE);
		  txtPriority.setSelection(todo.getPriority());
		  txtPriority.setLayoutData(dataPriority);
	    
	    new Label(parent, SWT.NONE).setText("Due Date");
	    txtData = new DateTime(parent, SWT.CALENDAR);
	    txtData.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	    Data date = todo.getDueDate();
	    txtData.setDate(date.getYear(), date.getMonth(), date.getDay());
	    
	    
	    Button btnSave= new Button(parent, SWT.PUSH);
	    btnSave.setText("Save");
	    btnSave.addSelectionListener(new SelectionAdapter() {
    		
    		
  	      @Override
  	      public void widgetSelected(SelectionEvent e) {
  	    	  Data d= new Data(txtData.getYear(),txtData.getMonth(),txtData.getDay());
  	    	ModelProvider.getInstance().editTask(new Task(todo.getId(), txtName.getText(),txtPriority.getSelection(),txtDone.getSelection(),txtDescription.getText(),d));
  	    	        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
  	    	                .getActivePage().closeEditor(TaskEditor.this, true);

  	      
  	      }
  	      
  	  
 
    	});
	    
	    
	  }

	  @Override
	  public void doSave(IProgressMonitor monitor) {	  
		  
	  }

	  @Override
	  public void doSaveAs() {
	  }

	  @Override
	  public boolean isDirty() {
	      return false;
	  }
	  
	  protected void setDirty(boolean newValue) {
		    if (isDirty != newValue) {
		        isDirty = newValue;
		        firePropertyChange(PROP_DIRTY);
		    }
		}

	  @Override
	  public boolean isSaveAsAllowed() {
	    return false;
	  }

	  @Override
	  public void setFocus() {
	  }
	  
	

}
