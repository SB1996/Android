package com.example.dagger.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.dagger.R
import android.content.SharedPreferences
import android.widget.Toast
import javax.inject.Inject
import com.example.dagger.di.dagger.component.SharedPrefComponent
import com.example.dagger.di.dagger.component.DaggerSharedPrefComponent
import com.example.dagger.di.dagger.module.SharedPrefModule

class MainActivity : AppCompatActivity() {

    private lateinit var sharePrefValueET: EditText
    private lateinit var sharePrefKeyET: EditText
    private lateinit var saveBTN: Button
    private lateinit var getBTN: Button
    private lateinit var showTV: TextView

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var sharedPrefComponent: SharedPrefComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharePrefValueET = findViewById<EditText>(R.id.etValue)
        sharePrefKeyET = findViewById<EditText>(R.id.etKey)
        saveBTN = findViewById<Button>(R.id.btnSave)
        getBTN = findViewById<Button>(R.id.btnGet)
        showTV = findViewById<TextView>(R.id.tvDisplay)

        sharedPrefComponent = DaggerSharedPrefComponent.builder().sharedPrefModule(SharedPrefModule(this@MainActivity, "UserDetails")).build()
        sharedPrefComponent.sharedPrefInject(this@MainActivity)

        saveBTN.setOnClickListener {
            val key = sharePrefKeyET.text.toString()
            val value = sharePrefValueET.text.toString()

            if(key.isNotEmpty() && value.isNotEmpty()){
                sharedPreferences.edit().also {
                    it.putString(key, value).apply()
                }
                if(sharedPreferences.getString(key, null) != null){
                    Toast.makeText(this@MainActivity, "Insert Successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this@MainActivity, "Nothing", Toast.LENGTH_SHORT).show()
            }
        }

        getBTN.setOnClickListener {
            val allEntries = sharedPreferences.getAll()
            if(allEntries.isNotEmpty()){
                showTV.text = ""
                for ((key, value) in allEntries) {
                    showTV.append("$key -> $value \n")
                }
            }else{
                Toast.makeText(this@MainActivity, "Empty SharedPreferences", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
