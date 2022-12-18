package xyz.ggof.aoc2022.day04

import java.io.File

fun String.toPair(): Pair<Pair<Int, Int>, Pair<Int, Int>> {
	val (f, s) = split(",").map { it.split("-").map(String::toInt) }
	return (f[0] to f[1]) to (s[0] to s[1])
}

fun full(p: Pair<Pair<Int, Int>, Pair<Int, Int>>): Boolean = p.let { (l, r) ->
	(l.first <= r.first && l.second >= r.second) || (l.first >= r.first && l.second <= r.second)
}

fun some(p: Pair<Pair<Int, Int>, Pair<Int, Int>>): Boolean = p.let { (l, r) ->
	l.first <= r.second && l.second >= r.first
}

fun List<String>.overlapping(p: (Pair<Pair<Int, Int>, Pair<Int, Int>>) -> Boolean) = count { p(it.toPair()) }

fun main() {
	val lines = File("input.txt").readLines()
	println("part 1: there are ${lines.overlapping(::full)} pairs with full overlap")
	println("part 2: there are ${lines.overlapping(::some)} pairs with some overlap")
}