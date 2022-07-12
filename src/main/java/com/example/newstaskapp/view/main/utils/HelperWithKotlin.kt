package com.example.newstaskapp.view.main.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.*
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.newstaskapp.R
import com.example.newstaskapp.view.main.data.local.SharedPreferencesManager
import com.example.newstaskapp.view.main.utils.interfaces.TryAgainOncall
import com.example.newstaskapp.view.main.views.activities.HomeCycleActivity
import org.aviran.cookiebar2.CookieBar
import java.util.*

object HelperWithKotlin {
    var progressDialog: ProgressDialog? = null
    var alertDialog: AlertDialog? = null

    @JvmStatic
    fun replaceFragment(
            getChildFragmentManager: FragmentManager,
            id: Int,
            fragment: Fragment?
        ) {
            val transaction = getChildFragmentManager.beginTransaction()
            transaction.replace(id, fragment!!)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    @JvmStatic
    fun replaceFragment2(
            getChildFragmentManager: FragmentManager,
            savedInstanceState: Bundle?,
            id: Int,
            fragment: Fragment?
        ) {
            if (savedInstanceState == null) {
                getChildFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment!!)
                    .commitNow()
            }
        }
    @JvmStatic
    fun openGoogleCroomLink(activity: Activity, googleUrl: String) {
            try {
                val i = Intent("android.intent.action.MAIN")
                i.component =
                    ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main")
                i.addCategory("android.intent.category.LAUNCHER")
                i.data = Uri.parse(googleUrl)
                activity.startActivity(i)
            } catch (e: ActivityNotFoundException) {
                // Chrome is not installed
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(googleUrl))
                activity.startActivity(i)
            }
        }
    @JvmStatic
    fun replaceFragmentWithAnimation(
            getChildFragmentManager: FragmentManager,
            id: Int,
            fragment: Fragment?,
            fromWhere: String
        ) {
            val transaction = getChildFragmentManager.beginTransaction()
            if (fromWhere === "l") {
//            android.R.anim.slide_in_left
                transaction.setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
            }
            if (fromWhere === "r") {
                transaction.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left
                )
            }
            if (fromWhere === "t") {
                transaction.setCustomAnimations(R.anim.slide_out_down, R.anim.slide_in_down)
            }
            if (fromWhere === "b") {
                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
            }
            //        if(fromWhere=="rr"){
//            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.slide_in_left, R.anim.slide_out_right);}
//        if(fromWhere=="t"){
//            transaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_up);}
//        if(fromWhere=="b"){
//            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down);}
            transaction.replace(id, fragment!!)
            transaction.addToBackStack(null)
            transaction.commit()

//            }
//        }
        }
    @JvmStatic
    fun hideSoftKeyboard(activity: Activity) {
            if (activity.currentFocus == null) {
                return
            }
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }

    @JvmStatic
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBar(activity: Activity, color: Int) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(activity, color)
        }

    @JvmStatic
    fun replaceFragmentWithAnimation2(
            getChildFragmentManager: FragmentManager,
            id: Int,
            fragment: Fragment?,
            fromWhere: String
        ) {
//        if (fragment != null) {
            val handler = Handler()
            val transaction = getChildFragmentManager.beginTransaction()
            if (fromWhere === "l") {
//            android.R.anim.slide_in_left
                transaction.setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
            }
            if (fromWhere === "r") {
                transaction.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left
                )
            }
            if (fromWhere === "t") {
                transaction.setCustomAnimations(R.anim.slide_out_down, R.anim.slide_in_down)
            }
            if (fromWhere === "b") {
                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
            }
            //        if(fromWhere=="rr"){
//            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.slide_in_left, R.anim.slide_out_right);}
//        if(fromWhere=="t"){
//            transaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_up);}
//        if(fromWhere=="b"){
//            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down);}
            transaction.replace(id, fragment!!)
            transaction.addToBackStack(null)
            val r = Runnable {
                if (!getChildFragmentManager.isDestroyed) {
                    transaction.commit()
                }
            }
            handler.postDelayed(r, 500)
            //            }
