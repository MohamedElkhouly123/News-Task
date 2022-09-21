package com.example.newstaskapp.view.main.views.fragments

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newstaskapp.R
import com.example.newstaskapp.view.main.utils.HelperWithKotlin
import com.example.newstaskapp.view.main.utils.netWork.InternetStateKotlin.isConnected2
import com.example.newstaskapp.view.main.views.activities.BaseActivityKotlin
import com.example.newstaskapp.view.main.views.activities.HomeCycleActivityKotlin
import com.example.newstaskapp.view.main.views.activities.SplashCycleActivity
import org.aviran.cookiebar2.CookieBar
import java.util.*

open class BaseFragmentKotlin : Fragment() {
    var baseActivity: BaseActivityKotlin? = null
    var homeCycleActivity: HomeCycleActivityKotlin? = null
    private var activity: Context? = null
    fun setUpActivity() {
        baseActivity = getActivity() as BaseActivityKotlin?
        baseActivity!!.baseFragment = this
        try {
            homeCycleActivity = getActivity() as HomeCycleActivityKotlin?
        } catch (e: Exception) {
        }
    }

    fun shootingPreven() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        Log.e("getWido", requireActivity().window.toString() + "")
        //            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE
//                    , WindowManager.LayoutParams.FLAG_SECURE);
//        }
    }

    open fun onBack() {
        baseActivity!!.superBackPressed()
    }

    override fun onStart() {
        super.onStart()
        setUpActivity()
    }

    fun refreshLanguage() {
        // your language
        val locale = Locale(BaseActivityKotlin.languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        requireActivity().resources.updateConfiguration(
            config,
            requireActivity().resources.displayMetrics
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context
        if (!isConnected2(requireActivity())) {
            HelperWithKotlin.showCookieMsg(
                "Warning", getString(R.string.error_inter_net), getActivity(), R.color.red,
                CookieBar.TOP
            )
            //            onCreateErrorToast(getActivity(), getString(R.string.error_inter_net));
        }
        refreshLanguage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshLanguage()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpActivity()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        //        ||"".equalsIgnoreCase(languageToLoad)||languageToLoad==null
        if ("".equals(BaseActivityKotlin.BASE_URL2, ignoreCase = true)) {
            startActivity(Intent(getActivity(), SplashCycleActivity::class.java))
        }
    }

    companion object {
        var postAdded = false
        var Togllle = false
    }
}