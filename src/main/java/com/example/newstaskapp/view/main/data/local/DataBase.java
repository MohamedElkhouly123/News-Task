package com.example.newstaskapp.view.main.data.local;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.newstaskapp.view.main.data.models.getNewsListResponce.Article;
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.ArticleForRoom;


@Database(entities = {ArticleForRoom.class,}, version = 2, exportSchema = false)


//@Database(entities = {Client.class, ClientData.class}, version = 1, exportSchema = false)  // more than ClientMakeNewOrderItem
//@TypeConverters({DataTypeConverter.class})
public abstract class DataBase extends RoomDatabase {
    private static final String DB_NAME = "databaseNewsApp.db";
    private static volatile DataBase instance;

    // region singleton implementation
    public static synchronized DataBase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static DataBase create(final Context context) {
        Builder<DataBase> builder = Room.databaseBuilder(context, DataBase.class, DB_NAME).fallbackToDestructiveMigration();
        return builder.build();
    }

    // endregion
    // region DAOs
    public abstract NewsItemsForRoomDao addNewOrderItemDao();
}
