//package com.codepath.apps.restclienttemplate.models;
//
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
//import androidx.room.Query;
//
//@Dao
//public interface UserDao {
//    @Query("SELECT * FROM User where userId := :id")
//    public User getById(int id);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    public Long insertUser(User user);
//
//    @Delete
//    public void deleteUser(User user);
//}