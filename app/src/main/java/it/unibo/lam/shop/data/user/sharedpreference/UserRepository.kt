package it.unibo.lam.shop.data.user.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64


class UserRepository(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserLocalStorage", Context.MODE_PRIVATE)

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

    fun saveUser(user: User) {
        val editor = sharedPreferences.edit()
        editor.putString("user_name", user.name)
        editor.putString("user_surname", user.surname)
        editor.putString("user_email", user.email)
        editor.putString("user_password", encrypt(user.password))
        editor.apply()
    }

    fun getUser(): User? {
        val name = sharedPreferences.getString("user_name", null) ?: return null
        val surname = sharedPreferences.getString("user_surname", null) ?: return null
        val email = sharedPreferences.getString("user_email", null) ?: return null
        val password = decrypt(sharedPreferences.getString("user_password", null) ?: return null)
        return User(name, surname, email, email, password)
    }

    fun deleteUser() {
        val editor = sharedPreferences.edit()
        editor.remove("user_name")
        editor.remove("user_surname")
        editor.remove("user_email")
        editor.remove("user_password")
        editor.apply()
    }

    private fun encrypt(input: String): String {
        // Dummy encryption, replace with real encryption
        return Base64.encodeToString(input.toByteArray(), Base64.DEFAULT)
    }

    private fun decrypt(input: String): String {
        // Dummy decryption, replace with real decryption
        return String(Base64.decode(input, Base64.DEFAULT))
    }

}