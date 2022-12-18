package xyz.ggof.aoc2022.day09

import java.io.File
import kotlin.math.sqrt

data class Position(val x: Int, val y: Int)

typealias Move = Pair<Int, Int>

data class Rope(val hd: Position, val tl: List<Position>) {
	constructor(len: Int) : this(Position(0, 0), List(len - 1) { Position(0, 0) })

	fun apply(m: Move): Rope {
		val newHD = Position(hd.x + m.first, hd.y + m.second)
		val newTL = tl.scan(newHD) { h, t -> moveTail(h, t) }.drop(1)
		return Rope(newHD, newTL)
	}
}

fun List<String>.unfold() = flatMap {
	val (dir, len) = it.split(" ")
	val move = when (dir) {
		"U" -> 0 to 1
		"R" -> 1 to 0
		"D" -> 0 to -1
		"L" -> -1 to 0
		else -> error("$dir is not a valid option")
	}
	List(len.toInt()) { move }
}

fun Move.clamp(): Move {
	val dist = sqrt((first * first + second * second).toDouble())
	return if (dist < 2) 0 to 0
	else first.coerceIn(-1, 1) to second.coerceIn(-1, 1)
}

fun moveTail(hd: Position, tl: Position): Position {
	val diff = (hd.x - tl.x) to (hd.y - tl.y)
	val (x, y) = diff.clamp()
	return Position(tl.x + x, tl.y + y)
}

fun List<Move>.allPositions(len: Int) = runningFold(Rope(len)) { rope, move -> rope.apply(move) }

fun List<Rope>.distinctTailPositions(): List<Position> = map { it.tl.last() }.distinct()

fun main() {
	val moves = File("input.txt").readLines().unfold()
	val part1 = moves.allPositions(2).distinctTailPositions().count()
	val part2 = moves.allPositions(10).distinctTailPositions().count()
	println("part 1: $part1")
	println("part 2: $part2")
}