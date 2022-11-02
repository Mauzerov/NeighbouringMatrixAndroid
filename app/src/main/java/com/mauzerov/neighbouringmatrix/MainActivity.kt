package com.mauzerov.neighbouringmatrix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mauzerov.neighbouringmatrix.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel = MainActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        binding.swipeRefresh.setOnRefreshListener {
//            viewModel.notifyPropertyChanged(BR._all)
//            binding.swipeRefresh.isRefreshing = false
//        }
    }
}