package com.example.newstaskapp.view.main.views.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newstaskapp.R
import com.example.newstaskapp.databinding.FragmentHomeBinding
import com.example.newstaskapp.view.main.adapters.AllNewsAdapter
import com.example.newstaskapp.view.main.data.api.ApiClient
import com.example.newstaskapp.view.main.data.api.ApiClientKotlin
import com.example.newstaskapp.view.main.data.local.DataBase
import com.example.newstaskapp.view.main.data.models.getNewsListResponce.javaPojo.*
import com.example.newstaskapp.view.main.utils.dialogs.LocateNewsDialogK
import com.example.newstaskapp.view.main.utils.interfaces.MakeLoadNewsInteface
import com.example.newstaskapp.view.main.utils.interfaces.TryAgainOncall
import com.example.newstaskapp.view.main.views.fragments.BaseFragmentKotlin
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import retrofit2.Call
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class HomeFragmentKotlin : BaseFragmentKotlin(), MakeLoadNewsInteface, DatePickerDialog.OnDateSetListener,
    TryAgainOncall {
    private var homeViewModel: HomeViewModelK? = null
    private var binding: FragmentHomeBinding? = null
    private val bottomSheetDialog: BottomSheetDialog? = null
    private var root: View? = null
    private var titleTextView: TextView? = null
    private var searchBtnView: ImageView? = null
    private var backBtnView: ImageView? = null
    private var searchEdtxtView: AutoCompleteTextView? = null
    var articles: MutableList<Article> = ArrayList()
    var articleForRoomsList: MutableList<ArticleForRoom> = ArrayList()
    private var articleForRoom: ArticleForRoom? = null
    private var controller: NavController? = null
    private var fromDate: String? = null
    private var fragment_home_locate_news_from_date_btn: TextInputLayout? = null
    private val lLayout: LinearLayoutManager? = null
    private var noResultErrorTitle: TextView? = null
    private var fragmentHomeRecyclerView: RecyclerView? = null
    private var fragmentHomeSrRefresh: SwipeRefreshLayout? = null
    private var maxPage = 1
    private var Filter = false
    private var searchText: String? = null
    private var allNewsAdapter: AllNewsAdapter? = null
    private var type: String? = null
    private var getAllUserResponceCall: Call<GetNewsListResponce>? = null
    private var basicAuthorization: String? = null
    private var dataBase: DataBase? = null
    private var gLayout: GridLayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModelK::class.java)
        initListenerViewModel()
