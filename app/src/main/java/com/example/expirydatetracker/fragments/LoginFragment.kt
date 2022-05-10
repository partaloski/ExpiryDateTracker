package com.example.expirydatetracker.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.expirydatetracker.R
import com.example.expirydatetracker.databinding.FragmentFirstBinding
import com.example.expirydatetracker.viewmodels.LoginFragmentViewmodel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var passwordField : EditText
    private lateinit var usernameField : EditText
    private lateinit var viewmodel : LoginFragmentViewmodel;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = LoginFragmentViewmodel(application = requireActivity().application)
        viewmodel.wakeUp()
        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_registerFragment)
        }

        uses = 1

        binding.buttonLogin.setOnClickListener {
            passwordField = requireView().findViewById(R.id.passwordField)
            usernameField = requireView().findViewById(R.id.usernameField)
            if(passwordField.text.isNullOrEmpty() || usernameField.text.isNullOrEmpty()){
                Toast.makeText(context, "The fields of username and password are required.", Toast.LENGTH_SHORT).show()
            }
            else{
                viewmodel.login(username = usernameField.text.toString(), password = passwordField.text.toString(), this)
            }
        }
    }

    fun redirect(){
        if(MainActivity.auth_code != null && uses > 0){
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            activity?.invalidateOptionsMenu()
            uses = 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        public var uses: Int = 1
    }
}