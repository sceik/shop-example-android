package it.unibo.lam.shop.data

import androidx.room.Database
import androidx.room.RoomDatabase
import it.unibo.lam.shop.data.user.database.User
import it.unibo.lam.shop.data.user.database.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}