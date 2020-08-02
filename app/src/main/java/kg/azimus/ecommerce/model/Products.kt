package kg.azimus.ecommerce.model

data class Products(
    val category: String? = null,
    val date: String? = null,
    val description: String? = null,
    val image: String,
    val name: String? = null,
    val pid: String? = null,
    val price: String? = null
) {


    constructor() : this("", "",
        "", "", "",
        "", ""
    )

}