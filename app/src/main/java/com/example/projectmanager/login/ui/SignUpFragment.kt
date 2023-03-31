package com.example.projectmanager.login.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.projectmanager.R
import com.example.projectmanager.databinding.FragmentSignupBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text
import kotlin.concurrent.timerTask

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

    private fun validateForm(name: String, email:String, password: String):Boolean{
        return when{
            TextUtils.isEmpty(name)->{
                (activity as MainActivity).showErrorMessageSnackBar("Enter Name")
                false
            }
            TextUtils.isEmpty(email)->{
                (activity as MainActivity).showErrorMessageSnackBar("Enter Email")
                false
            }
            TextUtils.isEmpty(password)->{
                (activity as MainActivity).showErrorMessageSnackBar("Enter Password")
                false
            }
            else-> return true
        }
    }

    private fun registerUser(){
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        if (validateForm(name,email,password)){
            (activity as MainActivity).showProgressDialog(getString(R.string.loading))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,
                password).addOnCompleteListener{
                (activity as MainActivity).hideProgressDialog()
                if (it.isSuccessful){
                    val firebaseUser = it.result.user
                    val registeredEmail = firebaseUser?.email
                    Toast.makeText(requireContext(),"$registeredEmail is" +
                            "successfully registered", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    findNavController().navigate(R.id.action_SignUpFragment_to_loginFragment2)
                }else{
                    Log.i(TAG, "registerUser: ${it.exception?.message}")
                    Toast.makeText(requireContext(),"Error",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}