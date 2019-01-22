package com.haulmont.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|title")
@Table(name = "APP_PROJECT")
@Entity(name = "app$Project")
public class Project extends StandardEntity {
    private static final long serialVersionUID = -775851822703007638L;

    @Column(name = "TITLE")
    protected String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


}