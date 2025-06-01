// User class with properties and a method to print user details

open class User (val name: String, val age: Int) {
    open fun printUserDetails(): String {
        return "I am $name and $age years old"
    }
}

val firstUser = User(name = "Alvin", age = 30)
println(firstUser.printUserDetails())

// Inheritance User Class. Include Null Safety for ID
class UserWithId (name: String, age: Int, val id: String?) : User(name, age) {
    override fun printUserDetails(): String {
        return "I am $name and $age years old. My ID is $id"
    }
}

val secondUser = UserWithId(name = "Ken", age = 31, id = "1")
println(secondUser.printUserDetails())

val thirdUser = UserWithId(name = "Kento", age = 32, id = null)
println(thirdUser.printUserDetails())
