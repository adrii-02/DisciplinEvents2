package cat.copernic.disciplinevents.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cat.copernic.disciplinevents.databinding.ActivityRecoverPasswordBinding

class recoverPassword : AppCompatActivity() {

    private lateinit var binding: ActivityRecoverPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //HIDE APP BAR
        if (supportActionBar != null)
            supportActionBar!!.hide()

        //INICIAR LAYOUT CON BINDING
        binding = ActivityRecoverPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //CLICK CANCELAR RESETEAR CONTRASEÑA
        binding.btnCancelar.setOnClickListener{
            Log.d("GoLogin", "Ir a Login")
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

    }
}