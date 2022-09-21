package com.example.newstaskapp.view.main.views.fragments.home

import android.app.Activity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.GetNewsListResponce
import retrofit2.Call

class MainModelForSend {
    var activity: Activity? =null
    var getAllUserResponceCall: Call<GetNewsListResponce>? =null
    var homeFragment: HomeFragmentKotlin?=null
    var fragmentHomeSrRefresh: SwipeRefreshLayout?=null
    var maxPage: Int = 0
}
