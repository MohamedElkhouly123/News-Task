package com.example.newstaskapp.view.main.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.ArticleForRoom

@Database(
    entities = [ArticleForRoom::class],
    version = 2,
    exportSchema = false
) //@Database(entities = {Client.class, ClientData.class}, version = 1, exportSchema = false)  // more than ClientMakeNewOrderItem
//@TypeConverters({DataTypeConverter.class})
abstract class DataBaseKotlin : RoomDatabase() {
    // endregion
    // region DAOs
    abstract fun addNewOrderItemDao(): NewsItemsForRoomDao?

    companion object {
        private const val DB_NAME = "databaseNewsApp.db"

        @Volatile
        private var instance: DataBase? = null

        // region singleton implementation
        @Synchronized
        fun getInstance(context: Context): DataBase? {
            if (instance == null) {
                instance = create(context)
            }
            return instance
        }

        private fun create(context: Context): DataBase {
            val builder = Room.databaseBuilder(
                context,
                DataBase::class.java, DB_NAME
            ).fallbackToDestructiveMigration()
            return builder.build()
        }
    }
}