package com.example.expirydatetracker.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.expirydatetracker.R
import com.example.expirydatetracker.viewmodels.RegisterFragmentViewmodel

class RegisterFragment : Fragment() {
    private lateinit var viewmodel: RegisterFragmentViewmodel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = RegisterFragmentViewmodel(application = requireActivity().application)

        val registerButton: Button = requireView().findViewById<Button>(R.id.button_register)

        registerButton.setOnClickListener {

            val username : String =  requireView().findViewById<EditText>(R.id.usernameField).text.toString()
            val password : String =  requireView().findViewById<EditText>(R.id.passwordField).text.toString()
            val confirmPassword : String =  requireView().findViewById<EditText>(R.id.confirmPasswordField).text.toString()
            val name : String =  requireView().findViewById<EditText>(R.id.nameField).text.toString()
            val surname : String =  requireView().findViewById<EditText>(R.id.surnameField).text.toString()
            val email : String =  requireView().findViewById<EditText>(R.id.emailField).text.toString()

            if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty()){
                //do nothing.
                Toast.makeText(this.requireContext(), "Мора да се пополнети сите полиња.", Toast.LENGTH_SHORT).show()
            }
            else{
                viewmodel.register(username, password, confirmPassword, name, surname, email, this)
            }
        }
    }
    fun redirect(){
        if(MainActivity.registerResponseSuccessful){
            findNavController().navigateUp()
        }
    }
}