package com.example.studentapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.studentapp.database.Converters;

@Dao
@TypeConverters({Converters.class})
public abstract class BaseDAO<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(T entity);

    @Update
    public abstract void update(T entity);

    @Delete
    public abstract void delete(T entity);
}
