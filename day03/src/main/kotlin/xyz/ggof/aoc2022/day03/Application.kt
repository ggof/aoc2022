package xyz.ggof.aoc2022.day03

import java.io.File

val Char.elfCode get() = if (isUpperCase()) code - 64 + 26 else code - 96

fun String.toHalves() = chunked(length / 2)

fun List<Set<Char>>.intersection() = reduce { p, n -> p.intersect(n) }.first()

fun Sequence<String>.sumOfElfCodes() = sumOf { it.toHalves().map(String::toSet).intersection().elfCode }

fun Sequence<String>.sumOfElfBadges() = chunked(3).sumOf { it.map(String::toSet).intersection().elfCode }

fun main() {
	val input = File("input.txt")
	input.useLines { println("part 1: ${it.sumOfElfCodes()}") }
	input.useLines { println("part 2: ${it.sumOfElfBadges()}") }
}