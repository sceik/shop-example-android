package it.unibo.lam.shop

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import it.unibo.lam.shop.data.user.database.User
import it.unibo.lam.shop.data.user.database.UserRepository
import it.unibo.lam.shop.ui.login.LoginActivity
import it.unibo.lam.shop.ui.productList.ProductListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private fun startLogin() {
        val loginActivityIntent = Intent(this, LoginActivity::class.java);
        startActivity(loginActivityIntent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userRepository = UserRepository.getInstance(applicationContext)
        createNotificationChannel()

        GlobalScope.launch(Dispatchers.IO) {
            val user = userRepository.getUser()
            withContext(Dispatchers.Main) {
                if (user === null) {
                    startLogin()
                } else {
                    showNotification(user)
                }
            }
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Mostra il pulsante Back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) { // Importante per evitare di sovrapporre i fragment durante i riavvii dell'attività
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ProductListFragment.newInstance())
                .commitNow()
        }
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(user: User) {
        val builder = NotificationCompat.Builder(this, "shop-app")
            .setSmallIcon(R.drawable.icon_not)
            .setContentTitle("User logged!")
            .setContentText("User ${user.email} - ${user.name} ${user.surname}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define.
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.i("main", "showNotification: Not permitted")
                return
            }
            notify(1000, builder.build())
        }

    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Login-show"
            val descriptionText = "Notifcation login status"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("shop-app", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Gestisci il click sul pulsante Back
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}