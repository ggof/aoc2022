package xyz.ggof.aoc2022.day14

import kotlinx.collections.immutable.*
import java.io.File
import kotlin.math.*

data class Pos(val x: Int, val y: Int) {
	operator fun plus(other: Pos) = Pos(x + other.x, y + other.y)
	operator fun minus(other: Pos) = Pos(x - other.x, y - other.y)
	val magnitude by lazy { sqrt((x * x + y * y).toDouble()).roundToInt() }
	val normalized by lazy { Pos(x / magnitude, y / magnitude) }
}

val down = Pos(0, 1)
val diagL = Pos(-1, 1)
val diagR = Pos(1, 1)

fun String.toPos(): Pos {
	val (x, y) = trim().split(",").map { it.toInt() }
	return Pos(x, y)
}

fun String.toPoints(): List<Pos> = split("->").map { it.toPos() }

fun List<Pos>.fill(): List<Pos> = windowed(2).flatMap { (b, e) ->
	val dir = Pos(b.x - e.x, b.y - e.y).normalized
	fillRecursive(b - dir, dir, e, emptyList())
} + this

tailrec fun fillRecursive(cur: Pos, dir: Pos, end: Pos, acc: List<Pos>): List<Pos> =
	if (cur == end) acc
	else fillRecursive(cur - dir, dir, end, acc + cur)

typealias Predicate = Set<Pos>.(Pos) -> Boolean

tailrec fun PersistentSet<Pos>.maxSandRec(beg: Pos, floor: Int, cur: Pos = beg, i: Int = 0, cond: Predicate): Int =
	if (!cond(cur)) i
	else if (cur.y + 1 == floor) (this + cur).maxSandRec(beg, floor, beg, i + 1, cond)
	else if (cur + down !in this) maxSandRec(beg, floor, cur + down, i, cond)
	else if (cur + diagL !in this) maxSandRec(beg, floor, cur + diagL, i, cond)
	else if (cur + diagR !in this) maxSandRec(beg, floor, cur + diagR, i, cond)
	else (this + cur).maxSandRec(beg, floor, beg, i + 1, cond)

fun main() {
	val input = File("input.txt").readLines().flatMap { it.toPoints().fill() }.toPersistentSet()
	val begin = Pos(500, 0)

	val floor = input.maxOf(Pos::y) + 2

	val part1 = input.maxSandRec(begin, floor) { it.y < input.maxOf(Pos::y) }
	println("part 1: $part1")
	val part2 = input.maxSandRec(begin, floor) { begin !in this }
	println("part 2: $part2")
}