package com.haulmont.demo.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum TaskType implements EnumClass<Integer> {

    PRIMARY(10),
    SECONDARY(20),
    DEFERRED(30);

    private Integer id;

    TaskType(Integer value) {
        this.id = value;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Nullable
    public static TaskType fromId(Integer id) {
        for (TaskType at : TaskType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}