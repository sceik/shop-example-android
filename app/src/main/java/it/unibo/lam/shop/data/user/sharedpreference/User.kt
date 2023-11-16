package it.unibo.lam.shop.data.user.sharedpreference

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    var name: String,
    var surname: String,
    var email: String,
    var birthDate: String,
    var password: String // Questa password sarà cifrata prima di essere salvata
)
