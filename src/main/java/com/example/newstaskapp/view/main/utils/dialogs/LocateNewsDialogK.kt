package com.example.newstaskapp.view.main.utils.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.*
import com.example.newstaskapp.R
import com.example.newstaskapp.view.main.utils.HelperMethod
import com.example.newstaskapp.view.main.utils.interfaces.MakeLoadNewsInteface
import com.google.android.material.textfield.TextInputLayout
import org.aviran.cookiebar2.CookieBar

class LocateNewsDialogK {
    //    private MakeLoadNewsInteface makeLoadNewsInteface;
    private val checkOutTv: TextView? = null
    private val checkInTv: TextView? = null
    private val fromDateTv: TextView? = null
    private val toDateTv: TextView? = null
    private val hotelNameTv: TextView? = null
    private val typeTv: TextView? = null
    var editTexts: List<EditText> = ArrayList()
    private val idPackage = 0
    private val idHotel = 0
    private var fragment_home_locate_news_type_auto: AutoCompleteTextView? = null
    private var fragment_home_locate_news_from_date_btn: TextInputLayout? = null
    private var doneNowBtn: Button? = null
    private var news_type: String? = null
    private var fragment_home_locate_news_from_date_edtx_btn: EditText? = null
    var chooceType = arrayOf("everything", "top-headlines")
    fun showDialog(activity: Activity, makeLoadNewsInteface: MakeLoadNewsInteface) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.option_dialog_to_get_news)
        dialog.setCanceledOnTouchOutside(false)
        fragment_home_locate_news_type_auto =
            dialog.findViewById(R.id.fragment_home_locate_news_type_auto)
        fragment_home_locate_news_from_date_btn =
            dialog.findViewById(R.id.fragment_home_locate_news_from_date_btn)
        fragment_home_locate_news_from_date_edtx_btn =
            dialog.findViewById(R.id.fragment_home_locate_news_from_date_edtx_btn)
        doneNowBtn = dialog.findViewById<View>(R.id.fragment_home_locate_news_done_btn) as Button
        val adapter =
            ArrayAdapter(activity, android.R.layout.select_dialog_singlechoice, chooceType)
        fragment_home_locate_news_type_auto?.setThreshold(1)
        fragment_home_locate_news_type_auto?.setAdapter(adapter)
        fragment_home_locate_news_from_date_edtx_btn?.setOnClickListener(View.OnClickListener {
            makeLoadNewsInteface.openDatePickerBtnCall(
                fragment_home_locate_news_from_date_btn
            )
        })
        doneNowBtn!!.setOnClickListener {
            news_type = fragment_home_locate_news_type_auto?.text.toString()
            if (news_type != null && !"".equals(news_type, ignoreCase = true)) {
                makeLoadNewsInteface.makeDoneBtnCall(news_type)
                dialog.cancel()
            } else {
                HelperMethod.showCookieMsg(
                    activity.getString(R.string.warning),
                    activity.getString(R.string.invalid_university),
                    activity,
                    R.color.red2,
                    CookieBar.TOP
                )
            }
        }

//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                activity.finish();
//            }
//        });
        dialog.show()
    } //    private void onCall(Activity activity, Dialog dialog, HotelData hotelData, GetRoom getRoom) {

    //        String reserfedFrom = checkinDate.getDate_txt();
    //        String reserfedTo = checkoutDate.getDate_txt();
    ////        showToas(activity, String.valueOf(checkinDate.getDate_txt()));
    //        int userId = userData.getId();
    //        int hotelId = hotelData.getId();
    //        int roomId = getRoom.getId();
    //        Call<GetDiscoverHomeResponce> updateItemCal= null;
    //            updateItemCal = getApiClient().bookHotel(reserfedFrom, reserfedTo, userId, hotelId, roomId);
    //
    //
    //        sentUserRateAndBookHotelCallBack(activity,updateItemCal,"Success Hotel Booking");
    //        dialog.cancel();
    //
    //    }
    companion object {
        private const val adultNum = 0
    }
}