package com.starterkit.todo.rcp.handler;

import java.util.Iterator;

import model.ModelProvider;
import model.Task;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddTask implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	    ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
	        .getActivePage().getSelection();
	    if (selection != null & selection instanceof IStructuredSelection) {
	      IStructuredSelection strucSelection = (IStructuredSelection) selection;
	      for (Iterator<Task> iterator = strucSelection.iterator(); iterator
	          .hasNext();) {
	        Task element = iterator.next();
	        ModelProvider.getInstance().moveToArchiv(element);
	      }
	    }
	    return null;
	  }

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}
}


