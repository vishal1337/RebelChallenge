package com.rebel.challenge.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//Todo Store Favorite User Locally and show map upon Click

@Entity(tableName = "users")
data class User @JvmOverloads constructor(
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "username") var username: String = "",
    @ColumnInfo(name = "email") var email: String = "",
    @ColumnInfo(name = "phone") var phone: String = "",
    @ColumnInfo(name = "website") var website: String = "",
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false,
    @PrimaryKey
    @ColumnInfo(name = "userId")
    var userId: String = UUID.randomUUID().toString()
) /*{
    constructor(name: String? = null,
                username: String? = null,
                email: String? = null,
                phone: String? = null,
                website: String? = null,
                userId: String? = null
    ) : this(
            name ?: "",
            username ?: "",
            email ?: "",
            phone ?: "",
            website ?: "",
            userId ?: UUID.randomUUID().toString()
    )
}*/

/*
data class Address(

        val street: String?,
        val suite: String?,
        val city: String?,
        val zipCode: String?,
        val geo: Geo?

)

data class Geo(
        val lat: Long?,
        val long: Long?
)

data class Company(
        val name: String?,
        val catchPhrase: String?,
        val bs: String?
)*/
