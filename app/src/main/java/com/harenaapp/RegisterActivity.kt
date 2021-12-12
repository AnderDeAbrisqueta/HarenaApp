package com.harenaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harenaapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.registerButton.setOnClickListener {
            register(it)

        }


    }

    private fun register(view: View) {
        val email: String = binding.emailInputRegister.text.toString().trim()
        val pass: String = binding.passwordInputRegister.text.toString().trim()

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Snackbar.make(view, R.string.email_pass_correct, Snackbar.LENGTH_LONG).show()
                val intencion = Intent(this, LoginActivity::class.java)
                startActivity(intencion)
            } else {
                Snackbar.make(view, R.string.error_email_pass_register, Snackbar.LENGTH_LONG).show()
            }
        }

    }
}