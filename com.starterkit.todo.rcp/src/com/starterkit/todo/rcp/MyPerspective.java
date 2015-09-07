package com.starterkit.todo.rcp;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class MyPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
		IFolderLayout folder=layout.createFolder("com.starterkit.todo.rcp.folder",IPageLayout.LEFT,0.382f,layout.getEditorArea());
		  folder.addView(AllTasks.ID);
		  folder.addView(CurrentTasks.ID);
		  folder.addView(Archive.ID);
			  
		
	}

}
