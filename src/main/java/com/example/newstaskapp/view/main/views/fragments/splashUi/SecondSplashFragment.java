package com.example.newstaskapp.view.main.views.fragments.splashUi;

import static com.example.newstaskapp.view.main.utils.HelperMethod.setStatusBar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.newstaskapp.R;
import com.example.newstaskapp.view.main.views.activities.HomeCycleActivity;
import com.example.newstaskapp.view.main.views.activities.SplashCycleActivity;
import com.example.newstaskapp.view.main.views.fragments.BaseFragment;

public class SecondSplashFragment extends BaseFragment {


    private TextView logo;
    private View view;
    private LinearLayout skipBtn;
    private Button lognBtn;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.second_splash_fragment, container, false);
        setStatusBar(getActivity(),R.color.blue);
        initView();

        return view;
    }

    private void initView() {
        lognBtn = view.findViewById(R.id.fragment_solash2_login_btu);
        skipBtn = view.findViewById(R.id.Fragment_splash2_skip);
        btnOnclick();
    }

    private void btnOnclick() {
        lognBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startHomeActivity();
            }
        });
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeActivity();
            }
        });
    }

    private void startHomeActivity() {
        startActivity(new Intent(getActivity(), HomeCycleActivity.class));
        getActivity().finish();
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}