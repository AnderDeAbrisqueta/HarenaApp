package com.harenaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harenaapp.database.AdultosMayoresDataBase
import com.harenaapp.databinding.ActivityViewBinding
import com.squareup.picasso.Picasso

class ViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: Int = intent.extras?.getInt("_id") as Int

        val adultoMayor = AdultosMayoresDataBase.getAdultosMayoresDatabase(this)?.getAdultosMayoresDao()?.getOldPeopleById(id)

        adultoMayor?.let {
            binding.apply {
                Picasso.get().load(adultoMayor.avatar).into(binding.avatarView)
                nameView.text = adultoMayor.first_name
                lastnameView.text = adultoMayor.last_name
                genderView.text = adultoMayor.gender
                phoneView.text = adultoMayor.phone
                addressView.text = adultoMayor.street_address
                descriptionView.text = adultoMayor.description
                volverButton.setOnClickListener { finish() }
            }
        }
    }
}