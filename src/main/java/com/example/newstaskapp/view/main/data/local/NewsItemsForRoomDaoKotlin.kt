package com.example.newstaskapp.view.main.data.local

import androidx.room.*
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.ArticleForRoomK

@Dao
interface NewsItemsForRoomDaoKotlin {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg ArticleItem: ArticleForRoomK?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOrders(ArticleItem: List<ArticleForRoomK?>?)

    @Insert
    fun add(vararg ArticleItem: ArticleForRoomK?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg ArticleItem: ArticleForRoomK?)

    //    @Query("UPDATE clientNewOrderSahl SET quantity=:quan WHERE itemId = :id")
    //    void update(String quan, int id);
    @Delete
    fun delete(vararg ArticleItem: ArticleForRoomK?)

    //    @Query("DELETE FROM database WHERE itemId = :id")
    //    void deleteById(int id);
    //    @Query("DELETE FROM clientNewOrderSahl WHERE itemId = :id")
    //    int deleteById(int id);
    @Update
    fun updateArticleDate(ArticleItem: ArticleForRoomK?)

    //    @Query("SELECT * FROM clientNews2")
    //    MutableLiveData<List<Article>> getAllArticles();
    @get:Query("SELECT * FROM clientNews2 ")
    val allItems: List<ArticleForRoomK?>?

    //    LiveData<List<ClientMakeNewOrderItemForRoom>> getAllItems();
    //
    //
    //    @Query("SELECT * FROM clientNewOrder WHERE itemId = :id")
    //    LiveData<AllProductForRom> getSubjectById(int id);
    //    @Query("select * from clientNewOrder where active = 1;")
    //    ClientData checkIfUserExist();
    @Query("DELETE  FROM clientNews2")
    fun deletAll()
}