package xyz.ggof.aoc2022.day12

import java.io.File

const val a = 'a'.code
const val z = 'z'.code
const val s = 'S'.code
const val e = 'E'.code

data class Pos(val x: Int, val y: Int)

typealias Grid<T> = MutableList<MutableList<T>>

operator fun <T> Grid<T>.get(x: Int, y: Int) = this[y][x]
operator fun <T> Grid<T>.set(x: Int, y: Int, value: T) {
	this[y][x] = value
}

fun List<String>.toGrid() = map { it.toCharArray().map { c -> c.code }.toMutableList() }.toMutableList()

fun Grid<Int>.bfs(start: Pos, found: (Pos) -> Boolean): List<Pos>? {
	val paths = mutableListOf(listOf(start))
	val visited = mutableSetOf<Pos>()

	while (paths.isNotEmpty()) {
		val path = paths.removeFirstOrNull() ?: break
		val last = path.last()
		if (found(last)) return path
		if (last in visited) continue

		visited.add(last)

		val newPaths = listOf(
			last.copy(x = last.x + 1), last.copy(x = last.x - 1), last.copy(y = last.y + 1), last.copy(y = last.y - 1),
		).filter { p -> p !in visited && inside(p) && this[p.x, p.y] - this[last.x, last.y] < 2 }.map { path + it }

		paths.addAll(newPaths)
	}

	return null
}

fun Grid<Int>.shortestTo(start: Pos, end: Pos) = bfs(start) { it == end }

val List<Pos>.steps get() = size - 1

fun Grid<Int>.inside(p: Pos) = p.y >= 0 && p.y < this.size && p.x >= 0 && p.x < this[p.y].size

fun Grid<Int>.replace(old: Int, new: Int): Pos {
	for (y in indices) {
		for (x in this[y].indices) {
			if (this[x, y] == old) {
				this[x, y] = new
				return Pos(x, y)
			}
		}
	}

	error("no start?")
}

fun Grid<Int>.every(value: Int): List<Pos> =
	flatMapIndexed { y, r -> r.withIndex().filter { (_, v) -> v == value }.map { Pos(it.index, y) } }

fun Grid<Int>.part1(start: Pos, end: Pos) = shortestTo(start, end)?.steps ?: error("no shortest path")
fun Grid<Int>.part2(end: Pos) = every(a).mapNotNull { shortestTo(it, end)?.steps }.min()

fun main() {
	val input = File("input.txt").readLines().toGrid()

	val start = input.replace(s, a)
	val end = input.replace(e, z)

	val part1 = input.part1(start, end)
	val part2 = input.part2(end)
	println("part 1: $part1")
	println("part 2: $part2")
}