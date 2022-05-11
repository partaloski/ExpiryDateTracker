package com.example.expirydatetracker.fragments

import android.content.Intent
import android.os.Bundle
import com.example.expirydatetracker.database.AppDatabase
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.example.expirydatetracker.R
import com.example.expirydatetracker.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var starterIntent : Intent

    companion object{
        public var username: String? = null
        public var auth_code: String? = null
        public var registerResponseSuccessful: Boolean = false
        public var registerResponseCompleted: Boolean = false
        public var logoutAvailable: Boolean = false;
        private lateinit var menu: Menu
    }

    fun getSupportFragmentManagerExternal():FragmentManager{
        return getSupportFragmentManagerExternal()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        starterIntent = intent;

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        invalidateOptionsMenu()

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        if(logoutAvailable){
            menu.getItem(0).setEnabled(true)
        }
        else {
            menu.getItem(0).setEnabled(false)
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                if(auth_code != null && username != null){
                    auth_code = null
                    username = null
                    val path = filesDir
                    var fileLogin = File(path, "info.txt")
                    fileLogin.createNewFile()
                    fileLogin.writeText("")
                    finish()
                    startActivity(starterIntent)
                    invalidateOptionsMenu()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}