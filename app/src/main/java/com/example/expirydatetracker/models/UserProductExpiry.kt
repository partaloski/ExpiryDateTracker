package com.example.expirydatetracker.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class UserProductExpiry(
    var id: Int,
    var product: Product,
    var user: User,
    var expiryDate: String
)