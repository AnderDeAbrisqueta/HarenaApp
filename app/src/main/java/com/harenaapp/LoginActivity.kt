package com.harenaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harenaapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

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
}