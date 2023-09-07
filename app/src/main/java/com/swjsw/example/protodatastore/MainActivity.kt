package com.swjsw.example.protodatastore

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.swjsw.example.protodatastore.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindingViewModel()
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun bindingViewModel() {
        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = mainViewModel
        }
    }

    private fun initViews() {
        binding.etUserName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 시 호출됨
                mainViewModel.setUserNameForSaving(s.toString())
                // 변경된 텍스트 처리
            }

            override fun afterTextChanged(s: Editable?) {
                // 텍스트 변경 후에 호출됨
            }
        })

        binding.etUserEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트 변경 시 호출됨
                mainViewModel.setUserEmailForSaving(s.toString())
                // 변경된 텍스트 처리
            }

            override fun afterTextChanged(s: Editable?) {
                // 텍스트 변경 후에 호출됨
            }
        })
    }


}