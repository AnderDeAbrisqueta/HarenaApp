package com.harenaapp.adapters

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.harenaapp.entities.AdultoMayor
import com.harenaapp.R
import com.harenaapp.databinding.ItemAdultoMayorBinding
import com.squareup.picasso.Picasso

class AdultoMayorAdapter(var datosAdultosMayores:MutableList<AdultoMayor>): RecyclerView.Adapter<AdultoMayorAdapter.AdultoMayorHolder>() {

    // Definimos el escuchador para la pulsación simple
    var pulsacionCorta: (AdultoMayor) -> Unit = {}
        set(value)
        {
            field = value
        }

    // Definimos el escuchador para el menú contextual
    var pulsacionLarga: (MenuItem, AdultoMayor) -> Boolean = { menuItem: MenuItem,
                                                         adultoMayor: AdultoMayor -> false }
        set(value)
        {
            field = value
        }

    override fun getItemCount(): Int = datosAdultosMayores.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdultoMayorHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemAdultoMayorBinding.inflate(layoutInflater, parent, false)

        return AdultoMayorHolder(binding)

    }

    override fun onBindViewHolder(holder: AdultoMayorHolder, position: Int) {
        holder.render(datosAdultosMayores[position])
    }


    inner class AdultoMayorHolder(val binding: ItemAdultoMayorBinding):RecyclerView.ViewHolder(binding.root) {

        fun render (adultoMayor: AdultoMayor) {
            Picasso.get().load(adultoMayor.avatar).into(binding.avatarAdulto)
            binding.lastNameAdulto.text = adultoMayor.last_name
            binding.streetAddressAdulto.text = adultoMayor.street_address
            binding.phoneAdulto.text = adultoMayor.phone

            binding.root.setOnClickListener{ pulsacionCorta(adultoMayor) }
            binding.root.setOnLongClickListener{
                val pop = PopupMenu(binding.root.context, binding.lastNameAdulto)
                    pop.inflate(R.menu.adulto_mayor_context_menu)
                    pop.setOnMenuItemClickListener { pulsacionLarga(it, adultoMayor) }
                    pop.show()
                true
            }
        }
    }
}