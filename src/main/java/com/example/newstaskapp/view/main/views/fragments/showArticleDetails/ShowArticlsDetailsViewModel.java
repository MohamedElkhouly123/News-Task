package com.example.newstaskapp.view.main.views.fragments.showArticleDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShowArticlsDetailsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShowArticlsDetailsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}