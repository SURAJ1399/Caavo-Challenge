package net.cavvo.cavvochallenge.room;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CartEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDao taskDao();
}
