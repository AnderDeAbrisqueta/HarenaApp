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

        /*val insertarAdultosMayores = bd.getAdultosMayoresDao().insertOldPeople(
            AdultoMayor(0,"https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.zl2oiPY9DtBnQCo0TvivkQAAAA%26pid%3DApi&f=1", "Phyllis", "Lefever", "Female","833-715-5551","733 Meadow Ridge Point", "Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus."),
            AdultoMayor(0,"https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.Wg3ZP_MkCzitlTs8Wu4VfgHaEK%26pid%3DApi&f=1","Cory","Sussans","Female","219-521-6295","8075 Hollow Ridge Place","Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum.\n\nProin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem."),
            AdultoMayor(0,"https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.fxGLHW6wvM4Z5rlRlE5hTQHaDt%26pid%3DApi&f=1","Max","Adamowicz","Genderfluid","626-565-9025","2811 Northport Plaza","In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.\n\nSuspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst."),
            AdultoMayor(0,"https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.yqWDCn7X1czh3tAV4CWNGAHaE7%26pid%3DApi&f=1","Allx","Zolini","Genderqueer","585-314-8744","4 Tony Crossing","Phasellus in felis. Donec semper sapien a libero. Nam dui.\n\nProin leo odio, porttitor id, consequat in, consequat ut, nulla. Sed accumsan felis. Ut at dolor quis odio consequat varius.\n\nInteger ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi."),
            AdultoMayor(0,"https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.Qq4iv1krmb68Wc7K6HYdBgHaE8%26pid%3DApi&f=1","Megan","Purchon","Genderqueer","644-739-3194","738 Havey Court","Phasellus in felis. Donec semper sapien a libero. Nam dui."),
            AdultoMayor(0,"https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.nDSJtFiTclE2Pd0yfci-3QHaE7%26pid%3DApi&f=1","Sabra","Chitham","Polygender","596-896-6557","5 Lakewood Gardens Hill","Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.\n\nDuis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus."),
            AdultoMayor(0,"https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.USzI1bShXrF1LAYHSwtrIwHaFh%26pid%3DApi&f=1","Patrizio","O'Criane","Polygender","295-890-3175","183 Alpine Place","In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus."),
            AdultoMayor(0,"https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.o2TsRAFTZJSJMhzOXW_2ZgHaD2%26pid%3DApi&f=1","Linn","Patrone","Non-binary","885-927-5558","1 Northland Trail","Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.\n\nCras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.\n\nProin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl."),
            AdultoMayor(0,"https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.qGSJ8U3LJ9u7IPR1aTyodAHaE8%26pid%3DApi&f=1","Coop","Trebilcock","Male","837-810-8430","9 Monterey Street","Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque.\n\nQuisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus.\n\nPhasellus in felis. Donec semper sapien a libero. Nam dui."),
            AdultoMayor(0, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.Eu7WeUvTyK1q5twnXaO9uAHaE7%26pid%3DApi&f=1","Jolie","Averill","Non-binary","191-266-1232","6918 Vernon Center","In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.\n\nNulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi.")
        )*/

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

        /*val adaptador = AdultoMayorAdapter(datosAdultosMayores, gestionarPulsacionCorta, gestionarPulsacionLarga)


        binding.recyclerView.adapter = adaptador
        binding.recyclerView.setHasFixedSize(true)*/

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