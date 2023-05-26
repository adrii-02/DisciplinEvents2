package cat.copernic.disciplinevents2.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import cat.copernic.disciplinevents2.DAO.UserDAO
import cat.copernic.disciplinevents2.Utils.Utils
import cat.copernic.disciplinevents2.databinding.ActivityRegisterBinding
import cat.copernic.disciplinevents2.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var bd: FirebaseFirestore
    private lateinit var userDAO : UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //HIDE APP BAR
        if (supportActionBar != null)
            supportActionBar!!.hide()

        //INIT LAYOUT
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //INIT userDAO
        userDAO = UserDAO()

        //GET INSTANCE
        auth = Utils.getCurrentUser()
        bd = Utils.getCurrentDB()

        //LOGIN
        binding.txtTienesCuenta.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        //REGISTER
        binding.btnRegister.setOnClickListener{
            var email = binding.inputEmail2.text.toString().lowercase()
            var password = binding.inputPassword2.text.toString()
            var repPassword = binding.inputRepPassword2.text.toString()

            //Create variable builder for AlertDialog
            val builder = AlertDialog.Builder(this)

            //Filters register
            if(password.equals(repPassword) && emptyCamp(email,password,repPassword)){
                register(email,password)

            } else if(email.isBlank() && password.isNotEmpty()){
                builder.setTitle("Error")
                builder.setMessage("El correo no puede estar vacío")
                builder.setPositiveButton("Aceptar", null)
                val dialog = builder.create()
                dialog.show()

            } else if(!password.equals(repPassword)){
                builder.setTitle("Error")
                builder.setMessage("La contraseña no coincide")
                builder.setPositiveButton("Aceptar", null)
                val dialog = builder.create()
                dialog.show()

            } else if(password.isBlank() && email.isNotEmpty()){
                builder.setTitle("Error")
                builder.setMessage("La contraseña no puede estar vacía")
                builder.setPositiveButton("Aceptar", null)
                val dialog = builder.create()
                dialog.show()

            } else if(email.isBlank() && password.isBlank()){
                builder.setTitle("Error")
                builder.setMessage("Los campos no pueden estar vacíos")
                builder.setPositiveButton("Aceptar", null)
                val dialog = builder.create()
                dialog.show()

            }
        }
    }

    //FUN EMPTY CAMPS
    private fun emptyCamp(email:String, password:String, repPassword:String):Boolean{
        return email.isNotEmpty()&&password.isNotEmpty()&&repPassword.isNotEmpty()
    }

    //FUN REGISTER
    private fun register(email: String, password: String){

        //Create variable builder for AlertDialog
        val builder = AlertDialog.Builder(this)

        //Create new user with email & password
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
            if(task.isSuccessful){
                var userObj = readData()

                //Call fun insertUser
                setUser(bd, userObj)

                //Navigation to main activity
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            } else {
                builder.setTitle("Error")
                builder.setMessage("El registro ha fallado")
                builder.setPositiveButton("Aceptar", null)
                val dialog = builder.create()
                dialog.show()

            }
        }
    }

    //FUN READ DATA
    private fun readData():User{

        //Save values
        var name = binding.inputNombre2.text.toString()
        var lastName = binding.inputApellido2.text.toString()
        var email = binding.inputEmail2.text.toString()
        var gender = binding.inputGender2.text.toString()

        //Return object with values
        return User(name, lastName, email, gender, false,null)
    }

    private fun setUser(bd: FirebaseFirestore, userObj: User) {

        //Users
        bd.collection("usuarios").document(userObj.email.toString()).set(
            hashMapOf(
                "email" to userObj.email,
                "nombre" to userObj.name,
                "apellidos" to userObj.lastName,
                "genero" to userObj.gender,
                "admin" to userObj.admin
            )
        ).addOnSuccessListener {
            Log.d("TAG", "Usuario registrado correctamente")
        }.addOnFailureListener { e ->
            Log.e("TAG", "Error al registrar el usuario", e)
        }

    }
}