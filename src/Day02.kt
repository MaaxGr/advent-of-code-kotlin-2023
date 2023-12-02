fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { gameRowString ->
                val gameParts = gameRowString.split(":")
                val gameId = gameParts.first().substringAfter("Game").trim().toInt()

                val gameSubsets = gameParts.last().trim().split(";")

                val cubeObjectList = gameSubsets.map {
                    val cubes = it.split(",").map { it.trim() }
                    cubes.map {
                        val count = it.substringBefore(" ").trim().toInt()
                        val color = it.substringAfter(" ").trim()

                        Cubes(color, count)
                    }
                }

                Entry(gameId, cubeObjectList.map { Subset(it) })
            }
            .filter { gameMatchesElfConstraints(it) }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { gameRowString ->
                val gameParts = gameRowString.split(":")
                val gameId = gameParts.first().substringAfter("Game").trim().toInt()

                val gameSubsets = gameParts.last().trim().split(";")

                val cubeObjectList = gameSubsets.map {
                    val cubes = it.split(",").map { it.trim() }
                    cubes.map {
                        val count = it.substringBefore(" ").trim().toInt()
                        val color = it.substringAfter(" ").trim()

                        Cubes(color, count)
                    }
                }

                Entry(gameId, cubeObjectList.map { Subset(it) })
            }
            .sumOf { getPowerForGame(it) }
    }

    val testInput = readInput("Day02_test")
    println("Part 1: ${part1(testInput)}")

    val input = readInput("Day02_test")
    println("Part 2: ${part2(input)}")

}

private fun gameMatchesElfConstraints(game: Entry): Boolean {
    val maxRedPerSubset = game.subsets.maxOfOrNull {
        it.cubes.filter { it.color == "red" }
            .sumOf { it.count }
    } ?: 0

    val maxGreenPerSubset = game.subsets
        .map {
            it.cubes.filter { it.color == "green" }
                .sumOf { it.count }
        }.maxOrNull() ?: 0

    val maxBluePerSubset = game.subsets
        .map {
            it.cubes.filter { it.color == "blue" }
                .sumOf { it.count }
        }.maxOrNull() ?: 0
    return maxRedPerSubset <= 12 && maxGreenPerSubset <= 13 && maxBluePerSubset <= 14
}

private fun getPowerForGame(game: Entry): Int {
    val maxRedPerSubset = game.getMaxOfColor("red")
    val maxGreenPerSubset = game.getMaxOfColor("green")
    val maxBluePerSubset = game.getMaxOfColor("blue")

    return maxRedPerSubset * maxGreenPerSubset * maxBluePerSubset
}

private fun Entry.getMaxOfColor(color: String): Int {
    return this.subsets.maxOfOrNull {
        it.cubes.filter { it.color == color }
            .sumOf { it.count }
    } ?: 0
}

data class Subset(
    val cubes: List<Cubes>,
)

data class Cubes(
    val color: String,
    val count: Int
)


data class Entry(
    val id: Int,
    val subsets: List<Subset>
)