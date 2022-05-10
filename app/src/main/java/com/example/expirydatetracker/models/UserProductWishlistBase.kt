package com.example.expirydatetracker.models

import com.google.gson.annotations.SerializedName

class UserProductWishlistBase {
    @SerializedName("wishlist")
    var data: MutableList<UserProductWishlist>

    constructor(data: MutableList<UserProductWishlist>) {
        this.data = data
    }

    fun insert_data(data: UserProductWishlist) {
        this.data.add(data)
    }

    fun deleteItem(username: String, product_id:String){
        for(wishlistItem:UserProductWishlist in data){
            if(wishlistItem.user.username.equals(username) and wishlistItem.product.productId.equals(product_id)){
                data.remove(wishlistItem)
                return
            }
        }
    }
}