package com.minorityhobbies.mop.api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MopAction {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Mop mop;
    private String action;

    public MopAction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Mop getMop() {
        return mop;
    }

    public void setMop(Mop mop) {
        this.mop = mop;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MopAction mopAction = (MopAction) o;

        return id == mopAction.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