//        showToast(requireActivity(),"jjh")
        val dialog2 = LocateNewsDialogK()
        dialog2.showDialog(requireActivity(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        root = binding!!.root
        //        USER_Time = LoadData(getActivity(), USER_Time);
        controller = Navigation.findNavController(container!!)
        //        HelperWithKotlin.INSTANCE.showToastss(getActivity(),
//                "efe");
        initView()
        initRS()
        return root
    }

    private fun coroutineExamble() {
        lifecycleScope.launchWhenStarted {
            homeViewModel!!.allUserMainNewsDataState.collect {responce ->
                try {
                    if (responce != null) {
                            articles.clear()
                            articleForRoomsList.clear()
                            deleteLocalList()
                            articles.addAll(responce.articles)
                            articleListConverter()
                    } else {
                        //                        showToast(getActivity(), "max=");
                    }
                } catch (e: Exception) {
                }
            }
        }
//        lifecycleScope.launchWhenStarted {  بنعمل اتنين سكوب لو فى حاجه متاخره
//
//            homeViewModel!!.listOfSavedIngredients.collectLatest {  responce ->
//                try {
//                    if (responce != null) {
//                        articles.addAll(responce.articles)
//                        articleListConverter()
//                    } else {
//                        //                        showToast(getActivity(), "max=");
//                    }
//                } catch (e: Exception) {
//                }
//
//            }
//        }
    }

    private fun articleListConverter() {
        for (i in articles.indices) {
            articleForRoom = ArticleForRoom(
                articles[i].author, articles[i].title, articles[i].description,
                articles[i].url, articles[i].urlToImage, articles[i].publishedAt,
                articles[i].content, articles[i].source?.id, articles[i].source?.name
            )
            articleForRoomsList.add(articleForRoom!!)
            saveLocalList(articleForRoom!!)
        }
        //        showToast(getActivity(), articleForRoomsList.size()+"  "+articles.size());
        allNewsAdapter!!.notifyDataSetChanged()
        allNewsAdapter!!.notifyItemRangeChanged(0, allNewsAdapter!!.itemCount)
        if (articles.size > 0) {
            noResultErrorTitle!!.visibility = View.GONE
        } else {
//                                noResultErrorTitle.setText(getActivity().getString(R.string.waiting_for_your_flash_cards));
            noResultErrorTitle!!.visibility = View.VISIBLE
        }
    }

    private fun deleteLocalList() {
        Executors.newSingleThreadExecutor().execute {
            dataBase!!.addNewOrderItemDao()?.deletAll()
        }
    }

    private fun saveLocalList(articleForRoom: ArticleForRoom) {
        Executors.newSingleThreadExecutor().execute {
            dataBase!!.addNewOrderItemDao()?.insert(articleForRoom!!)
        }
    }

    private fun initListenerViewModel() {
        homeViewModel!!.allUserMainNewsDataResponce.observe(
            requireActivity(),
            { getNewsListResponce ->
                try {
                    if (getNewsListResponce != null) {
                        //                        if (response.getStatus().equals("success")) {
                        if (getNewsListResponce.articles != null) {
                            articles.clear()
                            articleForRoomsList.clear()
                            deleteLocalList()
                            articles.addAll(getNewsListResponce.articles)
                            articleListConverter()
                        }
                    } else {
                        //                        showToast(getActivity(), "max=");
                    }
                } catch (e: Exception) {
                }
            })

        homeViewModel!!.localList.observe(
            requireActivity(),
            { articleForRooms2 ->
                try {
                    //                    showToast(getActivity(), articleForRooms2.size()+"  "+articles.size());
                    articles.clear()
                    articleForRoomsList.clear()
                    articleForRoomsList.addAll(articleForRooms2)
                    allNewsAdapter!!.notifyDataSetChanged()
                    allNewsAdapter!!.notifyItemRangeChanged(0, allNewsAdapter!!.itemCount)
                    if (articleForRooms2.size > 0) {
                        noResultErrorTitle!!.visibility = View.GONE
                    } else {
                        //                                noResultErrorTitle.setText(getActivity().getString(R.string.waiting_for_your_flash_cards));
                        noResultErrorTitle!!.visibility = View.VISIBLE
                    }
                } catch (e: Exception) {
                }
            })
    }

    private fun initView() {
        setUpActivity()
        titleTextView = binding!!.home2Part.toolbar.appGeneralToolbarTitleTv
        searchBtnView = binding!!.home2Part.toolbar.appGeneralToolbarSearchImg
        backBtnView = binding!!.home2Part.toolbar.appGeneralToolbarBackImg
        searchEdtxtView = binding!!.home2Part.toolbar.appGeneralToolbarSearchAutoEdtxt
        noResultErrorTitle = binding!!.home2Part.notFound.noResultErrorTitle
        fragmentHomeRecyclerView = binding!!.home2Part.fragmentHomeRecyclerView
        fragmentHomeSrRefresh = binding!!.home2Part.fragmentHomeSrRefresh
        dataBase = DataBase.getInstance(requireContext())
        searchBtnView!!.visibility = View.VISIBLE
        //        backBtnView.setVisibility(View.VISIBLE);
        titleTextView!!.text = getText(R.string.last_news)


//        controller.navigate(R.id.action_coursesFragment_to_loginFragment);
        searchBtnView!!.setOnClickListener { openSearch() }
    }

    private fun initRS() {
//        lLayout = new LinearLayoutManager(getActivity());
        gLayout = GridLayoutManager(context, 1)
        fragmentHomeRecyclerView!!.layoutManager = gLayout
        fragmentHomeRecyclerView!!.setHasFixedSize(true)
        allNewsAdapter = AllNewsAdapter(articleForRoomsList, controller!!, requireActivity(), requireContext())
        fragmentHomeRecyclerView!!.adapter = allNewsAdapter
//                    showToast(getActivity(), "success adapter");
        if (articles.size == 0) {
            getallLastNewsList(1)
        }
        fragmentHomeSrRefresh!!.setOnRefreshListener { //                showToast(getActivity(), "success adapter");
            getallLastNewsList(1)
        }
    }

    private fun getallLastNewsList(page: Int) {
        basicAuthorization = "d7022c7c9f8f416ebb0cb356cce5abda"
        //        Filter = false;
        if (page == 1) {
            maxPage = 1
        }
//        startShimmer(page);
        reInit(page)
        if (Filter) {
            searchText = searchEdtxtView!!.text.toString()
            if (searchText!!.length == 0) {
                searchText = "apple"
                initResponceCall(searchText!!)
            } else {
//                Toast.makeText(getContext(), ""+maxPage, Toast.LENGTH_SHORT).show();
//                checkSearchText(searchText);
                initResponceCall(searchText!!)
            }
        } else {
            searchText = "apple"
            initResponceCall(searchText!!)
        }
        Log.d("eeeeeeeeeeeeee", "getallUserList: eeeeeeeee")
        homeViewModel!!.sendToGetAllUserMainNewsDataResponce(
            activity,
            getAllUserResponceCall!!,
            this,
            fragmentHomeSrRefresh!!,
            maxPage
        )
    }

    private fun getallLastNewsListWithCoroutine(page: Int) {
        basicAuthorization = "d7022c7c9f8f416ebb0cb356cce5abda"
        if (page == 1) {
            maxPage = 1
        }
        reInit(page)
        if (Filter) {
            searchText = searchEdtxtView!!.text.toString()
            if (searchText!!.length == 0) {
                searchText = "apple"
                initResponceCall(searchText!!)
            } else {
                initResponceCall(searchText!!)
            }
        } else {
            searchText = "apple"
            initResponceCall(searchText!!)
        }
        Log.d("eeeeeeeeeeeeee", "getallUserList: eeeeeeeee")
        val mainModelForSend =MainModelForSend()
        mainModelForSend.activity=activity
        mainModelForSend.fragmentHomeSrRefresh=fragmentHomeSrRefresh!!
        mainModelForSend.getAllUserResponceCall=getAllUserResponceCall!!
        mainModelForSend.homeFragment=this
        mainModelForSend.maxPage=maxPage
        homeViewModel!!.sendToGetAllUserMainNewsDataResponce2WithStateFolowCoroutine(
            mainModelForSend
        )
    }

    private fun iniData() {
        searchEdtxtView!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            Log.d("actionId", "OnKeyListener: " + keyCode + " - " + KeyEvent.KEYCODE_ENTER)
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                Filter = true
                getallLastNewsList(1)
                return@OnKeyListener true
            }
            false
        })
    }

    private fun initResponceCall(searchText: String) {
        if ("everything".equals(type, ignoreCase = true)) {
            getAllUserResponceCall= ApiClient.getApiClient()?.getNewsList(searchText, "popularity", fromDate, basicAuthorization)
        } else {
            getAllUserResponceCall= ApiClient.getApiClient()?.getTopNewsList(
                searchText,
                "publishedAt",
                fromDate,
                "us",
                "business",
                basicAuthorization
            )
        }
    }

    private fun reInit(page: Int) {
        articles = ArrayList()
        articleForRoomsList = ArrayList()
        allNewsAdapter = AllNewsAdapter(articleForRoomsList, controller!!, requireActivity(), requireContext())
        fragmentHomeRecyclerView!!.adapter = allNewsAdapter
    }

    override fun onBack() {
        if (Filter) {
            searchEdtxtView!!.visibility = View.GONE
            searchBtnView!!.visibility = View.VISIBLE
        }
        requireActivity().finish()
    }

    fun showDate() {
        val min_date_c = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog.newInstance(
            this,
            min_date_c[Calendar.YEAR],  // Initial year selection
            min_date_c[Calendar.MONTH],  // Initial month selection
            min_date_c[Calendar.DAY_OF_MONTH] // Inital day selection
        )
        val days = arrayOfNulls<Calendar>(13)
        for (i in -6..6) {
            val day = Calendar.getInstance()
            day.add(Calendar.DAY_OF_MONTH, i * 2)
            days[i + 6] = day
        }
        datePickerDialog.minDate = min_date_c
        // Setting Max Date to next 2 years
        val max_date_c = Calendar.getInstance()
        max_date_c[Calendar.YEAR] = min_date_c[Calendar.YEAR] + 4
        datePickerDialog.maxDate = max_date_c
        datePickerDialog.show(requireFragmentManager(), "Datepickerdialog")
    }

    private fun openSearch() {
        Filter = true
        iniData()
        searchEdtxtView!!.visibility = View.VISIBLE
        //        initAutoComplete();
        searchBtnView!!.visibility = View.GONE
    }

    private fun initAutoComplete() {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.custam_text_view, R.id.text_view_list_item, searchKeys);
//        searchEdtxtView.setAdapter(adapter);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val symbols = DecimalFormatSymbols(Locale.US)
        val mFormat = DecimalFormat("00", symbols)
        val date =
            year.toString() + "-" + mFormat.format(java.lang.Double.valueOf((monthOfYear + 1).toDouble())) + "-" + mFormat.format(
                java.lang.Double.valueOf(dayOfMonth.toDouble())
            )
        fragment_home_locate_news_from_date_btn!!.editText!!.setText(date)
        fromDate = date
    }

    override fun tryAgainCall(type: String) {}
    override fun makeDoneBtnCall(news_type: String) {
        type = news_type
        getallLastNewsList(1)
    }

    override fun openDatePickerBtnCall(fragment_home_locate_news_from_date_btn: TextInputLayout) {
        this.fragment_home_locate_news_from_date_btn = fragment_home_locate_news_from_date_btn
        showDate()
    }
}