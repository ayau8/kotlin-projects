open class UserWithId(val name: String, val age: Int, val id: String?) {
    open fun getUserDetails(): String {
        return "I am $name and $age years old. My ID is $id"
    }
}

fun main() {
    val secondUser = UserWithId(name = "Ken", age = 31, id = "1")
    println(secondUser.getUserDetails())

    val thirdUser = UserWithId(name = "Kento", age = 32, id = null)
    println(thirdUser.getUserDetails())
}
