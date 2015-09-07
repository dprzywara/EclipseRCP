package com.starterkit.todo.rcp;

import model.ModelProvider;
import model.Task;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

public class AllTasks extends ViewPart {
	public AllTasks() {
	}

	public static final String ID = "com.starterkit.todo.rcp.AllTasks";

	private TableViewer viewer;
	private WritableList input;

	

	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(3, false);
		parent.setLayout(layout);
		Label optionLabel = new Label(parent, SWT.NONE);

		optionLabel.setText("Options: ");

		Button btnToarchive = new Button(parent, SWT.NONE);
		btnToarchive.setText("toArchive");

		btnToarchive.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toArchive();
			}
		});
		new Label(parent, SWT.NONE);

		Button delete = new Button(parent, SWT.PUSH);
		delete.setText("Delete");
		delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteTODO();
			}

		});

		Button btnAddTask = new Button(parent, SWT.PUSH);
		btnAddTask.setText("Add task");
		btnAddTask.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addTask();
			}

		});

		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);

		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		// table.set

		viewer.setContentProvider(new ArrayContentProvider());
		input = ModelProvider.getInstance().getTasks();
		viewer.setInput(input);
		ViewerSupport.bind(viewer,input,BeanProperties.values(new String[] { "id", "name", "priority",
						"done", "description", "dueDate" }));

		// make the selection available to other views
		getSite().setSelectionProvider(viewer);

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

		// Create a menu manager and create context menu
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(viewer.getTable());
		// set the menu on the SWT widget
		viewer.getTable().setMenu(menu);
		// register the menu with the framework
		getSite().registerContextMenu(menuManager, viewer);

		// make the viewer selection available
		getSite().setSelectionProvider(viewer);
		hookDoubleClickCommand();

	}
	
	private void deleteTODO() {
		if (!viewer.getSelection().isEmpty()) {
			IStructuredSelection selection = (IStructuredSelection) viewer
					.getSelection();
			Task p = (Task) selection.getFirstElement();
			input.remove(p);
		}
	}
	
	private void toArchive() {
		if (!viewer.getSelection().isEmpty()) {
			IStructuredSelection selection = (IStructuredSelection) viewer
					.getSelection();
			Task p = (Task) selection.getFirstElement();
			ModelProvider.getInstance().moveToArchiv(p);
		}
	}
	
	private void addTask() {
		Shell shell = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell();
		AddTaskDialog dialog = new AddTaskDialog(shell);
		dialog.create();
		if (dialog.open() == Window.OK) {
			int id = ModelProvider.getInstance().getTasks().size();
			Task todo = new Task(id + 1, dialog.getName(), dialog
					.getPriority(), dialog.getDone(), dialog
					.getDescription(), dialog.getDate());
			if (dialog.isCurrent()) {
				ModelProvider.getInstance().addCurrent(todo);
			} else {
				ModelProvider.getInstance().addTask(todo);
			}

		}
	}



	private void hookDoubleClickCommand() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IHandlerService handlerService = (IHandlerService) getSite()
						.getService(IHandlerService.class);
				try {
					handlerService.executeCommand(
							"com.starterkit.todo.rcp.openTaskEditor", null);
				} catch (Exception ex) {
					throw new RuntimeException(ex.getMessage());
				}
			}
		});
	}

	public TableViewer getViewer() {
		return viewer;
	}

	// create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "id", "Name", "priority", "done", "description",
				"dueDate" };
		int[] bounds = { 100, 100, 100, 100, 100, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task p = (Task) element;
				return Integer.toString(p.getId());
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task p = (Task) element;
				return p.getName();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task p = (Task) element;
				return Integer.toString(p.getPriority());
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task p = (Task) element;
				return Boolean.toString(p.isDone());
			}
		});
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task p = (Task) element;
				return p.getDescription();
			}
		});
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Task p = (Task) element;
				return p.getDueDate().toString();
			}
		});

	}
	public void moveTask() {
		if (!viewer.getSelection().isEmpty()) {
			IStructuredSelection selection = (IStructuredSelection) viewer
					.getSelection();
			Task p = (Task) selection.getFirstElement();
			input.remove(p);
		}
	}

	
	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}
}