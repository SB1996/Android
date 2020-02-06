package com.example.reactivex.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.reactivex.R
import com.example.reactivex.data.DataProvider
import com.example.reactivex.data.UserData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG: String by lazy { MainActivity::class.java.simpleName }

    private val massage: String = "Hii i'm ReactiveX[Kotlin/Android], and you?"

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnSave.setOnClickListener {

            progressBar.visibility = View.VISIBLE

            compositeDisposable = CompositeDisposable()

            val userObservable: Observable<UserData>? = Observable
                .fromIterable<UserData>(DataProvider.userDataProvider())
                .subscribeOn(Schedulers.io())
                .filter { user: UserData ->
                    Log.d(TAG, "filter: Thread On Run : ${Thread.currentThread().name}")

                    Thread.sleep(1000)
                    user.isComplete = true
                    Thread.sleep(1000)

                    user.isComplete
                }
                .observeOn(AndroidSchedulers.mainThread())


            userObservable?.subscribe(object : Observer<UserData> {

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe : called")
                    Log.d(TAG, "onSubscribe : Thread On Run : ${Thread.currentThread().name}")

                    compositeDisposable.add(d)
                }

                override fun onNext(userData: UserData) {
                    Log.d(TAG, "onNext : called")
                    Log.d(TAG, "onNext : Thread On Run : ${Thread.currentThread().name}")


                    Log.d(TAG, "onNext : ___________________________________________")
                    Log.d(TAG, "onNext : Name : ${userData.name}")
                    Log.d(TAG, "onNext : Nik Name : ${userData.nikName}")
                    Log.d(TAG, "onNext : Email : ${userData.email}")
                    Log.d(TAG, "onNext : Phone No : ${userData.contactNo}")
                    Log.d(TAG, "onNext : ___________________________________________")

                    textView.append("Name : ${userData.name} \n\n")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete : called")
                    Log.d(TAG, "onComplete : Thread On Run : ${Thread.currentThread().name}")

                    progressBar.visibility = View.INVISIBLE
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError : called")
                    Log.d(TAG, "onError : Thread On Run : ${Thread.currentThread().name}")
                    Log.e(TAG, "onError: $e")
                }

            })
        }

        btnCancel.setOnClickListener {
            textView.text = ""

        }

    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }
}
