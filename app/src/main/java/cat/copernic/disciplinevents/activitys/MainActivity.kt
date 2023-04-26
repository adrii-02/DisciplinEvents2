package cat.copernic.disciplinevents.activitys

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //INIT LAYOUT
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //GET INSTANCE
        auth = Firebase.auth

        //START -> LATERAL MENU & TOOLBAR
        //Set ActionBar into Toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        //Save DrawerLayout in drawer
        drawer = binding.drawerLayout

        //Config of toggle & drawer
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        //Save navView (lateral menu) in navigationView
        val navigationView: NavigationView = binding.navView
        //Select this like ItemSelectListener
        navigationView.setNavigationItemSelectedListener(this)
        //FINISH -> LATERAL MENU & TOOLBAR
    }

    //CALL METHOD WHEN SELECTED MENU ITEM
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        val navController = navHostFragment?.findNavController()

        when (item.itemId) {
            R.id.REventos -> {
                //Nav to REvents
                navController?.navigate(R.id.REventos)
            }
            R.id.profileUser -> {
                //Nav to profileUser
                navController?.navigate(R.id.profileUser)
            }
            R.id.cerrarSession -> {
                // Fun logout
                logOut(auth)
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    //CALL METHOD AFTER onCreate
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    //CALL METHOD IF BE A CHANGE IN DEVICE CONFIGURATION
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    //CALL METHOD WHEN MENU ITEM IS SELECT
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //If menu item selected is the burger
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //FUN LOGOUT
    private fun logOut(auth: FirebaseAuth){
        //LogOut
        auth.signOut()

        //Nav to Login
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}