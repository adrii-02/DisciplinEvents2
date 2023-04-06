package cat.copernic.disciplinevents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import cat.copernic.disciplinevents.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //INICIAR LAYOUT CON BINDING
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //CLICK EN REGÍSTRATE
        binding.txtNoTienesCuenta.setOnClickListener{
            startActivity(Intent(this, Register::class.java))
            finish()
        }
/*
        val reg = findViewById<TextView>(R.id.txtTienesCuenta)
        reg.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
*/
        //CLICK EN OLVIDAR CONTRASEÑA
        binding.txtRecuperar.setOnClickListener {
            //Log.d("ForgetPassword", "Ir a Restablecer Contraseña")
            val intent = Intent(this, recoverPassword::class.java)
            startActivity((intent))
            finish()
        }
    }
}