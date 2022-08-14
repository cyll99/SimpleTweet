package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Organization {

    @ColumnInfo
    @PrimaryKey(autoGenerate=true)
    Long id;

    @ColumnInfo
    String name;


}

