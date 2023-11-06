package it.unibo.lam.shop.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.unibo.lam.shop.R
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import it.unibo.lam.shop.MainActivity
import it.unibo.lam.shop.data.user.UserRepository
import it.unibo.lam.shop.data.user.model.User
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var dateOfBirthEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inizializzazione delle views
        firstNameEditText = findViewById(R.id.firstName)
        lastNameEditText = findViewById(R.id.lastName)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        dateOfBirthEditText = findViewById(R.id.dateOfBirth)
        loginButton = findViewById(R.id.loginButton)

        // Impostazione del DatePickerDialog
        dateOfBirthEditText.setOnClickListener {
            showDatePickerDialog()
        }

        // Gestione del click sul pulsante di login
        loginButton.setOnClickListener {
            saveLoginDetails()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                // Formattazione della data e aggiornamento del testo dell'EditText
                dateOfBirthEditText.setText(
                    String.format(
                        Locale.getDefault(),
                        "%d-%02d-%02d",
                        year,
                        monthOfYear + 1,
                        dayOfMonth
                    )
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun saveLoginDetails() {
        val user = User(
            firstNameEditText.text.toString(),
            lastNameEditText.text.toString(),
            emailEditText.text.toString(),
            dateOfBirthEditText.text.toString(),
            passwordEditText.text.toString()
        )
        // Salva il nuovo utente creato nelle preferenze
        UserRepository.getInstance(applicationContext).saveUser(user)
        // Ritorna il controllo all'actvity principale
        val mainActivityIntent = Intent(this, MainActivity::class.java);
        startActivity(mainActivityIntent)
        finish()
    }
}