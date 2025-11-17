package com.example.lab_week_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {
    // _total bersifat private dan bisa diubah (Mutable)
    private val _total = MutableLiveData<Int>()

    // total bersifat public dan hanya bisa dibaca (Immutable)
    val total: LiveData<Int> = _total

    // Inisialisasi nilai awal
    init {
        // postValue digunakan untuk set nilai (aman dari thread manapun)
        _total.postValue(0)
    }

    // Fungsi untuk menambah nilai
    fun incrementTotal() {
        // Ambil nilai saat ini, tambahkan 1, lalu post lagi
        _total.postValue(_total.value?.plus(1))
    }
}