package xyz.ggof.aoc2022.day16

import kotlinx.collections.immutable.*
import kotlinx.collections.immutable.plus
import java.io.File
import java.lang.Integer.max
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

data class Valve(val rate: Int, val next: List<String>)

typealias Cave = Map<String, Valve>

fun String.toCaveEntry(): Pair<String, Valve> {
	val (f, s) = split(";").map { it.trim() }
	val name = f.drop(6).take(2)
	val rate = f.takeLastWhile { it.isDigit() }.toInt()
	val neighbors = s.split(" ").drop(4).map { it.trimEnd(',') }
	return name to Valve(rate, neighbors)
}

class CachedSolver(val cave: Cave) {
	private val cache = HashMap<List<Any>, Int>()

	fun solve(pos: String, time: Int, opened: PersistentSet<String>, more: Boolean = false): Int {
		val key = listOf(pos, time, opened, more)
		if (key in cache) return cache[key]!!
		val result = solverec(pos, time, opened, more)
		cache[key] = result
		return result
	}

	fun solverec(pos: String, time: Int, opened: PersistentSet<String>, more: Boolean = false): Int {
		if (time == 0) {
			if (more) return solve("AA", 26, opened)
			return 0
		}

		val valve = cave[pos]!!

		val score = valve.next.maxOf { solve(it, time - 1, opened, more) }

		return if (valve.rate == 0 || pos in opened) score
		else max(score, (time - 1) * valve.rate + solve(pos, time - 1, opened + pos, more))
	}
}

fun List<String>.toCave(): Cave =
	map { it.toCaveEntry() }.fold(hashMapOf()) { acc, cur -> acc[cur.first] = cur.second; acc }

@OptIn(ExperimentalTime::class)
fun main() {
	val input = File("input.txt").readLines().toCave()

	val part1 = measureTimedValue { CachedSolver(input).solve("AA", 30, persistentHashSetOf()) }
	println("part 1: ${part1.value} in ${part1.duration}")
	val part2 = measureTimedValue { CachedSolver(input).solve("AA", 26, persistentHashSetOf(), true) }
	println("part 2: ${part2.value} in ${part2.duration}")
}