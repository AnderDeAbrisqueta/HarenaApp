package com.harenaapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class AdultoMayor(
    @PrimaryKey(autoGenerate = true) var idAdulto:Int,
    @ColumnInfo (name = "avatar") val avatar:String,
    @ColumnInfo (name = "first_name") val first_name: String,
    @ColumnInfo (name = "last_name") val last_name: String,
    @ColumnInfo (name = "gender") val gender: String,
    @ColumnInfo (name = "phone") val phone: String,
    @ColumnInfo (name = "street_address") val street_address:String,
    @ColumnInfo (name = "description") val description:String,
): Serializable
