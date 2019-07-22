package com.rebel.challenge.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "users")
data class User @JvmOverloads constructor(
        @ColumnInfo(name = "name") var name: String = "",
        @ColumnInfo(name = "username") var username: String = "",
        @ColumnInfo(name = "email") var email: String = "",
        @ColumnInfo(name = "address") var address: Address = Address(),
        @ColumnInfo(name = "phone") var phone: String = "",
        @ColumnInfo(name = "website") var website: String = "",
        @ColumnInfo(name = "company") var company: Company = Company(),
        @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false,
        @PrimaryKey
        @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
)

data class Address(

        val street: String = "",
        val suite: String = "",
        val city: String = "",
        val zipcode: String = "",
        val geo: Geo = Geo()

)

data class Geo(
        val lat: String = "",
        val lng: String = ""
)

data class Company(
        val name: String = "",
        val catchPhrase: String = "",
        val bs: String = ""
)
