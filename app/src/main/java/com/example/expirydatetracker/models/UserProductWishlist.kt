package com.example.expirydatetracker.models

data class UserProductWishlist (
    var product: Product,
    var user: User,
    var quantity: Int = 0
)