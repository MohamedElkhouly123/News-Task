package com.example.newstaskapp.view.main.views.fragments.showArticleDetails;

import static com.example.newstaskapp.view.main.utils.HelperMethod.onLoadImageFromUrl2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.newstaskapp.R;
import com.example.newstaskapp.databinding.FragmentShowArticleDetailsBinding;
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.ArticleForRoom;
import com.example.newstaskapp.view.main.views.fragments.BaseFragment;

import java.text.BreakIterator;

public class ShowArticlsDetailsFragment extends BaseFragment {

    private ShowArticlsDetailsViewModel slideshowViewModel;
    private FragmentShowArticleDetailsBinding binding;
    private TextView titleTextView;
    private ImageView backBtnView;
    private ArticleForRoom articleListDatum;
    private TextView fragmentArticleDescriptionTv;
    private TextView fragmentArticleNameTv;
    private TextView fragmentArticleTitleTv;
    private ImageView fragmentArticleImg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (this.getArguments() != null) {
            articleListDatum = (ArticleForRoom) this.getArguments().getSerializable("Articleobj");
        }
        slideshowViewModel =
                new ViewModelProvider(this).get(ShowArticlsDetailsViewModel.class);

        binding = FragmentShowArticleDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setUpActivity();
        titleTextView = binding.details.toolbar.appGeneralToolbarTitleTv;
        backBtnView = binding.details.toolbar.appGeneralToolbarBackImg;
        fragmentArticleDescriptionTv = binding.details.fragmentArticleDescriptionTv;
        fragmentArticleNameTv = binding.details.fragmentArticleNameTv;
        fragmentArticleTitleTv = binding.details.fragmentArticleTitleTv;
        fragmentArticleImg = binding.details.fragmentArticleImg;
        setData();
        return root;
    }

    private void setData() {
        try {
            backBtnView.setVisibility(View.VISIBLE);
            titleTextView.setText(getString(R.string.article_details));
            fragmentArticleDescriptionTv.setText(articleListDatum.getDescription());
            fragmentArticleTitleTv.setText(articleListDatum.getTitle());
            fragmentArticleNameTv.setText(articleListDatum.getName());
            if (articleListDatum.getUrlToImage() != null) {
                String postImage = articleListDatum.getUrlToImage();
                onLoadImageFromUrl2(fragmentArticleImg, postImage, getContext());
            }
            backBtnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBack();
                }
            });
        }catch (Exception e){}
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}