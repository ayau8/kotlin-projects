fun main() {
    val firstUser = User(name = "Alvin", age = 30)
    println(firstUser.getUserDetails())

    val secondUser = UserWithId(name = "Ken", age = 31, id = "1")
    println(secondUser.getUserDetails())

    val thirdUser = UserWithId(name = "Kento", age = 32, id = null)
    println(thirdUser.getUserDetails())
}
