package com.example.expirydatetracker.database

import android.os.Handler
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.expirydatetracker.R
import com.example.expirydatetracker.api.EDTApi
import com.example.expirydatetracker.api.EDTApiClient
import com.example.expirydatetracker.fragments.*
import com.example.expirydatetracker.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class AppDatabase {
    companion object{
        public  var products: ProductBase = ProductBase(mutableListOf<Product>())
        public  var manufacturers: ManufacturerBase  = ManufacturerBase(mutableListOf<Manufacturer>())
        public  var expiries: UserProductExpiryBase = UserProductExpiryBase(mutableListOf<UserProductExpiry>())
        public  var wishlist: UserProductWishlistBase = UserProductWishlistBase(mutableListOf<UserProductWishlist>())
        private val api: EDTApi = EDTApiClient.getApi()


        public fun wakeUpServerAndLoadData(fragment: LoadingFragment){

            api.getProducts().enqueue(object: Callback<ProductBase>{
                override fun onResponse(call: Call<ProductBase>, response: Response<ProductBase>) {
                    if(response.code() == 200 && response.body() != null){
                        println("Products "+response.code())
                        products.data = response.body()!!.data
                    }
                    else
                    {
                        println("Products "+response.code())
                        println(response.body())
                    }
                }
                override fun onFailure(call: Call<ProductBase>, t: Throwable) {
                    println("Products "+t.message)
                    println(t.message)
                }
            })

            api.getManufactuers().enqueue(object: Callback<ManufacturerBase>{
                override fun onResponse(call: Call<ManufacturerBase>, response: Response<ManufacturerBase>) {
                    if(response.code() == 200 && response.body() != null){
                        println("Manufacturers "+response.code())
                        manufacturers.data = response.body()!!.data
                        notifyLoadingFragmentSuccess(fragment);
                    }
                    else
                    {
                        println("Manufacturers "+response.code())
                    }
                }
                override fun onFailure(call: Call<ManufacturerBase>, t: Throwable) {
                    println("Manufacturers "+t.message)
                    if(t.message.equals("timeout")){
                        AppDatabase.wakeUpServerAndLoadData(fragment)
                    }
                }
            })


        }


        public fun refreshData() {
            println("refreshing data...")
            api.getProducts().enqueue(object: Callback<ProductBase>{
                override fun onResponse(call: Call<ProductBase>, response: Response<ProductBase>) {
                    if(response.code() == 200 && response.body() != null){
                        println("Products "+response.code())
                        products.data = response.body()!!.data
                    }
                    else
                    {
                        println("Products "+response.code())
                        println(response.body())
                    }
                }
                override fun onFailure(call: Call<ProductBase>, t: Throwable) {
                    println("Products "+t.message)
                    println(t.message)
                }
            })

            api.getManufactuers().enqueue(object: Callback<ManufacturerBase>{
                override fun onResponse(call: Call<ManufacturerBase>, response: Response<ManufacturerBase>) {
                    if(response.code() == 200 && response.body() != null){
                        println("Manufacturers "+response.code())
                        manufacturers.data = response.body()!!.data
                    }
                    else
                    {
                        println("Manufacturers "+response.code())
                    }
                }
                override fun onFailure(call: Call<ManufacturerBase>, t: Throwable) {
                    println("Manufacturers "+t.message)
                }
            })
        }

        public fun refreshUserData(){
            if(MainActivity.auth_code == null){
                return;
            }
            api.getExpirylist(MainActivity.username!!, MainActivity.auth_code!!).enqueue(object: Callback<UserProductExpiryBase>{
                override fun onResponse(call: Call<UserProductExpiryBase>, response: Response<UserProductExpiryBase>) {
                    if(response.code() == 200 && response.body() != null){
                        println("Expiry "+response.code())
                        expiries.data = response.body()!!.data
                    }
                    else
                    {
                        println("Expiry "+response.code())
                    }
                }
                override fun onFailure(call: Call<UserProductExpiryBase>, t: Throwable) {
                    println("Expiry "+t.message)
                }
            })

            api.getWishlist(MainActivity.username!!, MainActivity.auth_code!!).enqueue(object: Callback<UserProductWishlistBase>{
                override fun onResponse(call: Call<UserProductWishlistBase>, response: Response<UserProductWishlistBase>) {
                    if(response.code() == 200 && response.body() != null){
                        println("Wishlist "+response.code())
                        wishlist.data = response.body()!!.data
                    }
                    else
                    {
                        println("Wishlist "+response.code())
                    }
                }
                override fun onFailure(call: Call<UserProductWishlistBase>, t: Throwable) {
                    println("Wishlist "+t.message)
                }
            })
        }

        public fun addWishlistItem(
            username: String,
            productId: String,
            quantity: Int,
            auth_code: String,
            fragment: AddWishlistFragment
        ){
            api.addWishlistItem(username, productId, quantity, auth_code).enqueue(object: Callback<UserProductWishlist>{
                override fun onResponse(call: Call<UserProductWishlist>, response: Response<UserProductWishlist>) {
                    if(response.code() == 200){
                        println("Wishlist added!  "+response.code())
                        refreshUserData()
                        fragment.notifyOnSuccess()
                    }
                    else
                    {
                        println("Wishlist failed to add. "+response.code())
                    }
                }
                override fun onFailure(call: Call<UserProductWishlist>, t: Throwable) {
                    println("Manufacturers "+t.message)
                }
            })
        }

        public fun addExpiry(
            username: String,
            productId: String,
            expiryDate: String,
            auth_code: String,
            fragment: AddExpiryFragment
        ){
            api.addExpiry(username, productId, expiryDate, auth_code).enqueue(object: Callback<UserProductExpiry>{
                override fun onResponse(call: Call<UserProductExpiry>, response: Response<UserProductExpiry>) {
                    if(response.code() == 200){
                        println("Expiry added!  "+response.code())
                        Toast.makeText(AddExpiryFragment.contextOf, "Expiry item was added.", Toast.LENGTH_SHORT).show()
                        refreshUserData()
                        fragment.notifyOnSuccess()
                    }
                    else
                    {
                        Toast.makeText(AddExpiryFragment.contextOf, "Expiry item failed to be added.", Toast.LENGTH_SHORT).show()
                        println("Expiry failed to add. "+response.code())
                    }
                }
                override fun onFailure(call: Call<UserProductExpiry>, t: Throwable) {
                    Toast.makeText(AddExpiryFragment.contextOf, "Contact the system administrator.", Toast.LENGTH_SHORT).show()
                    println("Expiry "+t.message)
                }
            })
        }

        public fun addProduct(id:String, name: String, id_manufacturer: Int, fragment: AddProductFragment){
            api.addProduct(id, name, id_manufacturer).enqueue(object: Callback<Product>{
                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    if(response.code() == 200){
                        println("Product added!  "+response.code())
                        Toast.makeText(AddProductFragment.contextOf, "Product was added.", Toast.LENGTH_SHORT).show()
                        refreshUserData()
                        notifyAddFragmentOnSuccess(fragment)
                    }
                    else
                    {
                        Toast.makeText(AddProductFragment.contextOf, "Product failed to be added.", Toast.LENGTH_SHORT).show()
                        println("Product failed to add. "+response.code())
                    }
                }
                override fun onFailure(call: Call<Product>, t: Throwable) {
                    Toast.makeText(AddProductFragment.contextOf, "Products - Contact the system administrator.", Toast.LENGTH_SHORT).show()
                    println("Product "+t.message)
                }
            })
        }

        public fun addManufacturer(name: String, fragment:AddManufacturerFragment){
            api.addManufacturer(name).enqueue(object: Callback<Manufacturer>{
                override fun onResponse(call: Call<Manufacturer>, response: Response<Manufacturer>) {
                    if(response.code() == 200){
                        println("Manufacturer added!  "+response.code())
                        Toast.makeText(AddManufacturerFragment.contextOf, "Manufacturer was added.", Toast.LENGTH_SHORT).show()
                        refreshUserData()
                        notifyAddMFragmentOnSuccess(fragment)
                    }
                    else
                    {
                        Toast.makeText(AddManufacturerFragment.contextOf, "Manufacturer failed to be added.", Toast.LENGTH_SHORT).show()
                        println("Manufacturer failed to add. "+response.code())
                    }
                }
                override fun onFailure(call: Call<Manufacturer>, t: Throwable) {
                    Toast.makeText(AddManufacturerFragment.contextOf, "Manufacturer - Contact the system administrator.", Toast.LENGTH_SHORT).show()
                    println("Manufacturer "+t.message)
                }
            })
        }

        public fun deleteExpiry(position: Int, id: Int, auth_code:String, fragment: ExpiryFragment){
            api.deleteExpiry(id, auth_code).enqueue(object: Callback<String>{
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    println(call.request().url().url().toString())
                    if(response.code() == 200){
                        Toast.makeText(fragment.requireContext(), "Бришењето беше успешно.", Toast.LENGTH_SHORT).show()
                        notifyDeleteExpiryFragment(fragment, id, position)
                    }
                    else{
                        Toast.makeText(fragment.requireContext(), "Бришењето беше неуспешно. " + response.code(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(fragment.requireContext(), "Повикот беше неуспешен. " + t.message, Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })
        }

        public fun deleteWishlistItem(position:Int, username: String, product_id:String, auth_code:String, fragment: WishlistFragment) {
            api.deleteWishlist(username, product_id, auth_code).enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    println(call.request().url().url().toString())
                    if (response.code() == 200) {
                        Toast.makeText(
                            fragment.requireContext(),
                            "Бришењето беше успешно.",
                            Toast.LENGTH_SHORT
                        ).show()
                        notifyDeleteWishlistFramgent(fragment, username, product_id, position)
                    } else {
                        Toast.makeText(
                            fragment.requireContext(),
                            "Бришењето беше неуспешно. " + response.code(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        fragment.requireContext(),
                        "Повикот беше неуспешен. " + t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    println(t.message)
                }
            })
        }

        private fun notifyLoadingFragmentSuccess(fragment: LoadingFragment){
            if(!LoadingFragment.loadedDataOnce){
                LoadingFragment.loadedDataOnce = true
                Handler().postDelayed({
                    val path = fragment.requireContext().filesDir
                    val fileLogin = File(path, "info.txt")
                    if(!fileLogin.exists()){
                        fragment.findNavController().navigate(R.id.action_loadingFragment_to_FirstFragment)
                    }
                    else{
                        val text = fileLogin.readText()
                        val lines : List<String> = text.split("\n")
                        if(lines.size == 2){
                            MainActivity.username = lines[0]
                            MainActivity.auth_code = lines[1]
                            MainActivity.logoutAvailable = true
                            fragment.requireActivity().invalidateOptionsMenu()
                            fragment.findNavController().navigate(R.id.action_loadingFragment_to_SecondFragment)
                        }
                        else
                            fragment.findNavController().navigate(R.id.action_loadingFragment_to_FirstFragment)
                    }
                }, 2000)
            }
            else{
                fragment.findNavController().navigate(R.id.action_loadingFragment_to_FirstFragment)
            }
        }

        private fun notifyDeleteExpiryFragment(fragment: ExpiryFragment, id: Int, position: Int){
            AppDatabase.expiries.deleteItem(id)
            fragment.invalidateAdapter(position)
        }

        private fun notifyDeleteWishlistFramgent(fragment: WishlistFragment, username: String, product_id:String, position: Int){
            AppDatabase.wishlist.deleteItem(username, product_id)
            fragment.invalidateAdapter(position)
        }

        private fun notifyAddFragmentOnSuccess(fragment: AddProductFragment){
            fragment.findNavController().navigateUp()
            fragment.findNavController().navigateUp()
        }

        private fun notifyAddMFragmentOnSuccess(fragment: AddManufacturerFragment){
            fragment.findNavController().navigateUp()
        }
    }
}