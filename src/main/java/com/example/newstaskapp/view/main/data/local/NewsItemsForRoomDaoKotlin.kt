package com.example.newstaskapp.view.main.data.local

import androidx.room.*
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.ArticleForRoom

@Dao
interface NewsItemsForRoomDaoKotlin {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg ArticleItem: ArticleForRoom?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOrders(ArticleItem: List<ArticleForRoom?>?)

    @Insert
    fun add(vararg ArticleItem: ArticleForRoom?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg ArticleItem: ArticleForRoom?)

    //    @Query("UPDATE clientNewOrderSahl SET quantity=:quan WHERE itemId = :id")
    //    void update(String quan, int id);
    @Delete
    fun delete(vararg ArticleItem: ArticleForRoom?)

    //    @Query("DELETE FROM database WHERE itemId = :id")
    //    void deleteById(int id);
    //    @Query("DELETE FROM clientNewOrderSahl WHERE itemId = :id")
    //    int deleteById(int id);
    @Update
    fun updateArticleDate(ArticleItem: ArticleForRoom?)

    //    @Query("SELECT * FROM clientNews")
    //    MutableLiveData<List<Article>> getAllArticles();
    @get:Query("SELECT * FROM clientNews ")
    val allItems: List<ArticleForRoom?>?

    //    LiveData<List<ClientMakeNewOrderItemForRoom>> getAllItems();
    //
    //
    //    @Query("SELECT * FROM clientNewOrder WHERE itemId = :id")
    //    LiveData<AllProductForRom> getSubjectById(int id);
    //    @Query("select * from clientNewOrder where active = 1;")
    //    ClientData checkIfUserExist();
    @Query("DELETE  FROM clientNews")
    fun deletAll()
}