//        }
        }

    @JvmStatic
    fun setLocale(activity: Activity, languageCode: String?, number: Int, type: String) {
            if (!activity.isFinishing) {
                val locale = Locale(languageCode)
                Locale.setDefault(locale)
                val config = Configuration()
                config.locale = locale
                activity.resources.updateConfiguration(config, activity.resources.displayMetrics)
                SharedPreferencesManager.SaveLanguage(activity, "LANGUAGE", languageCode)

//                if (number == 1) {
//                    Intent intent = new Intent(activity, UserCycleActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    activity.startActivity(intent);
//                }
//                if (number == 2) {
                val intent2 = Intent(activity, HomeCycleActivity::class.java)
                if (type !== "") {
                    intent2.putExtra("type", type)
                }
                //                showToast(activity,"yes "+userData.isAllow_service());
//                    if(userData!=null){
//                        intent2.putExtra("UserData", (Serializable) userData);
//                    }
//                    if(ownerStatusData!=null){
//                        intent2.putExtra("OwnerStatusData", ownerStatusData);
//                    }
                intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                activity.startActivity(intent2)
                //                }
                activity.finish()
            }
        }

    @JvmStatic
    fun showCookieMsg(
            title: String?,
            msg: String?,
            activity: Activity?,
            color: Int,
            popUpPosition: Int
        ) {
            CookieBar.build(activity)
                .setTitle(title)
                .setMessage(msg)
                .setBackgroundColor(color)
                .setCookiePosition(popUpPosition)
                .setDuration(4000)
                .show()
        }


    @JvmStatic
    fun onLoadImageFromUrl(imageView: ImageView?, Image: Int, context: Context?) {
            Glide.with(context!!)
                .load("")
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .placeholder(Image)
                .into(imageView!!)
        }

    @JvmStatic
    fun onLoadImageFromUrl2(imageView: ImageView?, URl: String?, context: Context?) {
            Glide.with(context!!)
                .load(URl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)) //                .placeholder(R.drawable.ic_logo_flash3)
                .into(imageView!!)
        }

    @JvmStatic
    fun onLoadImageFromUrl3(imageView: ImageView?, URl: String?, context: Context?) {
            Glide.with(context!!)
                .load(URl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)) //                .placeholder(R.drawable.ic_background_img)
                .into(imageView!!)
        }

    @JvmStatic
    fun onLoadImageFromUrl4(imageView: ImageView?, URl: String?, context: Context?) {
            Glide.with(context!!)
                .load(URl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView!!)
        }

        //    public static void onLoadCirImageFromUrl(CircleImageView cirImageView, int URl, Context context) {
        //        Glide.with(context)
        //                .load(URl)
        //                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        //                .placeholder(R.drawable.placeperson)
        //                .into(cirImageView);
        //    }

    @JvmStatic
    fun showProgressDialog(activity: Activity?, title: String?) {
            try {
                progressDialog = ProgressDialog(activity)
                progressDialog!!.setMessage(title)
                progressDialog!!.isIndeterminate =
                    false
                progressDialog!!.setCancelable(false)
                progressDialog!!.show()
            } catch (e: Exception) {
            }
        }


    @JvmStatic
    fun dismissProgressDialog() {
            try {
                progressDialog!!.dismiss()
            } catch (e: Exception) {
            }
        }


    @JvmStatic
    fun disappearKeypad(activity: Activity, v: View?) {
            try {
                if (v != null) {
                    val imm =
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            } catch (e: Exception) {
            }
        }


    @JvmStatic
    fun changeLang(context: Context, lang: String?) {
            val res = context.resources
            // Change locale settings in the app.
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.setLocale(Locale(lang)) // API 17+ only.
            // Use conf.locale = new Locale(...) if targeting lower versions
            res.updateConfiguration(conf, dm)
        }

    @JvmStatic
    fun htmlReader(textView: TextView, s: String?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.text = Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT)
            } else {
                textView.text = Html.fromHtml(s)
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

    @JvmStatic
    fun onPermission(activity: Activity?) {
            val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE
            )
            ActivityCompat.requestPermissions(
                activity!!,
                perms,
                100
            )
        }

    @JvmStatic

    fun showToast(activity: Activity?, message: String?) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }

    @JvmStatic
    fun showLongToast(activity: Activity?, message: String?) {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        }

    @JvmStatic
    fun showNoInternetDialogDialog(
            activity: Activity,
            tryAgainOncall: TryAgainOncall,
            type: String?
        ) {
            try {
//                final View view = activity.getLayoutInflater().inflate(R.layout.dialog_restaurant_add_category, null);
//            alertDialog = new AlertDialog.Builder(HomeFragment.this).create();
                if (!activity.isFinishing) {
                    alertDialog = AlertDialog.Builder(activity).create()
                    alertDialog?.setTitle(activity.getString(R.string.warning))
                    alertDialog?.setMessage(activity.getString(R.string.error_inter_net))
                    alertDialog?.setCancelable(true)
                    alertDialog?.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE,
                        activity.getString(R.string.try_again),
                        DialogInterface.OnClickListener { dialog, which ->
                            //                    if(type.equalsIgnoreCase("recycle")) {
                            tryAgainOncall.tryAgainCall(type)

                            //                    }else {
                            //
                            //
                            //                    }
                        })
                    alertDialog?.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE,
                        activity.getString(R.string.cancel),
                        DialogInterface.OnClickListener { dialog, which -> if (alertDialog != null) alertDialog!!.dismiss() })
                    alertDialog?.show()
                }
            } catch (e: Exception) {
            }
        }
}