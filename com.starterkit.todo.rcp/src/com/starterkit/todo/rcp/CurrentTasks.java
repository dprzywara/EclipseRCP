package com.starterkit.todo.rcp;


import model.ModelProvider;
import model.Task;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;


public class CurrentTasks extends ViewPart {
	public static final String ID = "com.starterkit.todo.rcp.currentTasks";

	public CurrentTasks() {
		// TODO Auto-generated constructor stub
	}
	private TableViewer viewer;
	private WritableList input;
	
	public void createPartControl(Composite parent) {
	    GridLayout layout = new GridLayout(3, false);
	    parent.setLayout(layout);
	
	
	    viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL
	        | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
	    createColumns(parent, viewer);

	    final Table table = viewer.getTable();
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);

	    viewer.setContentProvider(new ArrayContentProvider());
	    input =ModelProvider.getInstance().getCurrentTasks();
	    viewer.setInput(input);
	    ViewerSupport.bind(viewer,input,BeanProperties.values(new String[]  { "id", "name", "priority", "done","description", "dueDate" }));
	    
	    
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
	    
	    

	    getSite().setSelectionProvider(viewer);

	  }



	  public TableViewer getViewer() {
	    return viewer;
	  }

	  // create the columns for the table
	  private void createColumns(final Composite parent, final TableViewer viewer) {
	    String[] titles = { "id", "Name", "priority", "done","description","dueDate" };
	    int[] bounds = { 100, 100, 100, 100,100,100 };

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
	  
	

	  private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
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