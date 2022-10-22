package br.com.pucpr.pucprmunicipio.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Municipio(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val codigo: Int
)
