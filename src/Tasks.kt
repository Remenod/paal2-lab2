object Tasks {
    private val tasks: Map<String, () -> Unit> = linkedMapOf(
        "var  5: Знайти кількість додатних елементів кожного рядка." to ::task1,
        "var  5: Транспонувати матрицю, лишаючи її в тому самому масиві" to ::task2,
        "var  9: Упорядкувати за неспаданням головну діагональ матриці" to ::task3,
        "var 10: Упорядкувати рядки матриці за неспаданням добутків елементів у цих рядках." to ::task4
    )

    private fun task1() {
        val matrix = requestIntMatrix()
        val result = matrix.map { it.filter { x -> x > 0 }.size }
        println("Result:")
        for (i in matrix.indices)
            println("\t" + result[i].toString() + " -> " + matrix[i].toString())
    }

    private fun task2() {
        val matrix = requestIntMatrix(true).toMutableMatrix()

        for (i in matrix.indices) {
            for (j in i + 1..<matrix[i].size) {
                val temp = matrix[i][j]
                matrix[i][j] = matrix[j][i]
                matrix[j][i] = temp
            }
        }
        println("Result:")
        for (i in matrix)
            println("\t" + i.toString())
    }

    private fun task3() {
        val matrix = requestIntMatrix(true).toMutableMatrix()
        var swapped = true
        while (swapped) {
            swapped = false
            for (i in 1..<matrix.size)
                if (matrix[i][i] < matrix[i - 1][i - 1]) {
                    swapped = true
                    val temp = matrix[i][i]
                    matrix[i][i] = matrix[i - 1][i - 1]
                    matrix[i - 1][i - 1] = temp
                }
        }
        println("Result:")
        for (i in matrix)
            println("\t" + i.toString())
    }

    private fun task4() {
        val matrix = requestIntMatrix()
        val mulFold = matrix.map { it.fold(1) { x, y -> x * y } }
        val sortedMatrix = matrix.zip(mulFold) { row, mul -> row to mul }.sortedBy { it.second }.map { it.first }
        val sortedMulFold = mulFold.sorted()

        println("Result:")
        for (i in matrix.indices)
            println("\t" + sortedMulFold[i] + " -> " + sortedMatrix[i].toString())
    }

    private fun runTask(task: Int) {
        tasks.values.elementAt(task)()
    }

    fun selectAndRunTask() {
        println("Select task:")
        for (i in tasks.keys.withIndex())
            println("\t" + (i.index + 1).toString() + " -> " + i.value)
        runTask(readIntTrue { it > 0 && it <= tasks.size } - 1)
    }
}