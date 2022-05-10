package com.example.expirydatetracker.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.expirydatetracker.fragments.MainActivity
import com.example.expirydatetracker.api.EDTApi
import com.example.expirydatetracker.api.EDTApiClient
import com.example.expirydatetracker.fragments.RegisterFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragmentViewmodel(application: Application): AndroidViewModel(application) {
    private var api: EDTApi
    private var app: Application

    init{
        api = EDTApiClient.getApi()
        app = application
    }

    public fun register(username: String,
                        password:String,
                        confirmPassword:String,
                        name:String,
                        surname:String,
                        email:String,
                        fragment: RegisterFragment
    ){
        MainActivity.registerResponseCompleted = false
        MainActivity.registerResponseSuccessful = false
        api.registerUser(username, password, confirmPassword, name, surname, email).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                println(call.request().url().url().toString())
                if(response.code() == 200 && response.body() != null){
                    MainActivity.registerResponseCompleted = true;
                    MainActivity.registerResponseSuccessful = true;
                    Toast.makeText(app.applicationContext,"Registered successfully.", Toast.LENGTH_LONG).show()
                    fragment.redirect()
                }
                else{
                    MainActivity.registerResponseCompleted = true;
                    MainActivity.registerResponseSuccessful = false;
                    Toast.makeText(app.applicationContext, response.body().toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                MainActivity.registerResponseCompleted = true;
                MainActivity.registerResponseSuccessful = false;
                Toast.makeText(app.applicationContext,"Request failed.", Toast.LENGTH_LONG).show()
            }
        })
    }
}