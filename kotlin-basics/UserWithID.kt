class UserWithId(name: String, age: Int, val id: String?) : User(name, age) {
    override fun getUserDetails(): String {
        return "${super.getUserDetails()}. My ID is $id"
    }
}
