package com.rebel.challenge.data.source.local.users

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rebel.challenge.data.model.User


/**
 * The Room Database that contains the User table.
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UsersDao

    companion object {

        private var INSTANCE: UsersDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): UsersDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java, "Users.db"
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

}