package com.example.expirydatetracker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.expirydatetracker.api.EDTApi
import com.example.expirydatetracker.api.EDTApiClient

class HomeFragmentViewmodel(application: Application): AndroidViewModel(application) {
    private var api: EDTApi
    private var app: Application

    init{
        api = EDTApiClient.getApi()
        app = application
    }

    public fun load_data(){

    }
}