package com.example.newstaskapp.view.main.views.fragments.home;

import static com.example.newstaskapp.view.main.data.api.ApiClient.getApiClient;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newstaskapp.R;
import com.example.newstaskapp.databinding.FragmentHomeBinding;
import com.example.newstaskapp.view.main.adapters.AllNewsAdapterJava;
import com.example.newstaskapp.view.main.data.local.DataBase;
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.Article;
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.ArticleForRoom;
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.GetNewsListResponce;
import com.example.newstaskapp.view.main.utils.dialogs.LocateNewsDialog;
import com.example.newstaskapp.view.main.utils.interfaces.MakeLoadNewsInteface;
import com.example.newstaskapp.view.main.utils.interfaces.TryAgainOncall;
import com.example.newstaskapp.view.main.views.fragments.BaseFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import retrofit2.Call;


public class HomeFragment extends BaseFragment implements MakeLoadNewsInteface, DatePickerDialog.OnDateSetListener, TryAgainOncall {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private BottomSheetDialog bottomSheetDialog;
    private View root;
    private TextView titleTextView;
    private ImageView searchBtnView;
    private ImageView backBtnView;
    private AutoCompleteTextView searchEdtxtView;
    List<Article> articles = new ArrayList<>();
    List<ArticleForRoom> articleForRoomsList = new ArrayList<>();
    private ArticleForRoom articleForRoom;
    private NavController controller;
    private String fromDate;
    private TextInputLayout fragment_home_locate_news_from_date_btn;
    private LinearLayoutManager lLayout;
    private TextView noResultErrorTitle;
    private RecyclerView fragmentHomeRecyclerView;
    private SwipeRefreshLayout fragmentHomeSrRefresh;
    private int maxPage=1;
    private boolean Filter=false;
    private String searchText;
    private AllNewsAdapterJava allNewsAdapter;
    private String type;
    private Call<GetNewsListResponce> getAllUserResponceCall;
    private String basicAuthorization;
    private DataBase dataBase;
    private GridLayoutManager gLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        initListenerViewModel();
        final LocateNewsDialog dialog2 = new LocateNewsDialog();
        dialog2.showDialog(getActivity(), this);
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
//        USER_Time = LoadData(getActivity(), USER_Time);
        controller = Navigation.findNavController(container);
//        HelperWithKotlin.INSTANCE.showToastss(getActivity(),
//                "efe");
        initView();
        initRS();
        return root;
    }

    private void articleListConverter() {
        for(int i=0;i<articles.size();i++){
             articleForRoom=new ArticleForRoom(
                     articles.get(i).getAuthor(),articles.get(i).getTitle(),articles.get(i).getDescription(),
                     articles.get(i).getUrl(),articles.get(i).getUrlToImage(),articles.get(i).getPublishedAt(),
                     articles.get(i).getContent(),articles.get(i).getSource().getId(),articles.get(i).getSource().getName()
                     );
            articleForRoomsList.add(articleForRoom);
            saveLocalList(articleForRoom);
        }
//        showToast(getActivity(), articleForRoomsList.size()+"  "+articles.size());
        allNewsAdapter.notifyDataSetChanged();
        allNewsAdapter.notifyItemRangeChanged(0, allNewsAdapter.getItemCount());
        if (articles.size() > 0) {
            noResultErrorTitle.setVisibility(View.GONE);
        } else {
//                                noResultErrorTitle.setText(getActivity().getString(R.string.waiting_for_your_flash_cards));
            noResultErrorTitle.setVisibility(View.VISIBLE);
        }
    }

    private void deleteLocalList() {
        Executors.newSingleThreadExecutor().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        dataBase.addNewOrderItemDao().deletAll();
                    }

                });
    }

    private void saveLocalList(ArticleForRoom articleForRoom) {
        Executors.newSingleThreadExecutor().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        dataBase.addNewOrderItemDao().insert(articleForRoom);
                    }

                });
    }

    private void initListenerViewModel() {
        homeViewModel.getAllUserMainNewsDataResponce().observe(getActivity(), new Observer<GetNewsListResponce>() {
            @Override
            public void onChanged(GetNewsListResponce getNewsListResponce) {
                try {
                    if (getNewsListResponce != null) {
//                        if (response.getStatus().equals("success")) {

                        if (getNewsListResponce.getArticles() != null) {
                            articles.clear();
                            articleForRoomsList.clear();
                            deleteLocalList();
                            articles.addAll(getNewsListResponce.getArticles() );
                            articleListConverter();
                        }
                    } else {
//                        showToast(getActivity(), "max=");
                    }

                } catch (Exception e) {
                }

            }
        });
        homeViewModel.getLocalList().observe(getActivity(), new Observer<List<ArticleForRoom>>() {
            @Override
            public void onChanged(List<ArticleForRoom> articleForRooms2) {
                try {
//                    showToast(getActivity(), articleForRooms2.size()+"  "+articles.size());
                    articles.clear();
                    articleForRoomsList.clear();
                    articleForRoomsList.addAll(articleForRooms2);
                    allNewsAdapter.notifyDataSetChanged();
                    allNewsAdapter.notifyItemRangeChanged(0, allNewsAdapter.getItemCount());
                if (articleForRooms2.size() > 0) {
                    noResultErrorTitle.setVisibility(View.GONE);
                } else {
//                                noResultErrorTitle.setText(getActivity().getString(R.string.waiting_for_your_flash_cards));
                    noResultErrorTitle.setVisibility(View.VISIBLE);
                }
            }  catch (Exception e) {
            }
            }
        });
    }

    private void initView() {
        setUpActivity();
        titleTextView = binding.home2Part.toolbar.appGeneralToolbarTitleTv;
        searchBtnView = binding.home2Part.toolbar.appGeneralToolbarSearchImg;
        backBtnView = binding.home2Part.toolbar.appGeneralToolbarBackImg;
        searchEdtxtView = binding.home2Part.toolbar.appGeneralToolbarSearchAutoEdtxt;
        noResultErrorTitle = binding.home2Part.notFound.noResultErrorTitle;
        fragmentHomeRecyclerView = binding.home2Part.fragmentHomeRecyclerView;
        fragmentHomeSrRefresh = binding.home2Part.fragmentHomeSrRefresh;
        dataBase = DataBase.getInstance(getContext());

        searchBtnView.setVisibility(View.VISIBLE);
//        backBtnView.setVisibility(View.VISIBLE);
        titleTextView.setText(getText(R.string.last_news));


//        controller.navigate(R.id.action_coursesFragment_to_loginFragment);
        searchBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch();
            }
        });
    }

    private void initRS() {
//        lLayout = new LinearLayoutManager(getActivity());
        gLayout = new GridLayoutManager(getContext(), 1);
        fragmentHomeRecyclerView.setLayoutManager(gLayout);
        fragmentHomeRecyclerView.setHasFixedSize(true);
        allNewsAdapter = new AllNewsAdapterJava(articleForRoomsList, controller, getActivity(), getContext());
        fragmentHomeRecyclerView.setAdapter(allNewsAdapter);
//            showToast(getActivity(), "success adapter");
        if (articles.size() == 0) {
            getallLastNewsList(1);
        }

        fragmentHomeSrRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                showToast(getActivity(), "success adapter");
                getallLastNewsList(1);

            }
        });
    }


    private void getallLastNewsList(int page) {
        basicAuthorization = "d7022c7c9f8f416ebb0cb356cce5abda";
//        Filter = false;
        if (page == 1) {
            maxPage = 1;
        }

//        startShimmer(page);

        reInit(page);
        if (Filter) {
            searchText = searchEdtxtView.getText().toString();
            if (searchText.length() == 0) {
                searchText="apple";
                initResponceCall(searchText);
            } else {
//                Toast.makeText(getContext(), ""+maxPage, Toast.LENGTH_SHORT).show();
//                checkSearchText(searchText);
                initResponceCall(searchText);
            }
        } else {
            searchText="apple";
            initResponceCall(searchText);
        }
        Log.d("eeeeeeeeeeeeee", "getallUserList: eeeeeeeee");
        homeViewModel.sendToGetAllUserMainNewsDataResponce(getActivity(), getAllUserResponceCall, this, fragmentHomeSrRefresh, maxPage);
    }

    private void iniData() {

        searchEdtxtView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey (View v,int keyCode, KeyEvent event){
                Log.d("actionId", "OnKeyListener: " + keyCode + " - " + KeyEvent.KEYCODE_ENTER);
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Filter = true;
                    getallLastNewsList(1);
                    return true;
                }
                return false;
            }
        });
    }


    private void initResponceCall(String searchText) {
        if("everything".equalsIgnoreCase(type)) {
            getAllUserResponceCall = getApiClient().getNewsList(searchText,"popularity",fromDate, basicAuthorization);
        }else {
            getAllUserResponceCall = getApiClient().getTopNewsList(searchText, "publishedAt", fromDate, "us", "business", basicAuthorization);
        }
    }

    private void reInit(int page) {
        articles = new ArrayList<>();
        articleForRoomsList = new ArrayList<>();
        allNewsAdapter = new AllNewsAdapterJava(articleForRoomsList, controller, getActivity(), getContext());
        fragmentHomeRecyclerView.setAdapter(allNewsAdapter);
    }

    @Override
    public void onBack() {
        if(Filter){
            searchEdtxtView.setVisibility(View.GONE);
            searchBtnView.setVisibility(View.VISIBLE);
        }
        getActivity().finish();
    }

    public void showDate() {
        Calendar min_date_c = getInstance();

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance( this,
                min_date_c.get(YEAR), // Initial year selection
                min_date_c.get(MONTH), // Initial month selection
                min_date_c.get(DAY_OF_MONTH) // Inital day selection

        );
        Calendar[] days = new Calendar[13];
        for (int i = -6; i < 7; i++) {
            Calendar day = getInstance();
            day.add(DAY_OF_MONTH, i * 2);
            days[i + 6] = day;
        }
        datePickerDialog.setMinDate(min_date_c);
        // Setting Max Date to next 2 years
        Calendar max_date_c = getInstance();
        max_date_c.set(YEAR, min_date_c.get(YEAR) + 4);
        datePickerDialog.setMaxDate(max_date_c);
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");

    }

    private void openSearch() {
        Filter = true;
        iniData();
        searchEdtxtView.setVisibility(View.VISIBLE);
//        initAutoComplete();
        searchBtnView.setVisibility(View.GONE);
    }

    private void initAutoComplete() {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.custam_text_view, R.id.text_view_list_item, searchKeys);
//        searchEdtxtView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat mFormat = new DecimalFormat("00", symbols);
        String date = year + "-" + mFormat.format(Double.valueOf((monthOfYear + 1))) + "-" + mFormat.format(Double.valueOf(dayOfMonth));
        fragment_home_locate_news_from_date_btn.getEditText().setText(date);
        fromDate = date;
    }

    @Override
    public void tryAgainCall(String type) {

    }

    @Override
    public void makeDoneBtnCall(String news_type) {
       type=news_type;
       getallLastNewsList(1);
    }

    @Override
    public void openDatePickerBtnCall(TextInputLayout fragment_home_locate_news_from_date_btn) {
      this.fragment_home_locate_news_from_date_btn=fragment_home_locate_news_from_date_btn;
      showDate();
    }
}