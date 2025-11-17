package com.example.lab_week_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.viewmodels.TotalViewModel

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
    }

    private fun updateText(total: Int) {
        // Gunakan view?.findViewById karena kita di dalam Fragment
        view?.findViewById<TextView>(R.id.text_total)?.text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel() {
        val viewModel =
            ViewModelProvider(requireActivity())[TotalViewModel::class.java]

        // 1. Observe LiveData
        //    Gunakan 'viewLifecycleOwner' di Fragment
        viewModel.total.observe(viewLifecycleOwner) { total ->
            // 2. Setiap kali nilai 'total' berubah,
            //    kode ini akan dijalankan.
            updateText(total)
        }
    }
}