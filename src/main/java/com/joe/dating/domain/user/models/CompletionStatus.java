package com.joe.dating.domain.user.models;

/**
 * Created by Joe Deluca on 11/23/2016.
 */
public enum CompletionStatus {
    INCOMPLETE(1),
    COMPLETE(2);

    private final int id;

    CompletionStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id+"";
    }
}
