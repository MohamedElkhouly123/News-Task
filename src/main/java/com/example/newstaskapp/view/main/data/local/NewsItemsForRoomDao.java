package com.example.newstaskapp.view.main.data.local;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.ArticleForRoom;

import java.util.List;


@Dao
public interface NewsItemsForRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArticleForRoom... ArticleItem);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllOrders(List<ArticleForRoom> ArticleItem);

    @Insert
    void add(ArticleForRoom... ArticleItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ArticleForRoom... ArticleItem);

//    @Query("UPDATE clientNewOrderSahl SET quantity=:quan WHERE itemId = :id")
//    void update(String quan, int id);

    @Delete
    void delete(ArticleForRoom... ArticleItem);

//    @Query("DELETE FROM database WHERE itemId = :id")
//    void deleteById(int id);

//    @Query("DELETE FROM clientNewOrderSahl WHERE itemId = :id")
//    int deleteById(int id);

    @Update
    void updateArticleDate(ArticleForRoom ArticleItem);

//    @Query("SELECT * FROM clientNews")
//    MutableLiveData<List<Article>> getAllArticles();

    @Query("SELECT * FROM clientNews ")
    List<ArticleForRoom> getAllItems();


//    LiveData<List<ClientMakeNewOrderItemForRoom>> getAllItems();

//
//
//    @Query("SELECT * FROM clientNewOrder WHERE itemId = :id")
//    LiveData<AllProductForRom> getSubjectById(int id);

//    @Query("select * from clientNewOrder where active = 1;")
//    ClientData checkIfUserExist();

    @Query("DELETE  FROM clientNews")
    void deletAll();
}
