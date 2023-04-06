package cat.copernic.disciplinevents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cat.copernic.disciplinevents.databinding.ActivityLoginBinding
import cat.copernic.disciplinevents.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //INICIAR LAYOUT CON BINDING
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //CLICK TIENES CUENTA EXISTENTE
        binding.txtTienesCuenta.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}