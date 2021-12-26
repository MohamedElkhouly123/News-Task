package com.example.newstaskapp.view.main.utils;


import static com.example.newstaskapp.view.main.data.local.SharedPreferencesManager.SaveLanguage;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.newstaskapp.R;
import com.example.newstaskapp.view.main.utils.interfaces.TryAgainOncall;
import com.example.newstaskapp.view.main.views.activities.HomeCycleActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.aviran.cookiebar2.CookieBar;

import java.util.Calendar;
import java.util.Locale;


public class HelperMethod {
    public static ProgressDialog progressDialog;
    public static AlertDialog alertDialog;

    public static void replaceFragment(FragmentManager getChildFragmentManager, int id, Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void replaceFragment2(FragmentManager getChildFragmentManager, Bundle savedInstanceState, int id, Fragment fragment) {
        if (savedInstanceState == null) {
            getChildFragmentManager.beginTransaction()
                    .replace(R.id.container,fragment)
                    .commitNow();
        }
    }

    public static void openGoogleCroomLink(Activity activity, String googleUrl) {
        String url = googleUrl;
        try {
            Intent i = new Intent("android.intent.action.MAIN");
            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
            i.addCategory("android.intent.category.LAUNCHER");
            i.setData(Uri.parse(url));
            activity.startActivity(i);
        }
        catch(ActivityNotFoundException e) {
            // Chrome is not installed
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(i);
        }
    }

    public static void replaceFragmentWithAnimation(FragmentManager getChildFragmentManager, int id, Fragment fragment, String fromWhere) {

            FragmentTransaction transaction = getChildFragmentManager.beginTransaction();
            if (fromWhere == "l") {
//            android.R.anim.slide_in_left
                transaction.setCustomAnimations(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
            if (fromWhere == "r") {
                transaction.setCustomAnimations(R.anim.enter_from_right,
                        R.anim.exit_to_left);
            }
            if (fromWhere == "t") {
                transaction.setCustomAnimations(R.anim.slide_out_down, R.anim.slide_in_down);
            }
            if (fromWhere == "b") {
                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
            }
//        if(fromWhere=="rr"){
//            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.slide_in_left, R.anim.slide_out_right);}
//        if(fromWhere=="t"){
//            transaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_up);}
//        if(fromWhere=="b"){
//            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down);}
            transaction.replace(id, fragment);
            transaction.addToBackStack(null);

                    transaction.commit();

//            }
//        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBar(Activity activity,int color) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(activity,color)); }

    public static void replaceFragmentWithAnimation2(FragmentManager getChildFragmentManager, int id, Fragment fragment, String fromWhere) {
//        if (fragment != null) {
        Handler handler = new Handler();

        FragmentTransaction transaction = getChildFragmentManager.beginTransaction();
        if (fromWhere == "l") {
//            android.R.anim.slide_in_left
            transaction.setCustomAnimations(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        }
        if (fromWhere == "r") {
            transaction.setCustomAnimations(R.anim.enter_from_right,
                    R.anim.exit_to_left);
        }
        if (fromWhere == "t") {
            transaction.setCustomAnimations(R.anim.slide_out_down, R.anim.slide_in_down);
        }
        if (fromWhere == "b") {
            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
        }
//        if(fromWhere=="rr"){
//            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.slide_in_left, R.anim.slide_out_right);}
//        if(fromWhere=="t"){
//            transaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_up);}
//        if(fromWhere=="b"){
//            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down);}
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        Runnable r = new Runnable() {
            public void run() {
                if (!getChildFragmentManager.isDestroyed()) {
                    transaction.commit();
                }
            }
        };
        handler.postDelayed(r, 500);
//            }
//        }
    }



        public static void setLocale(Activity activity, String languageCode, int number, String type) {
            if (!activity.isFinishing()) {

                Locale locale = new Locale(languageCode);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                activity.getResources().updateConfiguration(config, activity.getResources().getDisplayMetrics());
                SaveLanguage(activity, "LANGUAGE", languageCode);

//                if (number == 1) {
//                    Intent intent = new Intent(activity, UserCycleActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    activity.startActivity(intent);
//                }
//                if (number == 2) {

                    Intent intent2 = new Intent(activity, HomeCycleActivity.class);
                    if (type != "") {
                        intent2.putExtra("type", type);
                    }
//                showToast(activity,"yes "+userData.isAllow_service());
//                    if(userData!=null){
//                        intent2.putExtra("UserData", (Serializable) userData);
//                    }
//                    if(ownerStatusData!=null){
//                        intent2.putExtra("OwnerStatusData", ownerStatusData);
//                    }
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent2);
//                }
                activity.finish();

            }
        }



    public static void showCookieMsg(String title, String msg, Activity activity, int color, int popUpPosition) {
        CookieBar.build(activity)
                .setTitle(title)
                .setMessage(msg)
                .setBackgroundColor(color)
                .setCookiePosition(popUpPosition)
                .setDuration(4000)
                .show();
    }








    public static void onLoadImageFromUrl(ImageView imageView, int Image, Context context) {
        Glide.with(context)
                .load("")
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .placeholder(Image)
                .into(imageView);

    }

    public static void onLoadImageFromUrl2(ImageView imageView, String URl, Context context) {
        Glide.with(context)
                .load(URl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
//                .placeholder(R.drawable.ic_logo_flash3)
                .into(imageView);


    }

    public static void onLoadImageFromUrl3(ImageView imageView, String URl, Context context) {
        Glide.with(context)
                .load(URl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
//                .placeholder(R.drawable.ic_background_img)
                .into(imageView);


    }

    public static void onLoadImageFromUrl4(ImageView imageView, String URl, Context context) {
        Glide.with(context)
                .load(URl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);


    }



//    public static void onLoadCirImageFromUrl(CircleImageView cirImageView, int URl, Context context) {
//        Glide.with(context)
//                .load(URl)
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
//                .placeholder(R.drawable.placeperson)
//                .into(cirImageView);
//    }

    public static void showProgressDialog(Activity activity, String title) {
        try {

            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(title);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);

            progressDialog.show();

        } catch (Exception e) {

        }
    }

    public static void dismissProgressDialog() {
        try {

            progressDialog.dismiss();

        } catch (Exception e) {

        }
    }

    public static void disappearKeypad(Activity activity, View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }

    public static void changeLang(Context context, String lang) {
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lang)); // API 17+ only.
        // Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
    }

    public static void htmlReader(TextView textView, String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(s));
        }
    }

//    public static void customToast(Activity activity, String ToastTitle, boolean failed) {
//
//        LayoutInflater inflater = activity.getLayoutInflater();
//
//        int layout_id;
//
//        if (failed) {
//            layout_id = R.layout.toast;
//        } else {
//            layout_id = R.layout.success_toast;
//        }
//
//        View layout = inflater.inflate(layout_id,
//                (ViewGroup) activity.findViewById(R.id.toast_layout_root));
//
////        TextView text = layout.findViewById(R.id.toast_txt);
////        text.setText(ToastTitle);
//
//        Toast toast = new Toast(activity);
//        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();
//    }

    public static void onPermission(Activity activity) {
        String[] perms = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE};

        ActivityCompat.requestPermissions(activity,
                perms,
                100);

    }
    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();

    }



       public static void showNoInternetDialogDialog(Activity activity, TryAgainOncall tryAgainOncall, String type) {
        try {
//                final View view = activity.getLayoutInflater().inflate(R.layout.dialog_restaurant_add_category, null);
//            alertDialog = new AlertDialog.Builder(HomeFragment.this).create();
            if (!activity.isFinishing()) {

                alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle(activity.getString(R.string.warning));
                alertDialog.setMessage(activity.getString(R.string.error_inter_net));
                alertDialog.setCancelable(true);

                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.try_again), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                    if(type.equalsIgnoreCase("recycle")) {
                        tryAgainOncall.tryAgainCall(type);

//                    }else {
//
//
//                    }

                    }
                });


                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(alertDialog!=null)
                            alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        } catch(Exception e){

        }

    }




}
