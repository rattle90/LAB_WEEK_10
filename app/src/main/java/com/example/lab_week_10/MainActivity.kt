package com.example.lab_week_10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast // [BONUS] Import Toast
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.database.TotalObject // [BONUS] Import TotalObject
import com.example.lab_week_10.viewmodels.TotalViewModel
import java.util.Date // [BONUS] Import Date

class MainActivity : AppCompatActivity() {

    private val db by lazy { prepareDatabase() }
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    companion object {
        const val ID: Long = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeValueFromDatabase()
        prepareViewModel()
    }

    override fun onStart() {
        super.onStart()
        val totalData = db.totalDao().getTotal(ID)
        if (totalData.isNotEmpty()) {
            val lastUpdateDate = totalData.first().total.date
            Toast.makeText(this, lastUpdateDate, Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        val currentValue = viewModel.total.value ?: 0
        val currentDate = Date().toString() //

        db.totalDao().update(
            Total(ID, TotalObject(value = currentValue, date = currentDate)) //
        )
    }

    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun initializeValueFromDatabase() {
        val total = db.totalDao().getTotal(ID)
        if (total.isEmpty()) {
            val initialDate = Date().toString()
            db.totalDao().insert(
                Total(id = ID, total = TotalObject(value = 0, date = initialDate))
            )
            viewModel.setTotal(0)
        } else {
            viewModel.setTotal(total.first().total.value)
        }
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel() {
        viewModel.total.observe(this) { total ->
            updateText(total)
        }
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }
}