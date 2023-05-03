package cat.copernic.disciplinevents.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.databinding.FragmentEditProfileUserBinding
import cat.copernic.disciplinevents.databinding.FragmentInfoEventBinding
import cat.copernic.disciplinevents.databinding.FragmentProfileUserBinding
import com.bumptech.glide.Glide


class EditProfileUser : Fragment() {

    private lateinit var binding: FragmentEditProfileUserBinding //Binding
    private val args by navArgs<EditProfileUserArgs>()
    private lateinit var getContent: ActivityResultLauncher<String> //Implicit Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            //Save ActivityResultLauncher in getContent
            getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                // Handle the returned Uri
                // Load the image into the ImageView
                Glide.with(this)
                    .load(uri)
                    .into(binding.imgUsuario)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OnClick btn save
        binding.btnGuardar.setOnClickListener{
            //Nav to ProfileUser
            findNavController().navigate(R.id.action_editProfileUser_to_profileUser)
        }

        //OnClick btn cancel
        binding.btnCancelar.setOnClickListener{
            //Nav to ProfileUser
            findNavController().navigate(R.id.action_editProfileUser_to_profileUser)
        }

        //OnClick change photo
        binding.imgUsuario.setOnClickListener {

            // Launch the intent
            getContent.launch("image/*")

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment init binding
        binding = FragmentEditProfileUserBinding.inflate(inflater, container, false)

        //Add values of object in Layout
        binding.nombre.text = args.currentUser.name
        binding.apellido.text = args.currentUser.lastName
        binding.genero.text = args.currentUser.gender

        return binding.root
    }
}