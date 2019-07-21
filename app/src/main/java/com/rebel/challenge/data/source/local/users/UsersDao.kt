package com.rebel.challenge.data.source.local.users

import androidx.room.*
import com.rebel.challenge.data.model.User

/**
 * Data Access Object for the users table.
 */
@Dao
interface UsersDao {

    /**
     * Select all users from the users table.
     *
     * @return all users.
     */
    @Query("SELECT * FROM Users")
    fun getUsers(): List<User>

    /**
     * Select a user by id.
     *
     * @param id the user id.
     * @return the user with id.
     */
    @Query("SELECT * FROM Users WHERE id = :id")
    fun getUserById(id: String): User?

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    /**
     * Update a user.
     *
     * @param user user to be updated
     * @return the number of users updated. This should always be 1.
     */
    @Update
    fun updateUser(user: User): Int

    /**
     * Update the like Counter of a user
     *
     * @param userId        id of the user
     * @param isFavorite    status to be updated
     */
    @Query("UPDATE users SET isFavorite = :isFavorite WHERE id = :id")
    fun favoriteUser(id: String, isFavorite: Boolean)

    /**
     * Delete a user by id.
     *
     * @return the number of users deleted. This should always be 1.
     */
    @Query("DELETE FROM Users WHERE id = :id")
    fun deleteUserById(id: String): Int

    /**
     * Delete all users.
     */
    @Query("DELETE FROM Users")
    fun deleteUsers()

}