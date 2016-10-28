package com.example.sfirstapp.model;

import java.util.Date;

/**
 * Created by Moritz on 19.10.2016.
 */

public class Recording {
    public Date creationDate;
    public String assocContact;
    public String path;
    public String title;

    public Recording(String title, String contact, String path, Date date) {
        this.title = title;
        this.assocContact = contact;
        this.creationDate = date;
        this.path = path;
    }

    @Override
    public String toString() {
        return "Person: " + this.title;
    }
}
