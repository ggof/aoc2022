package xyz.ggof.aoc2022.day18

import kotlinx.collections.immutable.toImmutableSet
import java.io.File

data class Pos(val x: Int, val y: Int, val z: Int)

fun String.toPos() = split(",").map { it.toInt() }.let { (x, y, z) -> Pos(x, y, z) }

fun Pos.edges() = listOf(
	copy(x = x + 1),
	copy(x = x - 1),
	copy(y = y + 1),
	copy(y = y - 1),
	copy(z = z + 1),
	copy(z = z - 1),
)

fun List<String>.toSet(): Set<Pos> = map { it.toPos() }.toImmutableSet()

fun Set<Pos>.surface() = fold(0) { acc, pos -> acc + pos.edges().count { it !in this } }

fun Set<Pos>.externalSurface(): Int {
	val minX = minOf { it.x } - 1
	val minY = minOf { it.y } - 1
	val minZ = minOf { it.z } - 1
	val maxX = maxOf { it.x } + 1
	val maxY = maxOf { it.y } + 1
	val maxZ = maxOf { it.z } + 1

	var acc = 0

	val visited = mutableSetOf<Pos>()
	val q = mutableListOf(Pos(minX, minY, minZ))

	while (q.isNotEmpty()) {
		val p = q.removeLast()
		if (p in visited) continue

		visited.add(p)

		for (e in p.edges()) {
			if (e.x in minX..maxX && e.y in minY..maxY && e.z in minZ..maxZ) {
				if (e in this) acc++
				else q.add(e)
			}
		}
	}

	return acc
}

fun main() {
	val input = File("input.txt").readLines().toSet()

	val part1 = input.surface()
	println("part 1: $part1")
	val part2 = input.externalSurface()
	println("part 2: $part2")
}