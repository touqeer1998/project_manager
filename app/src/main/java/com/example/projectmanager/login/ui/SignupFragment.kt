package com.example.projectmanager.login.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.projectmanager.R
import com.example.projectmanager.databinding.FragmentMainBinding
import com.example.projectmanager.databinding.FragmentSignUpBinding
import com.google.android.material.appbar.MaterialToolbar

class SignupFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        //check validity of entered information by user
        binding.btnSignup.setOnClickListener {
            if (isInputValid()) {
                Toast.makeText(requireContext(), "Going to Home", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Invalid Input", Toast.LENGTH_SHORT).show()
            }
        }
        binding.taSingInInstead.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().actionBar?.title = getString(R.string.sign_up)
    }

    private fun isInputValid():Boolean {
        return !(binding.etName.text.isEmpty() || binding.etEmail.text.isEmpty() || binding.etPassword.text.isEmpty())
    }
}