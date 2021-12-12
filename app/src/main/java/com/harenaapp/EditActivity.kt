package com.harenaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harenaapp.adapters.AdultoMayorAdapter
import com.harenaapp.database.AdultosMayoresDataBase
import com.harenaapp.databinding.ActivityEditBinding
import com.harenaapp.databinding.ActivityMainBinding
import com.harenaapp.entities.AdultoMayor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var bd: AdultosMayoresDataBase
    private lateinit var adaptador: AdultoMayorAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var idAdultoMayor: Int? = null

        if(intent.hasExtra("_adultoMayor")) {
            val adultoMayor = intent.extras?.getSerializable("_adultoMayor") as AdultoMayor

            editTextAvatar.setText (adultoMayor.avatar)
            editTextNombre.setText (adultoMayor.first_name)
            editTextApellido.setText (adultoMayor.last_name)
            editTextGenero.setText (adultoMayor.gender)
            editTextTelefono.setText (adultoMayor.phone)
            editTextDireccion.setText (adultoMayor.street_address)
            editTextDescripcion.setText (adultoMayor.description)
            idAdultoMayor = adultoMayor.idAdulto
        }

        bd = AdultosMayoresDataBase.getAdultosMayoresDatabase(this)!!

        saveButton.setOnClickListener{
            val avatar = editTextAvatar.text.toString()
            val nombre = editTextNombre.text.toString()
            val apellido = editTextApellido.text.toString()
            val genero = editTextGenero.text.toString()
            val telefono = editTextTelefono.text.toString()
            val direccion = editTextDireccion.text.toString()
            val descripcion = editTextDescripcion.text.toString()


            val adultoMayorEdit = AdultoMayor(0, avatar, nombre, apellido, genero, telefono, direccion, descripcion)

            if(idAdultoMayor != null) {
                adultoMayorEdit.idAdulto = idAdultoMayor
                bd.getAdultosMayoresDao().updateOldPeople(adultoMayorEdit)
                finish()

            } else {
                bd.getAdultosMayoresDao().insertOldPeople(adultoMayorEdit)

                finish()
            }



        }


    }
}