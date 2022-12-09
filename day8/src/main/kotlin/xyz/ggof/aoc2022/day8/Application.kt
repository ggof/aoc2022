package xyz.ggof.aoc2022.day8

import java.io.File

enum class Direction {
	North,
	East,
	South,
	West
}


fun List<String>.toGrid() = map { it.toCharArray().map(Char::digitToInt) }

operator fun List<List<Int>>.get(x: Int, y: Int) = this[y][x]

fun List<List<Int>>.visible(x: Int, y: Int): List<Direction> {
	val acc = mutableListOf<Direction>()
	val biggerFromWest = (0 until x).map { this[it, y] }.any { it >= this[x, y] }
	val biggerFromEast = (x + 1 until this[x].size).map { this[it, y] }.any { it >= this[x, y] }
	val biggerFromNorth = (0 until y).map { this[x, it] }.any { it >= this[x, y] }
	val biggerFromSouth = (y + 1 until size).map { this[x, it] }.any { it >= this[x, y] }

	if (!biggerFromWest) acc.add(Direction.West)
	if (!biggerFromEast) acc.add(Direction.East)
	if (!biggerFromNorth) acc.add(Direction.North)
	if (!biggerFromSouth) acc.add(Direction.South)

	return acc
}

fun List<List<Int>>.scenicScore(x: Int, y: Int): Int {
	var ws = 0
	for (xx in (x - 1) downTo 0) {
		ws++
		if (this[xx, y] >= this[x, y]) break
	}

	var es = 0
	for (xx in x + 1 until this[y].size) {
		es++
		if (this[xx, y] >= this[x, y]) break
	}

	var ns = 0
	for (yy in (y - 1) downTo 0) {
		ns++
		if (this[x, yy] >= this[x, y]) break
	}

	var ss = 0
	for (yy in y + 1 until size) {
		ss++
		if (this[x, yy] >= this[x, y]) break
	}

	return ws * es * ns * ss
}

fun List<List<Int>>.countVisible(): Int = withIndex()
	.sumOf { (y, l) ->
		l.withIndex().count { (x, i) -> visible(x, y).isNotEmpty() }
	}


fun List<List<Int>>.highestScenicScore(): Int =
	withIndex().maxOf { (y, l) -> l.withIndex().maxOf { (x, _) -> this.scenicScore(x, y) } }

fun main() {
	val input = File("input.txt").readLines().toGrid()

	val part1 = input.countVisible()
	val part2 = input.highestScenicScore()
	println("part 1: $part1")
	println("part 2: $part2")


}