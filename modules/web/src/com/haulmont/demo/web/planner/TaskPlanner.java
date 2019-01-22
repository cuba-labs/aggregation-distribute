package com.haulmont.demo.web.planner;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.GroupTable.GroupAggregationDistributionContext;
import com.haulmont.cuba.gui.components.Table.AggregationDistributionContext;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.demo.entity.Task;
import com.haulmont.demo.entity.TaskType;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

import static com.haulmont.demo.entity.TaskType.*;

public class TaskPlanner extends AbstractLookup {
    @Inject
    private GroupTable<Task> tasksTable;

    public static final String ESTIMATION_PROPERTY = "estimation";
    public static final String EXAMPLE_TWO_PROPERTY = "exampleTwo";
    public static final String EXAMPLE_THREE_PROPERTY = "exampleThree";

    public static final String GROUP_TYPE_PROPERTY = "type";

    public static final int PRIORITY_DIVIDER = 5;

    @Override
    public void ready() {
        tasksTable.expandAll();
    }

    @Override
    public void init(Map<String, Object> params) {
        tasksTable.setAggregationDistributionProvider(context -> {
            String columnId = context.getColumnId();

            if (ESTIMATION_PROPERTY.equals(columnId)) {
                distributeEstimation(context);
            } else if (EXAMPLE_TWO_PROPERTY.equals(columnId)) {
                distributeExampleTwo(context);
            } else if (EXAMPLE_THREE_PROPERTY.equals(columnId)) {
                distributeExampleThree(context);
            }
        });
    }

    protected void distributeEstimation(AggregationDistributionContext<Task> context) {
        Collection<Task> scope = context.getScope();
        if (scope.isEmpty()) {
            return;
        }

        double value = context.getValue() != null ?
                ((double) context.getValue()) : 0;

        if (context.isTotalAggregation()) {
            int primaryCount = getEstimationTypeCount(scope, PRIMARY);
            double valuePerPrimary = 0;
            if (primaryCount != 0) {
                valuePerPrimary = (value * 0.5) / primaryCount;
            }

            int secondaryCount = getEstimationTypeCount(scope, SECONDARY);
            double valuePerSecondary = 0;
            if (secondaryCount != 0) {
                valuePerSecondary = (value * 0.3) / secondaryCount;
            }

            int deferredCount = getEstimationTypeCount(scope, DEFERRED);
            double valuePerDeferred = 0;
            if (deferredCount != 0) {
                valuePerDeferred = (value * 0.2) / deferredCount;
            }

            for (Task task : scope) {
                if (task.getType() == PRIMARY) {
                    task.setEstimation(valuePerPrimary);
                } else if (task.getType() == SECONDARY) {
                    task.setEstimation(valuePerSecondary);
                } else if (task.getType() == DEFERRED) {
                    task.setEstimation(valuePerDeferred);
                }
            }
        } else {
            if (ESTIMATION_PROPERTY.equals(context.getColumnId())) {
                int scopeSize = scope.size();
                double valuePerTask = value / scopeSize;

                for (Task task : scope) {
                    task.setEstimation(valuePerTask);
                }
            }
        }
    }

    protected void distributeExampleTwo(AggregationDistributionContext<Task> context) {
        double value = context.getValue() != null ?
                (double) context.getValue() : 0;

        Collection<Task> scope = context.getScope();
        if (scope.isEmpty()) {
            return;
        }
        int scopeSize = scope.size();

        if (context.isTotalAggregation()) {
            double valuePerTask = value / scopeSize;
            for (Task task : scope) {
                task.setExampleTwo(valuePerTask);
            }
        } else {
            GroupAggregationDistributionContext groupContext = (GroupAggregationDistributionContext) context;

            if (GROUP_TYPE_PROPERTY.equals(groupContext.getGroupPropertyPath())) {
                GroupInfo groupInfo = groupContext.getGroupInfo();
                TaskType taskType = (TaskType) groupInfo.getValue();

                if (PRIMARY.equals(taskType) || SECONDARY.equals(taskType)) {
                    int aboveSize = getScopeSizeByPriority(true, scope);
                    double valuePerAbove = (value * 0.7) / aboveSize;

                    int belowSize = getScopeSizeByPriority(false, scope);
                    double valuePerBelow = (value * 0.3) / belowSize;

                    for (Task task : scope) {
                        task.setExampleTwo(task.getPriority() >= 5 ? valuePerAbove : valuePerBelow);
                    }
                } else if (DEFERRED.equals(taskType)) {
                    double valuePerTask = value / scopeSize;
                    for (Task task : scope) {
                        task.setExampleTwo(valuePerTask);
                    }
                }
            }
        }
    }

    protected void distributeExampleThree(AggregationDistributionContext<Task> context) {
        long value = context.getValue() != null ?
                (long) context.getValue() : 0;

        Collection<Task> scope = context.getScope();
        if (scope.isEmpty()) {
            return;
        }
        int scopeSize = scope.size();

        int valuePerTask = (int) value / scopeSize;
        int valueForFirstTask = valuePerTask;
        if (value % scopeSize != 0) {
            valueForFirstTask = (int) (value % scopeSize) + valuePerTask;
        }

        boolean firstItem = true;
        for (Task task : scope) {
            task.setExampleThree(valuePerTask);
            if (firstItem) {
                task.setExampleThree(valueForFirstTask);
                firstItem = false;
            }
        }
    }

    protected int getScopeSizeByPriority(boolean moreThan, Collection<Task> scope) {
        int count = 0;
        for (Task task : scope) {
            if (moreThan) {
                if (task.getPriority() >= PRIORITY_DIVIDER) {
                    count++;
                }
            } else if (task.getPriority() < PRIORITY_DIVIDER) {
                count++;
            }
        }
        return count;
    }

    protected int getEstimationTypeCount(Collection<Task> tasks, TaskType taskType) {
        int count = 0;
        for (Task task : tasks) {
            if (task.getType() == taskType) {
                count++;
            }
        }
        return count;
    }

    public void save() {
        getDsContext().commit();
    }
}