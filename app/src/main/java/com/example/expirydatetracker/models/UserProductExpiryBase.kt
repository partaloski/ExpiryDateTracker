package com.example.expirydatetracker.models

import com.google.gson.annotations.SerializedName

class UserProductExpiryBase {
    @SerializedName("expiries")
    var data: MutableList<UserProductExpiry>

    constructor(data: MutableList<UserProductExpiry>) {
        this.data = data
    }

    fun insert_data(data: UserProductExpiry) {
        this.data.add(data)
    }

    fun deleteItem(id: Int) {
        for(expiry:UserProductExpiry in data){
            if(expiry.id == id){
                data.remove(expiry)
                return
            }
        }
    }
}