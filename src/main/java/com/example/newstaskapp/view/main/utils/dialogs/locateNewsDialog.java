package com.example.newstaskapp.view.main.utils.dialogs;


import static com.example.newstaskapp.view.main.utils.HelperMethod.showCookieMsg;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.newstaskapp.R;
import com.example.newstaskapp.view.main.utils.interfaces.MakeLoadNewsInteface;
import com.example.newstaskapp.view.main.views.fragments.home.HomeFragment;
import com.google.android.material.textfield.TextInputLayout;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.List;


public class locateNewsDialog {
    private static int adultNum;
//    private MakeLoadNewsInteface makeLoadNewsInteface;
    private TextView checkOutTv,checkInTv,fromDateTv,toDateTv,hotelNameTv,typeTv;
    List<EditText> editTexts = new ArrayList<>();
    private int idPackage,idHotel;
    private AutoCompleteTextView  fragment_home_locate_news_type_auto;
    private TextInputLayout fragment_home_locate_news_from_date_btn;
    private Button doneNowBtn;
    private String news_type;
    private EditText fragment_home_locate_news_from_date_edtx_btn;
    String[] chooceType = {"everything", "top-headlines"};

    public void showDialog(final Activity activity, MakeLoadNewsInteface makeLoadNewsInteface) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.option_dialog_to_get_news);
        dialog.setCanceledOnTouchOutside(false);

        fragment_home_locate_news_type_auto=dialog.findViewById(R.id.fragment_home_locate_news_type_auto);
        fragment_home_locate_news_from_date_btn=dialog.findViewById(R.id.fragment_home_locate_news_from_date_btn);
        fragment_home_locate_news_from_date_edtx_btn=dialog.findViewById(R.id.fragment_home_locate_news_from_date_edtx_btn);
        doneNowBtn = (Button) dialog.findViewById(R.id.fragment_home_locate_news_done_btn);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.select_dialog_singlechoice, chooceType);
        fragment_home_locate_news_type_auto.setThreshold(1);
        fragment_home_locate_news_type_auto.setAdapter(adapter);


        fragment_home_locate_news_from_date_edtx_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                makeLoadNewsInteface.openDatePickerBtnCall(fragment_home_locate_news_from_date_btn);
            }
        });

        doneNowBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                news_type = fragment_home_locate_news_type_auto.getText().toString();
                if(news_type!=null&&!"".equalsIgnoreCase(news_type)) {
                    makeLoadNewsInteface.makeDoneBtnCall(news_type);
                    dialog.cancel();
                }else {
                    showCookieMsg(activity.getString(R.string.warning), activity.getString(R.string.invalid_university), activity, R.color.red2, CookieBar.TOP);
                }
            }
        });

//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                activity.finish();
//            }
//        });
                dialog.show();

            }






//    private void onCall(Activity activity, Dialog dialog, HotelData hotelData, GetRoom getRoom) {
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



}
