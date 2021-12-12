package com.harenaapp.database

import androidx.room.*
import com.harenaapp.entities.AdultoMayor

@Dao
interface AdultosMayoresDao {

    /**
     * Adultos Mayores
     */
    @Query("SELECT COUNT(*) FROM adultoMayor ;")
    fun countOldPeoples(): Int

    @Query("SELECT * FROM adultoMayor ;")
    fun getOldPeople(): MutableList<AdultoMayor>

    @Query("SELECT * FROM adultoMayor WHERE idAdulto = :id")
    fun getOldPeopleById(id: Int): AdultoMayor

    @Insert
    fun insertOldPeople(vararg adultoMayor: AdultoMayor)

    @Update
    fun updateOldPeople(vararg adultoMayor: AdultoMayor)

    @Delete
    fun deleteOldPeople(vararg adultoMayor: AdultoMayor)
}