package com.example.daggerdependenceyinjection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daggerdependenceyinjection.data.Car
import com.example.daggerdependenceyinjection.di.component.DaggerMainComponent
import com.example.daggerdependenceyinjection.di.component.MainComponent
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName

    /*** Main Component ***/
    private lateinit var mainComponent: MainComponent

    @Inject lateinit var car: Car

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        assembleBtn.setOnClickListener {

            mainComponent = DaggerMainComponent.builder().build()
            mainComponent.mainInjection(this@MainActivity)

            car.assembleCar()
            car.drive()
        }
    }
}
