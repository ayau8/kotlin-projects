// Adding ? for handling null
val name: String? = "Alvin"
println(name)

// ?. as safe call operator to access a method only if the var is not null

println(name?.length)

// ?. and let to execute code if the var is not null. Called by it by default

name?.let {
    println("Length: ${it.length}")
}

// ?: as Elvis operator to give a default value if something is null

val nullName: String? = null
val length = nullName?.length ?: 0
println(length)

// !! as not-null assertion if sure the value isn't null. It throws NPE if null

println(nullName!!.length)
