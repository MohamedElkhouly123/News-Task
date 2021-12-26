package com.example.newstaskapp.view.main.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newstaskapp.view.main.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class BaseActivity extends AppCompatActivity {
    public static String languageToLoad ="";
    public static String BASE_URL2 ="https://newsapi.org/v2/";
    public static String BASE_URL_IMAGES = "http://flashbook-storage.pina-app.com/";
    public BaseFragment baseFragment;
    private String userToken;

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }
//​


    public void superBackPressed() {
        super.onBackPressed();
    }


}
