package com.example.newstaskapp.view.main.views.activities

import androidx.appcompat.app.AppCompatActivity
import com.example.newstaskapp.view.main.views.fragments.BaseFragment
import com.example.newstaskapp.view.main.views.fragments.BaseFragmentKotlin

open class BaseActivityKotlin : AppCompatActivity() {
    var baseFragment: BaseFragmentKotlin? = null
    private val userToken: String? = null
    override fun onBackPressed() {
        baseFragment!!.onBack()
    }

    //​
    fun superBackPressed() {
        super.onBackPressed()
    }

    companion object {
        var languageToLoad = ""
        var BASE_URL2 = "https://newsapi.org/v2/"
        var BASE_URL_IMAGES = "http://flashbook-storage.pina-app.com/"
    }
}