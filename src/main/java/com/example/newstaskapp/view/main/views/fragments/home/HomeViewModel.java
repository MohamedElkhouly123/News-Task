package com.example.newstaskapp.view.main.views.fragments.home;

import static android.content.ContentValues.TAG;

import static com.example.newstaskapp.view.main.utils.netWork.InternetState.isConnected;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newstaskapp.R;
import com.example.newstaskapp.view.main.data.local.DataBase;
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.ArticleForRoom;
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.GetNewsListResponce;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<GetNewsListResponce> getNewsListResponceMutableLiveData;
    private MutableLiveData<List<ArticleForRoom>> getNewsListRoomResponceMutableLiveData;
    private DataBase dataBase;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        getNewsListResponceMutableLiveData = new MutableLiveData<>();
        getNewsListRoomResponceMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<List<ArticleForRoom>> getLocalList() {
        return getNewsListRoomResponceMutableLiveData;
    }




    public MutableLiveData<GetNewsListResponce> getAllUserMainNewsDataResponce() {
        return getNewsListResponceMutableLiveData;
    }

    public void sendToGetAllUserMainNewsDataResponce(FragmentActivity activity, Call<GetNewsListResponce> getAllUserResponceCall, HomeFragment homeFragment, SwipeRefreshLayout fragmentHomeSrRefresh, int maxPage) {
        dataBase = DataBase.getInstance(activity);
        if (isConnected(activity)) {
            fragmentHomeSrRefresh.setRefreshing(true);
            getAllUserResponceCall.enqueue(new Callback<GetNewsListResponce>() {
                @Override
                public void onResponse(Call<GetNewsListResponce> call, Response<GetNewsListResponce> response) {

                    if (response.body() != null) {
                        try {
                            if ("ok".equalsIgnoreCase(response.body().getStatus())) {
//                                loadMore.setVisibility(View.GONE);
                                fragmentHomeSrRefresh.setRefreshing(false);
                                getNewsListResponceMutableLiveData.postValue(response.body());
                                Log.i("rrrrrrrrrrrrrrrrrr", response.body().getArticles()+"");

                            }

                        } catch (Exception e) {

                        }
                    }
                    if (response.code() == 401) {
//                              ToastCreator.onCreateSuccessToast(activity, String.valueOf(response.errorBody()));
//                              showConfirmationDialog(activity);
                        return;
                    }


                }

                @Override
                public void onFailure(Call<GetNewsListResponce> call, Throwable t) {
                    try {
//                        loadMore.setVisibility(View.GONE);
                        fragmentHomeSrRefresh.setRefreshing(false);
                        Log.i(TAG, String.valueOf(t.getMessage()));
//                        showToast(activity, "false");
                        Log.i(TAG, String.valueOf(t.getCause()));
//                        new HomeFragment().setError(String.valueOf(R.string.error_list));
                        getNewsListResponceMutableLiveData.postValue(null);
                    } catch (Exception e) {

                    }
                }
            });
        } else {
            try {
                getArticlesFromRoom();
//                loadMore.setVisibility(View.GONE);
                fragmentHomeSrRefresh.setRefreshing(false);
            } catch (Exception e) {

            }

        }
    }

    private void getArticlesFromRoom() {
            Executors.newSingleThreadExecutor().execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            getNewsListRoomResponceMutableLiveData.postValue(dataBase.addNewOrderItemDao().getAllItems());
                        }
                    });
    }
}