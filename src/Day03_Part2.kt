fun main() {
    val testInput = readInput("Day03_test")
    println("Part 2: ${Day03Part2(testInput).part2()}")
}

class Day03Part2(private val input: List<String>) {

    private val lastColumnIndex = input.first().length - 1
    private val lastRowIndex = input.size - 1

    fun part2(): Int {
        return input.findStars().sumOf {
            val numbers = it.getNeighborNumbers().map { it.toRealNumber() }.distinct()

            if (numbers.size < 2) {
                0
            } else {
                numbers.first().number * numbers[1].number
            }
        }
    }

    private fun List<String>.findStars(): List<Position> {
        return this.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, character ->
                if (character == '*') {
                    Position(y = rowIndex, x = columnIndex)
                } else null
            }.filterNotNull()
        }.flatten()
    }

    private fun Position.getNeighborNumbers(): List<Position> {
        val positions = mutableListOf<Position>()

        val minPositionY = if (y - 1 < 0) 0 else y - 1
        val minPositionX = if (x - 1 < 0) 0 else x - 1
        val maxPositionY = if (y + 1 > lastRowIndex) lastRowIndex else y + 1
        val maxPositionX = if (x + 1 > lastColumnIndex) lastColumnIndex else x + 1

        for (rowIndex in minPositionY..maxPositionY) {
            for (columnIndex in minPositionX..maxPositionX) {

                val character = input[rowIndex][columnIndex]

                if (character.digitToIntOrNull() != null) {
                    positions.add(
                        Position(
                            y = rowIndex,
                            x = columnIndex,
                        )
                    )
                }
            }
        }
        return positions
    }

    fun Position.toRealNumber(): RealNumber {
        val row = input[y]
        var startIndexX = x
        var endIndexX = x

        var number = "${row[x]}"

        while (startIndexX > 0) {
            startIndexX--

            val char = row[startIndexX]
            if (char.isDigit()) {
                number = char + number
            } else {
                startIndexX++
                break
            };
        }

        while (endIndexX < lastColumnIndex) {
            endIndexX++

            val char = row[endIndexX]
            if (char.isDigit()) {
                number += char
            } else {
                endIndexX--
                break
            };
        }

        return RealNumber(
            number = number.toInt(),
            startIndexX = startIndexX,
            endIndexX = endIndexX,
            indexY = y
        )
    }

    data class Position(
        val y: Int,
        val x: Int,
    )

    data class RealNumber(
        val number: Int,
        // Indices for distinct algorithm
        // Example:
        // 112
        // .*.
        // => Three entries with the same real number "112"
        val startIndexX: Int,
        val endIndexX: Int,
        val indexY: Int
    )

}