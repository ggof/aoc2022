package xyz.ggof.aoc2022.day15

import java.io.File
import kotlin.math.absoluteValue
import kotlin.time.*

data class Pos(val x: Int, val y: Int)

data class Sensor(val x: Int, val y: Int, val m: Int) {
	val m1 = m + 1
}

fun manhattan(sx: Int, sy: Int, bx: Int, by: Int): Int = (sx - bx).absoluteValue + (sy - by).absoluteValue

fun Sensor.atDepth(depth: Int): Int = m - (y - depth).absoluteValue

fun Sensor.unusableAt(depth: Int): List<Pos> {
	val rest = atDepth(depth)

	return if (rest < 1) emptyList()
	else (-rest..rest).map { Pos(x + it, depth) }
}

fun List<Pair<Sensor, Pos>>.wrapPair(): Pair<List<Sensor>, List<Pos>> =
	fold(mutableListOf<Sensor>() to mutableListOf<Pos>()) { (ss, pp), (s, p) ->
		ss.add(s)
		pp.add(p)
		ss to pp
	}.let { (f, s) -> f.distinct() to s.distinct() }

fun List<Sensor>.allUnusableAt(depth: Int): Int {
	val allUnusable = map { it.unusableAt(depth) }
	val uniqUnusable = allUnusable.flatten().distinct()
	return uniqUnusable.size
}

fun Sensor.inManhattanDistance(p: Pos) = manhattan(x, y, p.x, p.y) <= m

fun List<Pos>.atDepth(depth: Int): Int = count { it.y == depth }

fun Pair<List<Sensor>, List<Pos>>.part1(depth: Int): Int = first.allUnusableAt(depth) - second.atDepth(depth)

fun List<Sensor>.outside(): List<Pos> = fold(mutableListOf()) { lst, s ->
	for (m in 0..s.m1) {
		lst.add(Pos((s.x - s.m1) + m, (s.y) + m)) // top left
		lst.add(Pos(s.x + m, (s.y + s.m1) - m)) // top right
		lst.add(Pos((s.x - s.m1) + m, (s.y) - m)) // bottom left
		lst.add(Pos(s.x + m, (s.y - s.m1) + m)) // bottom right
	}
	lst
}

fun List<Pos>.inside(size: Int) = filter { it.x in 0..size && it.y in 0..size }

fun Pos.tuning() = 4000000L * x + y

fun Pair<List<Sensor>, List<Pos>>.part2(size: Int): Long = first
	.outside()
	.inside(size)
	.first { p -> first.none { it.inManhattanDistance(p) } }.tuning()

fun String.toPos(drop: Int) = drop(drop).split(",").map { it.trim().drop(2).toInt() }

fun String.toSensor(): Pair<Sensor, Pos> {
	val (sensorString, beaconString) = split(":")
	val (sx, sy) = sensorString.toPos(10)
	val (bx, by) = beaconString.toPos(22)
	val m = manhattan(sx, sy, bx, by)

	return Sensor(sx, sy, m) to Pos(bx, by)
}

fun List<String>.toInput() = map { it.toSensor() }.wrapPair()

@OptIn(ExperimentalTime::class)
fun main() {
	val input = File("input.txt").readLines().toInput()
	val part1 = measureTimedValue { input.part1(2000000) }
	println("part 1: ${part1.value} in ${part1.duration.inWholeMilliseconds}ms")
	val part2 = measureTimedValue { input.part2(4000000) }
	println("part 2: ${part2.value} in ${part2.duration.inWholeMilliseconds}ms")
}