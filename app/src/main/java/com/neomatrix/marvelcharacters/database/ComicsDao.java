package com.neomatrix.marvelcharacters.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.neomatrix.marvelcharacters.models.Result;

import java.util.List;

import io.reactivex.Observable;

//@Dao
public interface ComicsDao {


   //  @Query("SELECT * FROM result ORDER BY url ASC ")
   //  Observable<List<Result>> getAll();

   //  @Query("SELECT * FROM result WHERE id = :id")
   //  Result getId(int id);

   //  @Query("SELECT * FROM result WHERE number = :number")
   //  Observable<Result> getNumber(int number);

   //  @Query("SELECT * FROM result WHERE name = :name")
   //  Observable<Result> getName(String name);

   //  @Insert(onConflict = OnConflictStrategy.REPLACE)
   //  void insert(Result result);

   //  @Update
   //  void update(Result result);

   //  @Query("DELETE FROM Result WHERE name = :name")
   //  void deleteByName(String name);
    }

