package com.starterkit.todo.rcp;

import java.util.Date;

import model.Data;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class AddTaskDialog extends TitleAreaDialog {

	private Text txtName;
	private Text txtDescription;
	private Spinner txtPriority;
	private Button txtDone;
	private DateTime txtData;

	private String name;
	private String description;
	private int priority;
	private boolean done;
	private Data data;
	private boolean isCurrent;

	public AddTaskDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Add new Task");
		setMessage("New Task Dialog", IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createFirstName(container);
		createDescription(container);
		createPriority(container);
		createDone(container);
		createDueDate(container);

		return area;
	}

	private void createFirstName(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
		lbtFirstName.setText("Name");

		GridData dataName = new GridData();
		dataName.grabExcessHorizontalSpace = true;
		dataName.horizontalAlignment = GridData.BEGINNING;

		txtName = new Text(container, SWT.BORDER);
		txtName.setLayoutData(dataName);
	}

	private void createDescription(Composite container) {
		Label lbtDescription = new Label(container, SWT.NONE);
		lbtDescription.setText("Description");

		GridData dataDescription = new GridData();
		dataDescription.grabExcessHorizontalSpace = true;
		dataDescription.horizontalAlignment = GridData.FILL;
		txtDescription = new Text(container, SWT.BORDER);
		txtDescription.setLayoutData(dataDescription);
	}

	private void createPriority(Composite container) {
		Label lbtDescription = new Label(container, SWT.NONE);
		lbtDescription.setText("priority");

		GridData dataPriority = new GridData();
		dataPriority.grabExcessHorizontalSpace = true;
		dataPriority.horizontalAlignment = GridData.BEGINNING;
		txtPriority = new Spinner(container, SWT.NONE);
		txtPriority.setLayoutData(dataPriority);
	}

	private void createDone(Composite container) {
		Label lbtDescription = new Label(container, SWT.NONE);
		lbtDescription.setText("Done");

		GridData dataDone = new GridData();
		dataDone.grabExcessHorizontalSpace = true;
		dataDone.horizontalAlignment = GridData.BEGINNING;
		txtDone = new Button(container, SWT.CHECK);
		txtDone.setLayoutData(dataDone);
	}

	private void createDueDate(Composite container) {
		Label lbtDate = new Label(container, SWT.NONE);
		lbtDate.setText("due Date");

		txtData = new DateTime(container, SWT.CALENDAR);
		txtData.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void saveInput() {
		name = txtName.getText();
		description = txtDescription.getText();
		priority = txtPriority.getSelection();
		done = txtDone.getSelection();
		data = new Data(txtData.getYear(), txtData.getMonth(), txtData.getDay());
		Date crt = new Date();

		  
		  
	
		if((crt.getYear()==txtData.getYear()-1900)&& (crt.getMonth()==txtData.getMonth())&& (crt.getDate()== txtData.getDay())){
			isCurrent=true;
		}
		else{
			isCurrent=false;
		}
		

	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getName() {
		return name;
	}
	public boolean isCurrent() {
		return isCurrent;
	}

	public int getPriority() {
		return priority;
	}

	public boolean getDone() {
		return done;
	}

	public Data getDate() {
		return data;
	}

	public String getDescription() {
		return description;
	}
}