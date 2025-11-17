package com.example.lab_week_10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.viewmodels.TotalViewModel

class MainActivity : AppCompatActivity() {

    // (Langkah 11) Buat instance DB
    private val db by lazy { prepareDatabase() } // [cite: 411]

    // (Langkah 11) Buat instance ViewModel
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    // (Langkah 13) Companion object untuk ID
    companion object {
        const val ID: Long = 1 // [cite: 450-451]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // (Langkah 12) Inisialisasi nilai dari database
        initializeValueFromDatabase() // [cite: 422]
        // (Langkah 12) Siapkan ViewModel (observer, dll)
        prepareViewModel() // [cite: 423]
    }

    // (Langkah 15) Simpan data saat Activity di-pause (misal: app ditutup)
    override fun onPause() { // [cite: 471]
        super.onPause()
        // Update database dengan nilai terbaru dari ViewModel
        db.totalDao().update(Total(ID, viewModel.total.value ?: 0)) // [cite: 474]
    }

    // (Langkah 13) Fungsi untuk membangun database
    private fun prepareDatabase(): TotalDatabase { // [cite: 430]
        return Room.databaseBuilder( // [cite: 432]
            applicationContext,
            TotalDatabase::class.java, "total-database" // [cite: 434]
        ).allowMainThreadQueries().build() // [cite: 435]
    }

    // (Langkah 13) Fungsi untuk mengambil data dari DB saat app start
    private fun initializeValueFromDatabase() { // [cite: 439]
        // Cek data di DB
        val total = db.totalDao().getTotal(ID) // [cite: 440]
        if (total.isEmpty()) { // [cite: 441]
            // Jika kosong, masukkan data baru (ID=1, total=0)
            db.totalDao().insert(Total(id = ID, total = 0)) // [cite: 442]
        } else {
            // Jika ada, set ViewModel dengan data dari DB
            viewModel.setTotal(total.first().total) // [cite: 444]
        }
    }

    // (Langkah 9 & 24 - tidak berubah)
    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    // (Langkah 24 - tidak berubah)
    private fun prepareViewModel() {
        viewModel.total.observe(this) { total ->
            updateText(total)
        }
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }
}