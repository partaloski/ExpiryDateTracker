package com.example.expirydatetracker.models

import com.google.gson.annotations.SerializedName

class ManufacturerBase {
    @SerializedName("manufacturers")
    var data: MutableList<Manufacturer>

    constructor(data: MutableList<Manufacturer>){
        this.data=data
    }

    fun insert_data(data: Manufacturer){
        this.data.add(data)
    }
}