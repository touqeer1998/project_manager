package com.example.projectmanager.login.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.projectmanager.R
import com.example.projectmanager.databinding.FragmentSignupBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "SignUpFragment"

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignup.setOnClickListener {
            registerUser()
        }
        binding.taSingInInstead.setOnClickListener {
            findNavController().navigate(R.id.action_SignUpFragment_to_loginFragment2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                (activity as LoginActivity).showErrorMessageSnackBar("Enter Name")
                false
            }
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

    private fun registerUser() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        if (validateForm(name, email, password)) {
            (activity as LoginActivity).showProgressDialog(getString(R.string.loading))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener {
                (activity as LoginActivity).hideProgressDialog()
                if (it.isSuccessful) {
                    val firebaseUser = it.result.user
                    val registeredEmail = firebaseUser?.email
                    Toast.makeText(
                        requireContext(), "$registeredEmail is" +
                                "successfully registered", Toast.LENGTH_SHORT
                    ).show()
                    FirebaseAuth.getInstance().signOut()
                    findNavController().navigate(R.id.action_SignUpFragment_to_loginFragment2)
                } else {
                    Log.i(TAG, "registerUser: ${it.exception?.message}")
                    Toast.makeText(
                        requireContext(), "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}