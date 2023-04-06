package cat.copernic.disciplinevents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cat.copernic.disciplinevents.databinding.ActivityLoginBinding
import cat.copernic.disciplinevents.databinding.ActivityMainBinding
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null)
            supportActionBar!!.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}