fun main() {
    val day03 = Day03()

    fun part1(input: List<String>): Long {
        with(day03) {
            val game = parseGame(input)

            return game.potentialPartNumbers
                .filter { game.isRealPartNumber(it) }
                .sumOf { it.number.toLong() }
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day03_test")
    println("Part 1: ${part1(testInput)}")

    val input = readInput("Day03_example")
    println("Part 2: ${part2(input)}")
}

class Day03 {

    data class Game(
        val rows: List<String>,
        val potentialPartNumbers: List<PotentialPartNumber>,
        val gears: List<PotentialGear>,
        val lastColumnIndex: Int,
        val lastRowIndex: Int,
    )

    data class PotentialPartNumber(
        val number: Int,
        val rowIndex: Int,
        val columnStartIndex: Int,
        val columnEndIndex: Int
    )

    data class PotentialGear(
        val y: Int,
        val x: Int,
    )

    fun Game.getSymbolAt(y: Int, x: Int): Char {
        return rows[y].elementAt(x)
    }

    fun Char.isValidSymbol(): Boolean {
        return this != '.'
    }

    fun Game.isRealPartNumber(potentialPartNumber: PotentialPartNumber): Boolean {
        val symbolLeftPresent =
            if (potentialPartNumber.columnStartIndex == 0) false else getSymbolAt(potentialPartNumber.rowIndex, potentialPartNumber.columnStartIndex - 1).isValidSymbol()
        val symbolRightPresent =
            if (potentialPartNumber.columnEndIndex == lastColumnIndex) false else getSymbolAt(potentialPartNumber.rowIndex, potentialPartNumber.columnEndIndex + 1).isValidSymbol()

        val symbolTopPresent = if (potentialPartNumber.rowIndex == 0) false else {
            var isValidSymbol = false
            for (index in potentialPartNumber.columnStartIndex..potentialPartNumber.columnEndIndex) {
                if (getSymbolAt(potentialPartNumber.rowIndex - 1, index).isValidSymbol()) {
                    isValidSymbol = true
                }
            }
            isValidSymbol
        }

        val symbolBottomPresent = if (potentialPartNumber.rowIndex == rows.size - 1) false else {
            var isValidSymbol = false
            for (index in potentialPartNumber.columnStartIndex..potentialPartNumber.columnEndIndex) {
                if (getSymbolAt(potentialPartNumber.rowIndex + 1, index).isValidSymbol()) {
                    isValidSymbol = true
                }
            }
            isValidSymbol
        }

        val symbolTopLeftDiagonalPresent = if (potentialPartNumber.rowIndex == 0 || potentialPartNumber.columnStartIndex == 0) false
        else getSymbolAt( potentialPartNumber.rowIndex - 1, potentialPartNumber.columnStartIndex - 1).isValidSymbol()

        val symbolTopRightDiagonalPresent = if (potentialPartNumber.rowIndex == 0 || potentialPartNumber.columnEndIndex == lastColumnIndex) false
        else getSymbolAt( potentialPartNumber.rowIndex - 1, potentialPartNumber.columnEndIndex + 1).isValidSymbol()

        val symbolBottomLeftDiagonalPresent = if (potentialPartNumber.rowIndex == rows.size - 1 || potentialPartNumber.columnStartIndex == 0) false
        else getSymbolAt( potentialPartNumber.rowIndex + 1, potentialPartNumber.columnStartIndex - 1).isValidSymbol()

        val symbolBottomRightDiagonalPresent = if (potentialPartNumber.rowIndex == rows.size - 1 || potentialPartNumber.columnEndIndex == lastColumnIndex) false
        else getSymbolAt( potentialPartNumber.rowIndex + 1, potentialPartNumber.columnEndIndex + 1).isValidSymbol()

        return symbolLeftPresent || symbolRightPresent || symbolTopPresent || symbolBottomPresent
                || symbolTopLeftDiagonalPresent || symbolTopRightDiagonalPresent
                || symbolBottomLeftDiagonalPresent || symbolBottomRightDiagonalPresent
    }

    fun Game.isGear(potentialGear: PotentialGear, partNumbers: List<PotentialPartNumber>) {
        val foundNeighbors = listOf<PotentialPartNumber>()

        for (partNumber in partNumbers) {

        }

    }


    fun parseGame(rows: List<String>): Game {
        val potentialPartNumbers = mutableListOf<PotentialPartNumber>()

        rows.forEachIndexed { rowIndex, row ->
            var numberBuffer = ""
            var numberStartColumnIndex = 0

            row.forEachIndexed { characterIndex, character ->
                if (character.digitToIntOrNull() != null) {
                    if (numberBuffer.isBlank()) {
                        numberStartColumnIndex = characterIndex
                    }

                    numberBuffer += character
                } else {
                    if (numberBuffer.isNotBlank()) {
                        if (numberBuffer != "-") {
                            potentialPartNumbers.add(
                                PotentialPartNumber(
                                    number = numberBuffer.toInt(),
                                    rowIndex = rowIndex,
                                    columnStartIndex = numberStartColumnIndex,
                                    columnEndIndex = characterIndex - 1
                                )
                            )
                        }
                        numberBuffer = ""
                    }
                }
            }

            if (numberBuffer.isNotBlank()) {
                potentialPartNumbers.add(
                    PotentialPartNumber(
                        number = numberBuffer.toInt(),
                        rowIndex = rowIndex,
                        columnStartIndex = numberStartColumnIndex,
                        columnEndIndex = row.length - 1
                    )
                )
            }
        }


        val gears = mutableListOf<PotentialGear>()
        rows.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, character ->
                if (character == '*') {
                    gears.add(PotentialGear(rowIndex, columnIndex))
                }
            }
        }

        return Game(
            rows = rows,
            potentialPartNumbers = potentialPartNumbers.toList(),
            gears = gears.toList(),
            lastRowIndex = rows.size - 1,
            lastColumnIndex = rows[0].length - 1
        )

    }

}

