package com.minorityhobbies.mop.api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MopActionVote {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private MopAction mopAction;

    private String username;

    public MopActionVote() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MopAction getMopAction() {
        return mopAction;
    }

    public void setMopAction(MopAction mopAction) {
        this.mopAction = mopAction;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
