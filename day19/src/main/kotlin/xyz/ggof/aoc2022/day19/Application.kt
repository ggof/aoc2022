package xyz.ggof.aoc2022.day19

import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

const val GEODE = 3

data class Blueprint(
	val id: Int,
	val prices: List<List<Int>>,
	val maxes: List<Int>
)

fun String.toBlueprint(): Blueprint {
	val matches = """\d+""".toRegex().findAll(this).map { it.value.toInt() }.toList()
	val oreRobot = listOf(matches[1], 0, 0)
	val clayRobot = listOf(matches[2], 0, 0)
	val obsidianRobot = listOf(matches[3], matches[4], 0)
	val geodeRobot = listOf(matches[5], 0, matches[6])
	return Blueprint(
		matches[0],
		listOf(oreRobot, clayRobot, obsidianRobot, geodeRobot),
		listOf(
			maxOf(matches[1], matches[2], matches[3], matches[5]),
			matches[4],
			matches[6]
		)
	)
}

fun List<String>.toBlueprints() = map { it.toBlueprint() }

data class State(val time: Int, val bots: List<Int>, val ores: List<Int>)

fun State.isMissingRobots(l: List<IndexedValue<Int>>) = l.any { (i, _) -> bots[i] == 0 }

fun State.waitTime(p: IndexedValue<Int>) = ((p.value - ores[p.index]) + bots[p.index] - 1) / bots[p.index]

fun Blueprint.dfs(state: State, states: MutableMap<State, Int>): Int {
	if (state.time == 0) return state.ores[GEODE]

	states[state]?.let { return it }

	var max = state.ores[GEODE] + (state.bots[GEODE] * state.time)

	for ((bot, recipe) in prices.withIndex()) {
		if (bot != GEODE && state.bots[bot] == maxes[bot]) continue
		val requiredMaterials = recipe.withIndex().filter { (_, c) -> c > 0 }

		if (state.isMissingRobots(requiredMaterials)) continue

		val wait = requiredMaterials.maxOf(state::waitTime)

		val nTime = state.time - wait - 1
		if (nTime <= 0) continue
		val nBots = state.bots.toMutableList()
		val nOres = state.ores.toMutableList()

		nBots[bot]++
		for (mat in 0..3) nOres[mat] += state.bots[mat] * (wait + 1)
		for ((mat, cost) in requiredMaterials) nOres[mat] -= cost
		for (mat in 0..2) nOres[mat] = minOf(nOres[mat], maxes[mat] * nTime)

		max = maxOf(max, dfs(State(nTime, nBots, nOres), states))
	}

	states[state] = max
	return max
}

fun List<Blueprint>.part1() = fold(0) { acc, cur ->
	acc + (cur.dfs(State(24, listOf(1, 0, 0, 0), listOf(0, 0, 0, 0)), mutableMapOf()) * cur.id)
}

fun List<Blueprint>.part2() = take(3).fold(1) { acc, cur ->
	acc * (cur.dfs(State(32, listOf(1, 0, 0, 0), listOf(0, 0, 0, 0)), mutableMapOf()))
}

@OptIn(ExperimentalTime::class)
fun main() {
	val input = File("input.txt").readLines().toBlueprints()

	val part1 = measureTimedValue { input.part1() }
	println("part 1: ${part1.value} in ${part1.duration}")

	val part2 = measureTimedValue { input.part2() }
	println("part 2: ${part2.value} in ${part2.duration}")
}