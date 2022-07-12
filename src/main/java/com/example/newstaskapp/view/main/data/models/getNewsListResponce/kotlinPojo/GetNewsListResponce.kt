package com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GetNewsListResponceK : Serializable {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null

    @SerializedName("articles")
    @Expose
    var articles: List<Article> = ArrayList()
}