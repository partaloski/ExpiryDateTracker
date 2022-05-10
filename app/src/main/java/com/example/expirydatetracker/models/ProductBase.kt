package com.example.expirydatetracker.models

import com.google.gson.annotations.SerializedName

class ProductBase {
    @SerializedName("products")
    var data: MutableList<Product>

    constructor(data: MutableList<Product>){
        this.data=data
    }

    fun insert_data(data: Product){
        this.data.add(data)
    }

    fun contains(barcode: String):Product?{
        for(p in data){
            if(p.productId.equals(barcode)){
                return p
            }
        }
        return null
    }
}