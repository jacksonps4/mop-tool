package com.minorityhobbies.mop.api;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Mop {
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Mop() {
    }

    public Mop(String title, String createdBy) {
        this.title = title;
        this.createdBy = createdBy;
        this.created = new Date();
    }

    public Mop(String title, Date created) {
        this.title = title;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
