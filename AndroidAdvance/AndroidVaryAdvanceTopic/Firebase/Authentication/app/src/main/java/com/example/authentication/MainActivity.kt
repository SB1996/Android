package com.example.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {

    private val TAG: String by lazy { MainActivity::class.java.simpleName }

    private lateinit var appbar: AppBarLayout
    private lateinit var toolbar: Toolbar
    private lateinit var optionSP: Spinner

    private lateinit var layoutBody: ConstraintLayout

    private lateinit var usernameET: EditText
    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var cPasswordET: EditText
    private lateinit var loginBTN: Button
    private lateinit var clearBTN: Button

    private lateinit var authBTN: View
    private lateinit var statusMassageTV: View

    private var selectedOption: String? = null
    private var potionArray: Array<String>?  = null

    private fun viewInitialized(){
        appbar = findViewById(R.id.appbar)
        toolbar = findViewById(R.id.toolbar)
        layoutBody = findViewById(R.id.layoutBody)
        optionSP = findViewById(R.id.spSelectItem)

        authBTN = findViewById(R.id.btnAuth)
        statusMassageTV = findViewById(R.id.tvStatusMassage)

        usernameET = findViewById(R.id.etUsername)
        emailET = findViewById(R.id.etEmail)
        passwordET = findViewById(R.id.etPassword)
        cPasswordET = findViewById(R.id.etConPassword)
        loginBTN = findViewById(R.id.btnSingup)
        clearBTN = findViewById(R.id.btnClear)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewInitialized()

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Firebase Auth"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.ic_firebase_small)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        ArrayAdapter.createFromResource(this, R.array.option, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            optionSP.adapter = adapter
        }
        potionArray = resources.getStringArray(R.array.option)
        optionSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedOption = "Nothing"
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                selectedOption = potionArray!![pos].toString()
                Log.d(TAG, "onCreate: $selectedOption")

                when(selectedOption){

                    "Email" -> {
                        layoutBody.visibility = View.VISIBLE
                        emailET.hint = "Email"
                        usernameET.visibility = View.GONE
                        cPasswordET.visibility = View.GONE

                    }
                    "Phone" -> {
                        layoutBody.visibility = View.VISIBLE
                        emailET.hint = "Phone No"
                        usernameET.visibility = View.GONE
                        cPasswordET.visibility = View.GONE

                    }
                    "Google" -> {
                        layoutBody.visibility = View.INVISIBLE

                    }
                    "Facebook" -> {
                        layoutBody.visibility = View.INVISIBLE

                    }
                    "Twitter" -> {
                        layoutBody.visibility = View.INVISIBLE

                    }
                    "Github" -> {
                        layoutBody.visibility = View.INVISIBLE

                    }
                    "Microsoft" -> {
                        layoutBody.visibility = View.INVISIBLE

                    }
                    "Anonymous" -> {
                        layoutBody.visibility = View.VISIBLE
                        usernameET.visibility = View.VISIBLE
                        emailET.visibility = View.VISIBLE
                        emailET.hint = "Email"
                        passwordET.visibility = View.VISIBLE
                        cPasswordET.visibility = View.VISIBLE

                    }


                    else -> {

                    }

                }
            }

        }


        authBTN.setOnClickListener {
            Log.d(TAG, "onCreate: Auth Button Clicked")
            if(selectedOption == null){
                Toast.makeText(this@MainActivity, "please select an option", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            when(selectedOption){

                "Email" -> {
                    Toast.makeText(this@MainActivity, "LoginWith : $selectedOption", Toast.LENGTH_SHORT).show()
                }
                "Phone" -> {
                    Toast.makeText(this@MainActivity, "LoginWith : $selectedOption", Toast.LENGTH_SHORT).show()

                }
                "Google" -> {
                    Toast.makeText(this@MainActivity, "LoginWith : $selectedOption", Toast.LENGTH_SHORT).show()

                }
                "Facebook" -> {
                    Toast.makeText(this@MainActivity, "LoginWith : $selectedOption", Toast.LENGTH_SHORT).show()

                }
                "Twitter" -> {
                    Toast.makeText(this@MainActivity, "LoginWith : $selectedOption", Toast.LENGTH_SHORT).show()

                }
                "Github" -> {
                    Toast.makeText(this@MainActivity, "LoginWith : $selectedOption", Toast.LENGTH_SHORT).show()

                }
                "Microsoft" -> {
                    Toast.makeText(this@MainActivity, "LoginWith : $selectedOption", Toast.LENGTH_SHORT).show()

                }
                "Anonymous" -> {
                    Toast.makeText(this@MainActivity, "LoginWith : $selectedOption", Toast.LENGTH_SHORT).show()

                }

                else -> {
                    Toast.makeText(this@MainActivity, "LoginWith : Nothing", Toast.LENGTH_SHORT).show()

                }

            }
        }




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_manu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menuLogout -> {
                Toast.makeText(this@MainActivity, "Logout Button Clicked", Toast.LENGTH_SHORT).show()

                true
            }

            else -> { false }
        }
    }
}
