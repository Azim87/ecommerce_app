package kg.azimus.ecommerce.util

import kg.azimus.ecommerce.model.UserModel

class Prevalent {
    companion object {
        public var currentUserOnline = UserModel()
        const val UserPasswordKey = "UserPasswordKey"
        const val UserPhoneKey = "UserPhoneNumberKey"
    }
}