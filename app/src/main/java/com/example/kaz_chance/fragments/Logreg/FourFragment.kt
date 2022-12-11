package com.example.kaz_chance.fragments.Logreg

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.kaz_chance.R
import com.example.kaz_chance.data.User
import com.example.kaz_chance.databinding.FragmentFourBinding
import com.example.kaz_chance.util.Resource
import com.example.kaz_chance.viewmodel.LogRegViewModel
import dagger.hilt.android.AndroidEntryPoint

private val TAG = "FourFragment"
@AndroidEntryPoint
class FourFragment:  Fragment() {
    private lateinit var binding: FragmentFourBinding
    private val viewModel by viewModels<LogRegViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonRegister.setOnClickListener {
                val user = User(
                    edFirstName.text.toString().trim(),
                    edLastName.text.toString().trim(),
                    edEmail.text.toString().trim()
                    )
                val password = edPassword.text.toString()
                viewModel.createAccountWithEmailAndPassword(user,password)

            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.register.collect{
                when(it){
                    is Resource.Loading -> {
                        binding.buttonRegister.startAnimation()

                    }
                    is Resource.Success -> {
                        Log.d("test",it.data.toString())
                        binding.buttonRegister.revertAnimation()

                    }
                    is Resource.Error -> {
                        Log.e(TAG,it.message.toString())
                        binding.buttonRegister.revertAnimation()

                    }
                }
            }
        }
    }

}




