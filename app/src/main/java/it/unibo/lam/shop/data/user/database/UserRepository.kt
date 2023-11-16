package it.unibo.lam.shop.data.user.database

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import it.unibo.lam.shop.data.AppDatabase


class UserRepository(context: Context) {

    private lateinit var userDao : UserDao

    // Init database and get userDao
    init {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        ).build()
        userDao = db.userDao()
    }

    companion object {
        var userRepository: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            if (userRepository == null) {
                userRepository = UserRepository(context)
            }
            return userRepository as UserRepository
        }
    }

    fun isLoggedIn(): Boolean {
        return getUser() != null;
    }

    suspend fun saveUser(user: User) {
        userDao.insertUser(user)
    }

    fun getUser(): User? {
        val users = userDao.getAllUsers()
        if (users.isNotEmpty()) {
            return users[0]
        }
        return null
    }

    fun deleteUser() {
        val users = userDao.getAllUsers()
        if (users.isNotEmpty()) {
            userDao.deleteUser(users[0])
        }
    }

}