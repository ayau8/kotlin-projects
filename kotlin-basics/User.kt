open class User (val name: String, val age: Int) {
    open fun getUserDetails(): String {
        return "I am $name and $age years old"
    }
}
