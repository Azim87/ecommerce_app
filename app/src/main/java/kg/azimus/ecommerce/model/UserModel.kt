package kg.azimus.ecommerce.model

data class UserModel(
    val userName: String?,
    val phoneNumber: String?,
    val password: String?
) {
    constructor() : this(userName = "", phoneNumber = "", password = "")
}