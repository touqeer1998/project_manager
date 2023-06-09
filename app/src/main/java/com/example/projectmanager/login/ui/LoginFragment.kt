package com.example.projectmanager.login.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projectmanager.R
import com.example.projectmanager.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email: String = binding.etEmail.text.toString().trim()
        val pwd: String = binding.etPassword.text.toString().trim()
        if (validateForm(email, pwd)) {
            Log.i(TAG, "login: Valid")
            (activity as LoginActivity).showProgressDialog(getString(R.string.please_wait))
            auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(requireActivity()) { task ->
                    (activity as LoginActivity).hideProgressDialog()
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Successfully Signed In",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            requireContext(),
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                (activity as LoginActivity).showErrorMessageSnackBar("Enter Email")
                false
            }
            TextUtils.isEmpty(password) -> {
                (activity as LoginActivity).showErrorMessageSnackBar("Enter Password")
                false
            }
            else -> return true
        }
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}