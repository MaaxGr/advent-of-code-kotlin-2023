
private val digitWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { word ->
            val firstDigit = word.first { it.digitToIntOrNull() != null }
            val lastDigit = word.last { it.digitToIntOrNull() != null }

            "$firstDigit$lastDigit".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val allowedDigits = digitWords + listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")

        return input.sumOf { word ->
            val (_, firstMatch) = word.findAnyOf(allowedDigits)!!
            val (_, lastMatch) = word.findLastAnyOf(allowedDigits)!!
            "${firstMatch.digitStringToInt()}${lastMatch.digitStringToInt()}".toInt()
        }
    }

    val testInput = readInput("Day01_test")
    println("Part 1: ${part1(testInput)}")

    val input = readInput("Day01_test")
    println("Part 2: ${part2(input)}")
}

private fun String.digitStringToInt(): Int {
    return if (this.toIntOrNull() != null) {
        return this.toInt()
    } else {
        digitWords.indexOf(this) + 1
    }
}
