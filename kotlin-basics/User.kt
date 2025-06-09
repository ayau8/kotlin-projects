open class User (val name: String, val age: Int) {
    open fun getUserDetails(): String {
        return "I am $name and $age years old"
    }
}

fun main() {
    val firstUser = User(name = "Alvin", age = 30)
    println(firstUser.getUserDetails())
}