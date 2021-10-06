package com.deskmateai.quizapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deskmateai.quizapp.R
import com.deskmateai.quizapp.adapters.QuizAdapter
import com.deskmateai.quizapp.models.Quizzes
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var appBar: androidx.appcompat.widget.Toolbar
    lateinit var drawer: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter:QuizAdapter
    private var quizList = mutableListOf<Quizzes>()
    lateinit var quizRecyclerView:RecyclerView
    lateinit var firestore: FirebaseFirestore
    lateinit var floatingBtn: FloatingActionButton
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appBar = findViewById(R.id.topAppBar)
        drawer = findViewById(R.id.drawer_layout)
        quizRecyclerView = findViewById(R.id.quiz_recyclerview)
        floatingBtn = findViewById(R.id.btn_datePicker)
        navigationView = findViewById(R.id.navigation_view)
        navigationView.itemIconTintList = null


        setupViews()


    }



    private fun setupViews(){
        setupFireStore()
        setupDrawerLayout()
        setupRecyclerView()
        setupDatePicker()

    }

    private fun setupDatePicker() {
        floatingBtn.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager,"DatePicker")

            datePicker.addOnPositiveButtonClickListener {

                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")

                val date = dateFormatter.format(Date(it))

                Log.d("DatePicker", date)

                val intent = Intent(this,QuestionActivity::class.java)
                intent.putExtra("DATE",date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DatePicker",datePicker.headerText)

            }
            datePicker.addOnCancelListener {
                Log.d("DatePicker","Date Picker was cancelled")
            }
        }
    }

    private fun setupFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference:CollectionReference = firestore.collection("Quizzes")
        
        //snapshot listener will notify and show snapshot if any update happen in our database collection
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error!=null){
                Toast.makeText(this,"Error in fetching Data",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("data",value.toObjects(Quizzes::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quizzes::class.java))
            adapter.notifyDataSetChanged()
        }

    }

    private fun setupRecyclerView() {
        adapter = QuizAdapter(this,quizList)
        quizRecyclerView.layoutManager = GridLayoutManager(this,2)
        quizRecyclerView.adapter = adapter

    }

    private fun setupDrawerLayout() {
        setSupportActionBar(appBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawer,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.btn_profile -> {
                    val intent = Intent(this,ProfileActivity::class.java)
                    startActivity(intent)
                }

                R.id.web -> {
                    val url = "https://deskmateai.tech/"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }

                R.id.fb -> {
                    val url = "https://www.instagram.com/deskmateai/"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)

                }

                R.id.twitter -> {
                    val url = "https://twitter.com/DeskmateAI"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }

                R.id.insta -> {
                    val url = "https://www.instagram.com/deskmateai/"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }

                R.id.linkedin -> {
                    val url = "https://www.linkedin.com/company/deskmate-ai/"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }

                R.id.contact -> {

                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:") // only email apps should handle this
                    }
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("contactus@deskmateai.tech"));

                    startActivity(intent)

                }

                R.id.btn_rateUs -> Toast.makeText(this,"Rate Us feature is not available now", Toast.LENGTH_SHORT).show()
            }

            drawer.closeDrawers()
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}


