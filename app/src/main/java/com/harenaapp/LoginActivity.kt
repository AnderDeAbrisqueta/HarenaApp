package com.harenaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harenaapp.database.AdultosMayoresDataBase
import com.harenaapp.databinding.ActivityLoginBinding
import com.harenaapp.entities.AdultoMayor

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private val respuestaRegistro = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        /**
         * La lambda contiene la lógica que se ejecutará cuando la actividad REGISTRO responda.
         */
        if (it.resultCode == RESULT_OK)
            it.data?.extras?.getString("_response")?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }

        else
            Snackbar.make(binding.root, R.string.err_registro, Snackbar.LENGTH_LONG)
                .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        inicializarBaseDatos()

        binding.loginButton.setOnClickListener { login(it) }

        binding.registerBtt.setOnClickListener {
            register(it)
        }
    }

    private fun login(view: View) {
        val email: String = binding.emailInput.text.toString().trim()
        val pass: String = binding.passwordInput.text.toString().trim()

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Snackbar.make(view, R.string.email_pass_correct, Snackbar.LENGTH_LONG).show()
                val intencion = Intent(this, MainActivity::class.java)
                startActivity(intencion)
            } else {
                Snackbar.make(view, R.string.error_email_pass, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun register(view: View) {
        val intencion = Intent(this, RegisterActivity::class.java)
        startActivity(intencion)
    }

    /**
     * Inicializamos la base de datos comprobando si hay registros en las tablas. En caso de
     * que no los haya, insertamos información sobre usuarios y series.
     */
    private fun inicializarBaseDatos()
    {
        val dao = AdultosMayoresDataBase.getAdultosMayoresDatabase(this)?.getAdultosMayoresDao()


        if(dao?.countOldPeoples() == 0)
        {
            dao.insertOldPeople(
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
            )
        }
    }
}