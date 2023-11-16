package it.unibo.lam.shop.data.user.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserById(email: String): User

    @Insert
    fun insertUser(users: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}