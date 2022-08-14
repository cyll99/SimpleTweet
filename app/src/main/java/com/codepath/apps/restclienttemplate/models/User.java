package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.models.Organization;

@Entity(foreignKeys = @ForeignKey(entity= Organization.class, parentColumns="id", childColumns="organization_id"))
public class User  {
    @ColumnInfo
    @PrimaryKey(autoGenerate=true)
    Long id;

    @ColumnInfo
    String name;

    @ColumnInfo
    Long organization_id;
}