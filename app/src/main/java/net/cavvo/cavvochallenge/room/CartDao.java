package net.cavvo.cavvochallenge.room;



import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CartDao {

    @Query("SELECT * FROM CartEntity")
    List<CartEntity> getAll();

    @Insert
    void insert(CartEntity cartEntity);

    @Delete
    void delete(CartEntity cartEntity);



}
