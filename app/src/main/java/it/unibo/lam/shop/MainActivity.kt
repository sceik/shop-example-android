package it.unibo.lam.shop

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

        GlobalScope.launch(Dispatchers.IO) {
            val userLogged = UserRepository.getInstance(applicationContext).isLoggedIn()
            withContext(Dispatchers.Main) {
                if (!userLogged) {
                    startLogin()
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