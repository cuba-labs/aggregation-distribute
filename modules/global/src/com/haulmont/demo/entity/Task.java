package com.haulmont.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NamePattern("%s|name")
@Table(name = "APP_TASK")
@Entity(name = "app$Task")
public class Task extends StandardEntity {
    private static final long serialVersionUID = 7361300679274045675L;

    @Column(name = "NAME")
    protected String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    protected Project project;

    @Column(name = "EXAMPLE_TWO")
    protected Double exampleTwo;

    @Column(name = "EXAMPLE_THREE")
    protected Integer exampleThree;

    @Column(name = "ESTIMATION")
    protected Double estimation;

    @Column(name = "TIME_CONSUMED")
    protected Integer timeConsumed;

    @Column(name = "TYPE_")
    protected Integer type;

    @Max(10)
    @Column(name = "PRIORITY")
    protected Integer priority;

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }


    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }


    public void setExampleTwo(Double exampleTwo) {
        this.exampleTwo = exampleTwo;
    }

    public Double getExampleTwo() {
        return exampleTwo;
    }

    public void setExampleThree(Integer exampleThree) {
        this.exampleThree = exampleThree;
    }

    public Integer getExampleThree() {
        return exampleThree;
    }


    public Double getEstimation() {
        return estimation;
    }

    public void setEstimation(Double estimation) {
        this.estimation = estimation;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTimeConsumed(Integer timeConsumed) {
        this.timeConsumed = timeConsumed;
    }

    public Integer getTimeConsumed() {
        return timeConsumed;
    }

    public void setType(TaskType type) {
        this.type = type == null ? null : type.getId();
    }

    public TaskType getType() {
        return type == null ? null : TaskType.fromId(type);
    }


}