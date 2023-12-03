

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.parseGame() }
            .filter { it.matchesElfConstraints() }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.parseGame() }
            .sumOf { it.calculatePowerForGame() }
    }

    val testInput = readInput("Day02_test")
    println("Part 1: ${part1(testInput)}")

    val input = readInput("Day02_test")
    println("Part 2: ${part2(input)}")
}

private fun String.parseGame(): Game {
    val gameParts = this.split(":")
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

    return Game(gameId, cubeObjectList.map { Subset(it) })
}

private fun Game.matchesElfConstraints(): Boolean {
    val maxRedPerSubset = getMaxOfColor("red")
    val maxGreenPerSubset = getMaxOfColor("green")
    val maxBluePerSubset = getMaxOfColor("blue")

    return maxRedPerSubset <= 12 && maxGreenPerSubset <= 13 && maxBluePerSubset <= 14
}

private fun Game.calculatePowerForGame(): Int {
    val maxRedPerSubset = getMaxOfColor("red")
    val maxGreenPerSubset = getMaxOfColor("green")
    val maxBluePerSubset = getMaxOfColor("blue")

    return maxRedPerSubset * maxGreenPerSubset * maxBluePerSubset
}

private fun Game.getMaxOfColor(color: String): Int {
    return this.subsets.maxOf { subset ->
        subset.cubes.firstOrNull { it.color == color }?.count ?: 0
    }
}

data class Subset(
    val cubes: List<Cubes>,
)

data class Cubes(
    val color: String,
    val count: Int
)

data class Game(
    val id: Int,
    val subsets: List<Subset>
)