package com.example.newstaskapp.view.main.views.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.example.newstaskapp.R
import com.example.newstaskapp.databinding.ActivityHomeCycleBinding
import com.example.newstaskapp.view.main.utils.HelperMethod
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlin.math.log

class HomeCycleActivityKotlin : BaseActivityKotlin() {

    private val mAppBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityHomeCycleBinding? = null
    private val parentJob2 :Job =Job()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeCycleBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        HelperMethod.setStatusBar(this, R.color.blue)

    }

    fun coroutineExambles(){
        GlobalScope.launch(newSingleThreadContext("mohamed thread")) {
            var tt = async {
                prentTextAfterDelay("ttt")//from retrofit
            }
            var tt2 = async {
                prentTextAfterDelay("ttt")  //from database /room
            }
            if(tt.await()==tt2.await()){ // عاوزهم يستنو على ميتنفزوا وبعدين يدخل هنا يتشك
//
            }
            launch {
                prentTextAfterDelay("ttt2")
            }
            launch {
                prentTextAfterDelay("ttt2")
            }
//            launch == GlobalScope.launch  بس لازم جوه لو عاوز تنفذ اكتر من حاجه فى نفس الوقت
//           1- Dispatchers.Main    اى حاجه خفيفه زى الديزين اللايف داتا
//           2- Dispatchers.IO       هتجيب حاجه من الروم هتجيب حاجه من الداتا بيز هتجيب حاجه من الرتروفيت من الباك /api
//           3- Dispatchers.Default  لو هتعمل عمليات حسابيه هتاخد وقت كبير و اى حاجه بتاخد وقت كبير فى التنفيذ ودى الديفولط لو مضيفتوش
//            Dispatchers.Default is limited to the number of CPU cores (with a minimum of 2) so only N (where N == cpu cores) tasks can run in parallel in this dispatcher.
//           2- On the IO dispatcher there are by default 64 threads, so there could be up to 64 parallel tasks running on that dispatcher.
//            The idea is that the IO dispatcher spends a lot of time waiting (IO blocked), while the Default dispatcher is intended for CPU intensive tasks, where there is little or no sleep.
//           4- 5Dispatchers.Unconfined coroutine dispatcher starts a coroutine in the caller thread, but only until the first suspension point. After suspension it resumes the coroutine in the thread that is fully determined by the suspending function that was invoked. The unconfined dispatcher is appropriate for coroutines which neither consume CPU time nor update any shared data (like UI) confined to a specific thread.
//           5-  newSingleThreadContext("mohamed thread")  my thread
        }

//     runBlocking     لو عاوز تنفذ الحاجه بالترتيب يعنى لو فى تاخير اتاخر عادى وبعدين نفذ الباقى
//     GlobalScope.launch     لو عاوز تخلص كل حاجه فى المين وتسيب تاسك شغال فى الباك جروند بيتاخر عادى
////////////////////////////////////////////////////////////////////////////////////////
        var job: Job =  GlobalScope.launch(parentJob2) {
            var var1 = launch {
                prentTextAfterDelay("ttt")//from retrofit
            }
            var var2 = launch {
                prentTextAfterDelay("ttt")  //from database /room
            }
//            var2.join()
//            var1.join()
//            var1.cancelAndJoin() خلص الجوب دى الول وبعدين كنسلها
            joinAll(var1,var2)// لو عاوز تنفذ البنش الاول والتانى وبعدين التالت
            launch {
                delay(20000)
            }
        }
        job.cancel()
        parentJob2.cancel()  //لو فى لونش من اللى جوه وقفت ومكملتش البارنت هيبوظ فبالتالى تقدر تعمل البارنت ولتشيلد اللى جوه كانسل عادى
//   استخدمات الجوين لو عاوز تنفذ اكشن بعد انتهاء زى وقت السويب
// استخدمات الكنسل لو عاوز تتنقل فراجمنت تانيه وتوقف الكورياتين تستخدمها

        val coroutinesScope : CoroutineScope= CoroutineScope(Dispatchers.IO+parentJob2)
        coroutinesScope.launch{

//            حط كل اللى عاوز تعمله ولما تنقل من الفراجمنت كانسل فى الonstop
            launch {  }

        }
////////////////////////////////////////////////////////////////////////////////////////
        val coroutineChanel= Channel<String> { 2 }  // 2 هى عدد المتغيرات اللى تقدر تبعتها من لونش للتانيه
        val coroutineChanel2= Channel<String> { 2 }
        runBlocking {
            var arr = arrayOf("a", "b", "c")
            launch {
                for (char in arr){
                    coroutineChanel.send(char)// لو عاوز انفذ كل الحالات بس بتعين فى بفر لحد ما اللونش اللى بتستقبل تكون متاحه تستقبله
                    coroutineChanel2.offer(char)  //  لو عاوز انفذ مره واحده زى ضغطه زرار
                }
            }
            launch {
                for (char in coroutineChanel){
                    Log.d("hh",""+char)
                }
            }

            for (char in coroutineChanel2){
                Log.d("hh",""+char)// لو عاوز انفذ مره واحده
            }

        }
////////////////////////////////////////////////////////////////////////////////////////
        runBlocking {
            flow<Int> {
//  لتحل مشاكل الchannel ,memmoryleak
//                لو مستقبلتش اللى بيتبعت مش هتتنفذ
                for(i in 1..10){
                    emit(i)
                }
            }.filter { i :Int -> i>5}
                .buffer() .collect {  // بتستخدم لما متخلص اللى جوه هنا بس
                    Log.d("hh",""+it)
                }
//                .collect{   بتنفذ بعد متخلص كله هنا وفى الفلو فوق
//                    Log.d("hh",""+it)
//                }
        }

        ////////////////////////////////////////////////////////////////////////////////////////
        runBlocking {
            var folow1 = flow<Int> {
//  لتحل مشاكل الchannel ,memmoryleak
//                لو مستقبلتش اللى بيتبعت مش هتتنفذ
                for (i in 1..3) {
                    emit(i)
                }
            }
            var chars = listOf<String>("a", "b", "c", "d")
            var folow2 = flow<String> {
//  لتحل مشاكل الchannel ,memmoryleak
//                لو مستقبلتش اللى بيتبعت مش هتتنفذ
                for (char in chars) {
                    emit(char)
                }
            }
//فى حاله zip
//            بتنفذ بعد الديلاى الاكبر ولحد العدد الداتا ليست الاقل الاصغر
            folow1.zip(folow2){a:Int ,b ->"$a:$b"}.collect(){
//                1:a output
            }
//            lifecycleScope.launch(){} شغاله عادى فى الباك جروند
//              lifecycleScope.launchWhenStarted {  }  شغاله لو التطبيق مفتوح فقط
        }
    }

    suspend fun prentTextAfterDelay(s: String) {
      delay(2000)
        Log.d("klk","jkjh")
    }

    suspend fun prentTextAfterDelay2(s: String) {
        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            Log.d("klk", "jkjh")
            withContext(Dispatchers.Main){
//                اى حجه فى الui
//   GlobalScope.launch  اى حاجه تتنفذ بدسباتشر مختلف ولكنها لا تساوى
//   يعنى مقدرش انفذ تسكين فى نفس الوقت بيها زى GlobalScope
            }
        }
    }

//    override fun onStop() {
//        super.onStop()
//        parentJob2.cancel()
//    }

//    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        // Inflate the menu; this adds items to the action bar if it is present.
    //        getMenuInflater().inflate(R.menu.home_cycle, menu);
    //        return true;
    //    }
    //
    //    @Override
    //    public boolean onSupportNavigateUp() {
    //        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_cycle);
    //        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
    //                || super.onSupportNavigateUp();
    //    }


//ملاحظات
//    What is Lateinit in Kotlin?
//    The lateinit keyword allows you to avoid initializing a property when an object is constructed.
//    If your property is referenced before being initialized,
//    Kotlin throws an UninitializedPropertyAccessException
//    , so be sure to initialize your property as soon as possible

//VAR is used for creating those variable whose value will change over the course of time in your application. It is same as VAR of swift,
//whereas VAL is used for creating those variable whose value will not change over the course of time in your application.It is same as LET of swift.

//    structured concurrency    ابدء جلوبل وبعدين نفد اتنين جلوبل تانيين وبعدين خلص الجلوبل الاولى
//             يعنى التحكم فى تنفذ ايه وبعدين تخلص ايه وكده
//
}