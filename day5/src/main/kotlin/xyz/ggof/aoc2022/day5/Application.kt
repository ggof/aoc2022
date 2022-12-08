package xyz.ggof.aoc2022.day5

import java.io.File

fun String.toTowers(): List<List<Char>> {
	val lines = split("\n").dropLast(1).map { it.filterIndexed { i, _ -> i % 4 == 1 } }

	return lines.fold(Array<List<Char>>(lines.last().length) { emptyList() }) { acc, cur ->
		for ((i, c) in cur.withIndex()) {
			if (c != ' ') acc[i] = acc[i] + c
		}
		acc
	}.map { it.reversed() }
}

fun String.toMove() = split(" ").mapNotNull { it.toIntOrNull() }

fun String.toMoves() = split("\n").dropLast(1).map { it.toMove() }

fun String.toInputs(): Pair<List<List<Char>>, List<List<Int>>> {
	val (f, s) = split("\n\n")
	return f.toTowers() to s.toMoves()
}

fun move9000(l: List<Char>, qt: Int): List<Char> = l.takeLast(qt).reversed()
fun move9001(l: List<Char>, qt: Int): List<Char> = l.takeLast(qt)

fun List<List<Char>>.set(i: Int, l: List<Char>) = take(i).plusElement(l).plus(drop(i + 1))

fun List<List<Char>>.move(qt: Int, from: Int, to: Int, move: (List<Char>, Int) -> List<Char>) =
	set(to, get(to) + move(get(from), qt)).set(from, get(from).dropLast(qt))


fun List<List<Char>>.applyMoves(m: List<List<Int>>, move: (List<Char>, Int) -> List<Char>) =
	m.fold(this) { acc, cur -> acc.move(cur[0], cur[1] - 1, cur[2] - 1, move) }

fun List<List<Char>>.topBoxes() = mapNotNull { it.lastOrNull() }.joinToString("")

fun main() {
	val input = File("input.txt").readText()

	val (t, m) = input.toInputs()

	val part1 = t.applyMoves(m, ::move9000).topBoxes()
	val part2 = t.applyMoves(m, ::move9001).topBoxes()

	println(part1)
	println(part2)
}