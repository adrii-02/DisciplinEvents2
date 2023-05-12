package cat.copernic.disciplinevents.activitys

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import cat.copernic.disciplinevents.DAO.UserDAO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var bd: FirebaseFirestore
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //INIT LAYOUT
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Init userDAO
        userDAO = UserDAO()

        //GET INSTANCE
        auth = userDAO.getCurrentUser()
        bd = userDAO.getCurrentDB()

        //START -> LATERAL MENU & TOOLBAR
        //Set ActionBar into Toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        //Change Title
        //val actionBar = supportActionBar
        //actionBar?.setTitle("DisciplinEvents")

        //Save DrawerLayout in drawer
        drawer = binding.drawerLayout

        //Config of toggle & drawer
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
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

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        //val navController = findNavController(R.id.navHostFragment)
        //navController.setGraph(R.navigation.admin_nav_graph)

        return super.onCreateView(name, context, attrs)


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