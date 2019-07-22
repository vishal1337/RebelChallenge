package com.rebel.challenge.data.source.local.users

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.rebel.challenge.data.model.Address
import com.rebel.challenge.data.model.Company

class Converters {

    @TypeConverter
    fun fromAddress(value: Address): String = Gson().toJson(value)

    @TypeConverter
    fun toAddress(value: String): Address = Gson().fromJson(value, Address::class.java)

    @TypeConverter
    fun fromCompany(value: Company): String = Gson().toJson(value)

    @TypeConverter
    fun toCompany(value: String): Company = Gson().fromJson(value, Company::class.java)

}