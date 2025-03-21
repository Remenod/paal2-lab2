typealias Matrix<T> = List<List<T>>
typealias MutableMatrix<T> = MutableList<MutableList<T>>
typealias Bool = Boolean

private val rand = java.util.Random()

fun java.util.Random.nextInt(range: IntRange): Int =
    this.nextInt(range.first, range.last + 1)

fun String.toIntRangeOrNull(): IntRange? = split("..")
    .mapNotNull { it.toIntOrNull() }
    .takeIf { it.size == 2 && it[0] <= it[1] }
    ?.let { it[0]..it[1] }

fun <T> Matrix<T>.toMutableMatrix(): MutableMatrix<T> =
    this.map { it.toMutableList() }.toMutableList()


fun readIntRangeTrue(message: String = "", predicate: (IntRange) -> Bool = { it.first <= it.last }): IntRange {
    while (true) {
        if (message.isNotEmpty()) println(message)
        val input = readln().toIntRangeOrNull()

        if (input != null && predicate(input))
            return input

        println("Invalid input")
    }
}

fun readIntTrue(message: String = "", inputSign: Bool = true, predicate: (Int) -> Bool = { true }): Int {
    while (true) {
        if (message.isNotEmpty()) println(message)
        if (inputSign) print("> ")
        val input = readln().toIntOrNull()

        if (input != null && predicate(input))
            return input

        println("Invalid input")
    }
}


fun generateIntList(elementRange: IntRange? = null, lengthRange: IntRange? = null): List<Int> {
    val range1 = lengthRange ?: readIntRangeTrue("Enter array random length range. (Example 1..10)")
    val range2 = elementRange ?: readIntRangeTrue("Enter array random element value range. (Example 1..10)")

    return List(rand.nextInt(range1)) { rand.nextInt(range2) }
}

fun inputIntList(size: Int = -1): List<Int> {
    while (true) {
        val input = readln().split(" ")
        val numbers = input.mapNotNull { it.toIntOrNull() }

        if (numbers.size == input.size && (size <= 0 || numbers.size == size))
            return numbers

        println("Invalid input")
    }
}

fun inputIntMatrix(isSquare: Bool): Matrix<Int> {
    val n = readIntTrue("Enter number of rows:")
    println("Enter $n sub arrays of matrix" + if (isSquare) " with $n elements in each:" else ":")
    return List(size = n) { inputIntList(if (isSquare) n else -1) }
}

fun generateIntMatrix(isSquare: Bool): Matrix<Int> {
    val n = readIntTrue("Enter number of rows:")
    val lengthRange =
        if (isSquare) n..n
        else readIntRangeTrue("Enter sub array random length range. (Example 1..10)")
    val elementsRange = readIntRangeTrue("Enter sub array elements range. (Example 1..10)")

    val result = List(size = n) { generateIntList(elementsRange, lengthRange) }

    println("Generated matrix:")
    for (i in result)
        println("\t" + i.toString())

    return result
}

fun requestIntMatrix(isSquare: Bool = false): Matrix<Int> {
    println(
        """
            Input types:
                1 -> line by line
                2 -> random
            """.trimIndent()
    )
    println("Enter input type:")
    return when (readIntTrue()) {
        1 -> inputIntMatrix(isSquare)
        2 -> generateIntMatrix(isSquare)
        else -> throw IllegalArgumentException("Invalid input")
    }
}