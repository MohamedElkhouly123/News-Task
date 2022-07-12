package com.example.newstaskapp.view.main.data.local

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManagerKotlin {
    var sharedPreferences: SharedPreferences? = null
    var USER_DATA = "USER_DATA"
    var PeopleSearch = "PeopleSearch"
    var ProfileBackStack = "profileBackStack"
    var REMEMBER_ME = "REMEMBER_ME"
    var LANG = "LANGUAGE"
    var USER_PASSWORD = "USER_PASSWORD"
    var USER_TOKEN = "USER_TOKEN"
    var USER_Time = "USER_Time"
    var USER_Type = "USER_Time"
    var CLIENT = "CLIENT"
    var TABPOSION = "TABPOSION"
    var USER_FireBase_TOKEN = "USER_FireBase_TOKEN"
    var GOOGLECHECK = "GOOGLECHECK"
    var MAC = "MAC"
    fun setSharedPreferences(activity: Activity) {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences(
                "memoria", Context.MODE_PRIVATE
            )
        }
    }

    fun SaveLanguage(activity: Activity, data_Key: String?, data_Value: String?) {
        setSharedPreferences(activity)
        if (sharedPreferences != null) {
            val editor = sharedPreferences!!.edit()
            editor.putString(data_Key, data_Value)
            editor.commit()
        } else {
            setSharedPreferences(activity)
        }
    }

    fun SaveData(activity: Activity, data_Key: String?, data_Value: String?) {
        setSharedPreferences(activity)
        if (sharedPreferences != null) {
            val editor = sharedPreferences!!.edit()
            editor.putString(data_Key, data_Value)
            editor.commit()
        } else {
            setSharedPreferences(activity)
        }
    }

    fun SaveData(activity: Activity, data_Key: String?, data_Value: Boolean) {
        setSharedPreferences(activity)
        if (sharedPreferences != null) {
            val editor = sharedPreferences!!.edit()
            editor.putBoolean(data_Key, data_Value)
            editor.commit()
        } else {
            setSharedPreferences(activity)
        }
    }

    //    public static void SaveData(Activity activity, String data_Key, Object data_Value) {
    //        setSharedPreferences(activity);
    //        if (sharedPreferences != null) {
    //            SharedPreferences.Editor editor = sharedPreferences.edit();
    //            Gson gson = new Gson();
    //            String StringData = gson.toJson(data_Value);
    //            editor.putString(data_Key, StringData);
    //            editor.commit();
    //        }
    //    }
    fun LoadData(activity: Activity, data_Key: String?): String? {
        setSharedPreferences(activity)
        return sharedPreferences!!.getString(data_Key, null)
    }

    //    public static UserDataResponse LoadUserData(Activity activity) {
    //        setSharedPreferences(activity);
    //
    //    UserDataResponse loginData = null;
    //        Gson gson = new Gson();
    //        loginData = gson.fromJson(LoadData(activity, USER_DATA), UserDataResponse.class);
    //
    //        return loginData;
    //    }
    fun LoadBoolean(activity: Activity, data_Key: String?): Boolean {
        setSharedPreferences(activity)
        return sharedPreferences!!.getBoolean(data_Key, false)
    }

    fun clean(activity: Activity) {
        setSharedPreferences(activity)
        if (sharedPreferences != null) {
            val editor = sharedPreferences!!.edit()
            editor.clear()
            editor.commit()
        }
    }

    fun LoadLanguage(activity: Activity, data_Key: String?): String? {
        setSharedPreferences(activity)
        return sharedPreferences!!.getString(data_Key, "en")
    }
}