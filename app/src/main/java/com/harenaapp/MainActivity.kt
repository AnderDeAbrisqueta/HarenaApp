package com.harenaapp

/**
 * Ander De Abrisqueta
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2021|22
 */

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harenaapp.adapters.AdultoMayorAdapter
import com.harenaapp.database.AdultosMayoresDataBase
import com.harenaapp.databinding.ActivityMainBinding
import com.harenaapp.entities.AdultoMayor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var bd: AdultosMayoresDataBase

    private lateinit var adaptador: AdultoMayorAdapter
    private lateinit var datosAdultosMayores: MutableList<AdultoMayor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Vinculación de vistas
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instancia del servicio de autenticación
        auth = Firebase.auth

        //Instancia del servicio de base de datos
        bd = AdultosMayoresDataBase.getAdultosMayoresDatabase(this)!!

        //Recuperación de la información de la base de datos
        datosAdultosMayores = bd.getAdultosMayoresDao().getOldPeople()

        //Direccionamiento a la vista de la persona mayor
        val gestionarPulsacionCorta: (AdultoMayor) -> Unit = {
            // Snackbar.make(binding.root, "Has pulsado sobre ${it.last_name}", Snackbar.LENGTH_LONG).show()
            val intent = Intent(this, ViewActivity::class.java)
            intent.putExtra("_id", it.idAdulto)
            startActivity(intent)
        }

        //Menú contextual
        val gestionarPulsacionLarga: (MenuItem, AdultoMayor) -> Boolean = { item, adultoMayor: AdultoMayor ->
            when(item.itemId) {
                R.id.adultoMayorEditar ->  {
                    /*Snackbar.make(binding.root, "Has pulsado sobre editar sobre ${adultoMayor.last_name}", Snackbar.LENGTH_LONG).show()
                    true*/
                    val intent = Intent(this, EditActivity::class.java)
                    intent.putExtra("_adultoMayor", adultoMayor)
                    startActivity(intent)
                    true
                }
                R.id.adultoMayorBorrar -> {
                    /*Snackbar.make(binding.root, "Has pulsado sobre borrar sobre ${adultoMayor.last_name}", Snackbar.LENGTH_LONG).show()
                    true*/
                    //Se borra el adulto mayor de la base de datos
                    bd.getAdultosMayoresDao().deleteOldPeople(adultoMayor)

                    //Busca el índice del adulto mayor
                    val idx = datosAdultosMayores.indexOfFirst { it.equals(adultoMayor) }

                    //Elimina el adulto mayor de la colección de datos
                    datosAdultosMayores.removeAt(idx)

                    //Notifica el cambio al adaptador
                    adaptador.notifyItemRemoved(idx)
                }
            }
            false
        }

        // creamos el adaptador
        adaptador = AdultoMayorAdapter(datosAdultosMayores).apply {
            pulsacionCorta = gestionarPulsacionCorta
            pulsacionLarga = gestionarPulsacionLarga

        }

        // asociamos el adaptador y configuramos el recyclerView
        binding.recyclerView.apply {
            adapter = adaptador
            setHasFixedSize(true)
        }

        //Pulsación del botón de crear nuevo adulto mayor
        createButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

    }

    //Bloquea la vuelta a la actividad anterior
    override fun onBackPressed() { }

    //Creación del menú principal
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Gestión de la pulsación del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when(item.itemId)
        {

            R.id.mnuSalir -> {
                auth.signOut()
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }



}