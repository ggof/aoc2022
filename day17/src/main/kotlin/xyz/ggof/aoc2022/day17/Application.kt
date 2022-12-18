package xyz.ggof.aoc2022.day17

import java.io.File

data class Pos(val x: Long, val y: Long)

typealias Shape = List<Pos>

fun Shape.down(full: List<Pos>): Shape? {
	val next = map { Pos(it.x, it.y - 1) }
	return if (next.any { it in full }) null
	else next
}

fun Shape.left(full: List<Pos>): Shape {
	val next = map { Pos(it.x - 1, it.y) }
	return if (next.any { it.x < 0 || it in full }) this
	else next
}

fun Shape.right(full: List<Pos>): Shape {
	val next = map { Pos(it.x + 1, it.y) }
	return if (next.any { it.x > 6 || it in full }) this
	else next
}

fun List<Pos>.onlyMaxY() = sortedByDescending(Pos::y).take(5000)

fun LineH(y: Long): Shape = listOf(Pos(2, y), Pos(3, y), Pos(4, y), Pos(5, y))
fun Plus(y: Long): Shape = listOf(Pos(2, y + 1), Pos(3, y), Pos(3, y + 1), Pos(3, y + 2), Pos(4, y + 1))
fun L(y: Long): Shape = listOf(Pos(2, y), Pos(3, y), Pos(4, y), Pos(4, y + 1), Pos(4, y + 2))
fun LineV(y: Long): Shape = listOf(Pos(2, y), Pos(2, y + 1), Pos(2, y + 2), Pos(2, y + 3))
fun Square(y: Long): Shape = listOf(Pos(2, y), Pos(2, y + 1), Pos(3, y), Pos(3, y + 1))

class Game(val moves: List<Char>) {
	var imove = 0
	var ishape = 0

	val shapes = listOf(
		::LineH,
		::Plus,
		::L,
		::LineV,
		::Square
	)

	fun placeShape(full: List<Pos>): List<Pos> {
		val start = full.maxOf { it.y } + 4
		var shape = shapes[ishape](start)
		ishape = (ishape + 1) % shapes.size

		while (true) {
			shape = if (moves[imove] == '<') shape.left(full) else shape.right(full)
			imove = (imove + 1) % moves.size
			shape = shape.down(full) ?: break
		}

		return full + shape
	}

	fun play(nb: Long) = (0 until nb)
		.fold(List(7) { Pos(it.toLong(), 0) }) { acc, _ -> placeShape(acc).onlyMaxY() }
		.maxOf(Pos::y)
}

fun main() {
	val input = File("input.txt").readText().toList()

	val part1 = Game(input).play(2022)
	println("part 1: $part1")
	val part2 = Game(input).play(1000000000000L)
	println("part 2: $part2")
}