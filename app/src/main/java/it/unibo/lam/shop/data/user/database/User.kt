package it.unibo.lam.shop.data.user.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey var email: String,
    var name: String,
    var surname: String,
    var birthDate: String,
    var password: String
)