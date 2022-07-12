package com.example.newstaskapp.view.main.views.fragments.home

import android.content.ContentValues
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newstaskapp.view.main.data.local.DataBase
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.ArticleForRoom
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.GetNewsListResponce
import com.example.newstaskapp.view.main.utils.netWork.InternetState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class HomeViewModelK : ViewModel() {
    private val mText: MutableLiveData<String>
    val allUserMainNewsDataResponce: MutableLiveData<GetNewsListResponce?>
    private val getNewsListRoomResponceMutableLiveData: MutableLiveData<List<ArticleForRoom>>
    private var dataBase: DataBase? = null
    val localList: LiveData<List<ArticleForRoom>>
        get() = getNewsListRoomResponceMutableLiveData

    fun sendToGetAllUserMainNewsDataResponce(
        activity: FragmentActivity?,
        getAllUserResponceCall: Call<GetNewsListResponce?>,
        homeFragment: HomeFragmentKotlin?,
        fragmentHomeSrRefresh: SwipeRefreshLayout,
        maxPage: Int
    ) {
        dataBase = DataBase.getInstance(activity)
        if (InternetState.isConnected(activity)) {
            fragmentHomeSrRefresh.isRefreshing = true
            getAllUserResponceCall.enqueue(object : Callback<GetNewsListResponce?> {
                override fun onResponse(
                    call: Call<GetNewsListResponce?>,
                    response: Response<GetNewsListResponce?>
                ) {
                    if (response.body() != null) {
                        try {
                            if ("ok".equals(response.body()!!.status, ignoreCase = true)) {
//                                loadMore.setVisibility(View.GONE);
                                fragmentHomeSrRefresh.isRefreshing = false
                                allUserMainNewsDataResponce.postValue(response.body())
                                Log.i(
                                    "rrrrrrrrrrrrrrrrrr",
                                    response.body()!!.articles.toString() + ""
                                )
                            }
                        } catch (e: Exception) {
                        }
                    }
                    if (response.code() == 401) {
//                              ToastCreator.onCreateSuccessToast(activity, String.valueOf(response.errorBody()));
//                              showConfirmationDialog(activity);
                        return
                    }
                }

                override fun onFailure(call: Call<GetNewsListResponce?>, t: Throwable) {
                    try {
//                        loadMore.setVisibility(View.GONE);
                        fragmentHomeSrRefresh.isRefreshing = false
                        Log.i(ContentValues.TAG, t.message.toString())
                        //                        showToast(activity, "false");
                        Log.i(ContentValues.TAG, t.cause.toString())
                        //                        new HomeFragment().setError(String.valueOf(R.string.error_list));
                        allUserMainNewsDataResponce.postValue(null)
                    } catch (e: Exception) {
                    }
                }
            })
        } else {
            try {
                articlesFromRoom
                //                loadMore.setVisibility(View.GONE);
                fragmentHomeSrRefresh.isRefreshing = false
            } catch (e: Exception) {
            }
        }
    }

    private val articlesFromRoom: Unit
        private get() {
            Executors.newSingleThreadExecutor().execute {
                getNewsListRoomResponceMutableLiveData.postValue(
                    dataBase!!.addNewOrderItemDao().allItems
                )
            }
        }

    init {
        mText = MutableLiveData()
        allUserMainNewsDataResponce = MutableLiveData()
        getNewsListRoomResponceMutableLiveData = MutableLiveData()
    }
}