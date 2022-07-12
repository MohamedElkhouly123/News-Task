package com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "clientNews")
class ArticleForRoomK : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemId")
    var itemId = 0

    @SerializedName("author")
    @Expose
    var author: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String? = null

    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    constructor(
        author: String?,
        title: String?,
        description: String?,
        url: String?,
        urlToImage: String?,
        publishedAt: String?,
        content: String?,
        id: String?,
        name: String?
    ) {
        this.author = author
        this.title = title
        this.description = description
        this.url = url
        this.urlToImage = urlToImage
        this.publishedAt = publishedAt
        this.content = content
        this.id = id
        this.name = name
    }

    constructor() {}
}