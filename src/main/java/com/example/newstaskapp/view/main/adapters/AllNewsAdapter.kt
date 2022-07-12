package com.example.newstaskapp.view.main.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.newstaskapp.R
import com.example.newstaskapp.view.main.adapters.AllNewsAdapter.NewsViewHolder
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.ArticleForRoom
import com.example.newstaskapp.view.main.utils.HelperMethod
import com.example.newstaskapp.view.main.views.activities.BaseActivity

class AllNewsAdapter(
    var list: List<ArticleForRoom>,
    var navController: NavController,
    activity: Activity,
    context: Context
) :
    RecyclerView.Adapter<NewsViewHolder>() {
    private val activity: BaseActivity
    private val context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.article_card, null, false)
        return NewsViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
//        showToast(activity, list.size()+"");
        setdata(holder, position)
        setaction(holder, position)
    }

    private fun setaction(holder: NewsViewHolder, position: Int) {
        holder.cardnewsImgShareBotton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                list[position].url
            )
            sendIntent.type = "text/plain"
            activity.startActivity(sendIntent)
        }
        holder.cardnewsMainLy.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("Articleobj", list[position])
            navController.navigate(R.id.nav_show_article_desc, bundle)
        }
    }

    private fun setdata(holder: NewsViewHolder, position: Int) {
        val news = list[position]
        try {
            holder.cardnewsDescriptionTv.text = news.description
            holder.cardnewsNameTv.text = news.name
            holder.cardnewsTitleTv.text = news.title
            if (news.urlToImage != null) {
                val postImage = news.urlToImage
                HelperMethod.onLoadImageFromUrl2(holder.cardnewsImg, postImage, context)
            }
        } catch (e: Exception) {
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class NewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var cardnewsImgShareBotton: FrameLayout
        var cardnewsTitleTv: TextView
        var cardnewsNameTv: TextView
        var cardnewsDescriptionTv: TextView
        var cardnewsImg: ImageView
        var cardnewsMainLy: FrameLayout

        init {
            cardnewsImgShareBotton = view.findViewById(R.id.card_news_item_share_btn)
            cardnewsMainLy = view.findViewById(R.id.card_news_item_framelayout)
            cardnewsImg = view.findViewById(R.id.card_news_item_imageview)
            cardnewsTitleTv = view.findViewById(R.id.card_news_news_items_title_tv)
            cardnewsNameTv = view.findViewById(R.id.card_news_item_name_tv)
            cardnewsDescriptionTv = view.findViewById(R.id.card_news_item_description_tv)
        }
    }

    //    JoinGroupInterFace joinGroupInterFace;
    init {
        //        this.joinGroupInterFace=joinGroupInterFace;
        this.activity = activity as BaseActivity
        this.context = context
    }
}