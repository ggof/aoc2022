package xyz.ggof.aoc2022.day16

import java.io.File
import java.lang.Integer.max
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

data class Valve(val rate: Int, val next: List<Int>)

typealias Cave = Array<Valve>

fun String.toCaveEntry(): Pair<Int, Valve> {
	val (f, s) = split(";").map { it.trim() }
	val name = f.drop(6).takeWhile { it.isDigit() }.toInt()
	val rate = f.takeLastWhile { it.isDigit() }.toInt()
	val neighbors = s.split(" ").drop(4).map { it.trimEnd(',').trim() }.map { it.toInt() }
	return name to Valve(rate, neighbors)
}

val Int.mask: ULong get() = 1UL shl this

class CachedSolver(val cave: Cave, val start: Int) {
	data class Key(val pos: Int, val time: Int, val opened: ULong, val more: Boolean)

	private val cache = HashMap<Key, Int>(30_000_000)

	fun solve(pos: Int, time: Int, opened: ULong, more: Boolean = false): Int {
		val key = Key(pos, time, opened, more)
		val v = cache[key]
		if (v != null) return v
		val result = solverec(pos, time, opened, more)
		cache[key] = result
		return result
	}

	fun solverec(pos: Int, time: Int, opened: ULong, more: Boolean = false): Int {
		if (time == 0) {
			return if (!more) 0
			else solve(start, 26, opened)
		}

		val valve = cave[pos]

		val score = valve.next.maxOf { solve(it, time - 1, opened, more) }

		return if (valve.rate == 0 || pos.mask and opened != 0UL) score
		else max(score, (time - 1) * valve.rate + solve(pos, time - 1, opened or pos.mask, more))
	}
}

fun List<String>.toCave(): Cave = map { it.toCaveEntry() }
	.fold(Array<Valve?>(size) { null }) { acc, cur -> acc[cur.first] = cur.second; acc }
	.requireNoNulls()

fun String.replaceNames() = lines()
	.map { it.split(" ").component2() }
	.withIndex()
	.fold(this) { acc, (i, name) -> acc.replace(name, i.toString()) }
	.lines()

fun String.startIndex() = lines().indexOfFirst { it.split(" ")[1] == "AA" }

@OptIn(ExperimentalTime::class)
fun main() {
	val text = File("input.txt").readText()
	val aa = text.startIndex()
	val input = text.replaceNames().toCave()
	val solver = CachedSolver(input, aa)

	val part1 = measureTimedValue { solver.solve(aa, 30, 0U) }
	println("part 1: ${part1.value} in ${part1.duration}")
	val part2 = measureTimedValue { solver.solve(aa, 26, 0U, true) }
	println("part 2: ${part2.value} in ${part2.duration}")
}