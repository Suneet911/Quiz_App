package com.example.quizapp.activites.activites

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.activites.activites.adapters.QuizAdapter
import com.example.quizapp.activites.activites.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    lateinit var fireStore: FirebaseFirestore
    private var quizList = mutableListOf<Quiz>()

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()
    }


    private fun setUpViews() {
        setUpFireStore()
        setDrawer()
        setUpRecyclerView()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        val datePicker = findViewById<FloatingActionButton>(R.id.date_picker)
        datePicker.setOnClickListener {
            val dateBtn = MaterialDatePicker.Builder.datePicker().build()
            dateBtn.show(supportFragmentManager, "DatePicker")
            dateBtn.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", dateBtn.headerText)
                val dateFormatter = SimpleDateFormat("dd/MM/yyyy")
                val date = dateFormatter.format(Date(it))
                Log.d("DATEFORMAT", date.toString())
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            dateBtn.addOnNegativeButtonClickListener {
                // Respond to negative button click.
            }
            dateBtn.addOnCancelListener {
                // Respond to cancel button click.
            }
        }
    }


    private fun setUpFireStore() {
        fireStore = FirebaseFirestore.getInstance()
        val collectionReference = fireStore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
            }

            if (value != null) {
                Log.d("DATA", value.toObjects(Quiz::class.java).toString())
                quizList.clear()
                quizList.addAll(value.toObjects(Quiz::class.java))
                adapter.notifyDataSetChanged()

            }

        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(this, quizList)
        val recyclerView = findViewById<RecyclerView>(R.id.quizRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }

    private fun setDrawer() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.appBar)
        setSupportActionBar(toolbar)
        val mainDrawer = findViewById<DrawerLayout>(R.id.mainDrawer)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, mainDrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener {

            val profile = findViewById<View>(R.id.profile)
            profile.setOnClickListener {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                mainDrawer.closeDrawers()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}