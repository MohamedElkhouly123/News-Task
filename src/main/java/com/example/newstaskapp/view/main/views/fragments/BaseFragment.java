package com.example.newstaskapp.view.main.views.fragments;


import static com.example.newstaskapp.view.main.data.api.ApiClient.BASE_URL;
import static com.example.newstaskapp.view.main.utils.HelperMethod.showCookieMsg;
import static com.example.newstaskapp.view.main.utils.netWork.InternetState.isConnected;
import static com.example.newstaskapp.view.main.views.activities.BaseActivity.BASE_URL2;
import static com.example.newstaskapp.view.main.views.activities.BaseActivity.languageToLoad;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.newstaskapp.R;
import com.example.newstaskapp.view.main.views.activities.BaseActivity;
import com.example.newstaskapp.view.main.views.activities.HomeCycleActivity;
import com.example.newstaskapp.view.main.views.activities.SplashCycleActivity;

import org.aviran.cookiebar2.CookieBar;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public class BaseFragment extends Fragment {
    public BaseActivity baseActivity;
    public HomeCycleActivity homeCycleActivity;
    public static boolean postAdded=false;
    public static boolean Togllle=false;

    private Context activity;
    public void setUpActivity() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;
        try {
            homeCycleActivity = (HomeCycleActivity) getActivity();
        } catch (Exception e) {

        }
    }

    public void shootingPreven() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Log.e("getWido", getActivity().getWindow().toString() + "");
//            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE
//                    , WindowManager.LayoutParams.FLAG_SECURE);
//        }
    }

    public void onBack() {
           baseActivity.superBackPressed();
    }



    @Override
    public void onStart() {
        super.onStart();
        setUpActivity();
    }
    public void refreshLanguage() {
        // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getResources().updateConfiguration(config,
                activity.getResources().getDisplayMetrics());
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = context;

        if (!isConnected(getActivity())) {
            showCookieMsg("Warning", getString(R.string.error_inter_net),getActivity(), R.color.red,
                    CookieBar.TOP);
//            onCreateErrorToast(getActivity(), getString(R.string.error_inter_net));
        }
        refreshLanguage();

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshLanguage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setUpActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }




    @Override
    public void onResume() {
        super.onResume();
//        ||"".equalsIgnoreCase(languageToLoad)||languageToLoad==null
        if ("".equalsIgnoreCase(BASE_URL2)) {
            startActivity(new Intent(getActivity(), SplashCycleActivity.class));
        }
    }





}
