package com.haulmont.demo.web.task;

import com.haulmont.cuba.gui.WindowManager.OpenType;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.demo.entity.Task;

import javax.inject.Inject;

public class TaskBrowse extends AbstractLookup {
    @Inject
    private GroupTable<Task> tasksTable;

    @Override
    public void ready() {
        tasksTable.expandAll();
    }

    public void showPlanner() {
        openWindow("app$Task.planner", OpenType.NEW_TAB);
    }
}