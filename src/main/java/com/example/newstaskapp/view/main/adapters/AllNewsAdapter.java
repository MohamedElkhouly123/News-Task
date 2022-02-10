package com.example.newstaskapp.view.main.adapters;


import static com.example.newstaskapp.view.main.utils.HelperMethod.onLoadImageFromUrl2;
import static com.example.newstaskapp.view.main.utils.HelperMethod.showToast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newstaskapp.R;
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.ArticleForRoom;
import com.example.newstaskapp.view.main.views.activities.BaseActivity;

import java.util.List;


public class AllNewsAdapter extends RecyclerView.Adapter<AllNewsAdapter.NewsViewHolder> {
    List<ArticleForRoom> list;
    private final BaseActivity activity;
    private final Context context;

    NavController navController;
//    JoinGroupInterFace joinGroupInterFace;
    public AllNewsAdapter(List<ArticleForRoom> list, NavController navController, Activity activity, Context context) {
        this.list = list;
//        this.joinGroupInterFace=joinGroupInterFace;
        this.navController=navController;
        this.activity = (BaseActivity) activity;
        this.context = context;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card, null, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(v);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
//        showToast(activity, list.size()+"");
        setdata(holder,position);
        setaction(holder,position);
    }

    private void setaction(NewsViewHolder holder, int position) {
        holder.cardnewsImgShareBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        list.get(position).getUrl());
                sendIntent.setType("text/plain");
                activity.startActivity(sendIntent);
            }
        });
        holder.cardnewsMainLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Articleobj",list.get(position));
                navController.navigate(R.id.nav_show_article_desc, bundle);
            }
        });
    }

    private void setdata(NewsViewHolder holder, int position) {
        ArticleForRoom news = list.get(position);
        try {
            holder.cardnewsDescriptionTv.setText(news.getDescription());
            holder.cardnewsNameTv.setText(news.getName());
            holder.cardnewsTitleTv.setText(news.getTitle());
            if (news.getUrlToImage() != null) {
                String postImage = news.getUrlToImage();
                onLoadImageFromUrl2(holder.cardnewsImg, postImage, context);
            }
        }catch (Exception e){}
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        FrameLayout cardnewsImgShareBotton;
        TextView cardnewsTitleTv;
        TextView cardnewsNameTv;
        TextView cardnewsDescriptionTv;
        ImageView cardnewsImg;
        FrameLayout cardnewsMainLy;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            cardnewsImgShareBotton=view.findViewById(R.id.card_news_item_share_btn);
            cardnewsMainLy=view.findViewById(R.id.card_news_item_framelayout);
            cardnewsImg=view.findViewById(R.id.card_news_item_imageview);
            cardnewsTitleTv=view.findViewById(R.id.card_news_news_items_title_tv);
            cardnewsNameTv=view.findViewById(R.id.card_news_item_name_tv);
            cardnewsDescriptionTv=view.findViewById(R.id.card_news_item_description_tv);

        }
    }
}
