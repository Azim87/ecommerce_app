package kg.azimus.util

import kg.azimus.model.UserModel

class Prevalent {
    companion object {
        val currentUserOnline = UserModel()
        const val UserPasswordKey = "UserPasswordKey"
        const val UserPhoneKey = "UserPhoneNumberKey"
    }
}