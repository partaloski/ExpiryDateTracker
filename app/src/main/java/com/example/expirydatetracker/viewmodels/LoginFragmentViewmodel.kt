package com.example.expirydatetracker.viewmodels

import android.app.Application
import android.widget.Switch
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.fragment.findNavController
import com.example.expirydatetracker.R
import com.example.expirydatetracker.fragments.MainActivity
import com.example.expirydatetracker.api.EDTApi
import com.example.expirydatetracker.api.EDTApiClient
import com.example.expirydatetracker.fragments.LoginFragment
import com.example.expirydatetracker.models.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.io.File
import java.io.FileOutputStream

class LoginFragmentViewmodel(application: Application):AndroidViewModel(application){
    private var api: EDTApi
    private var app: Application

    init{
        api = EDTApiClient.getApi()
        app = application
    }

    public fun login(username: String, password:String, fragment: LoginFragment, switch: Switch){
        api.loginUser(username, password).enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.code() == 200 && response.body() != null){
                    MainActivity.auth_code = response.body()!!.auth_code;
                    MainActivity.username = username;
                    MainActivity.logoutAvailable = true;

                    val path = fragment.requireContext().getFilesDir()
                    var fileLogin = File(path, "info.txt")

                    if(switch.isChecked){
                        fileLogin.createNewFile()
                        fileLogin.writeText(MainActivity.username!!+"\n"+MainActivity.auth_code!!)
                        println(fileLogin.readText())
                    }
                    else{
                        if(fileLogin.exists()){
                            fileLogin.createNewFile()
                            fileLogin.writeText("")
                        }
                    }
                    Toast.makeText(app.applicationContext,"Успешно бевте најавени.", Toast.LENGTH_LONG).show()
                    notifyLoginFragmentOnSuccess(fragment)
                }
                else{
                    Toast.makeText(app.applicationContext,"Најавата не беше успешна (" + response.code() + ")", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(app.applicationContext,"Контактирајте го администраторот." + t.message + " ", Toast.LENGTH_LONG).show()
                println(t.stackTrace)
                println(t.message)
            }

        })
    }

    public fun notifyLoginFragmentOnSuccess(fragment: LoginFragment){
        fragment.redirect()
    }

    public fun wakeUp(){
        api.loginUser("", "").enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }

        })
    }

}