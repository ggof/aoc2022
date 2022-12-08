package xyz.ggof.aoc2022.day1

import java.io.File

typealias ElfList = List<String>
typealias ElfCalories = List<List<Int>>
typealias ElfCaloriesSum = List<Int>

fun String.byElf(): ElfList = split("\n\n").map { it.trim() }

fun ElfList.asCalories(): ElfCalories = map { it.split("\n").map(String::toInt) }

fun ElfCalories.sums(): ElfCaloriesSum = map { it.sum() }

fun main() {
	val input = File("day1/input.txt").readText()

	val sums = input.byElf().asCalories().sums()
	val max = sums.max()

	println("part 1: max amount of calories is $max")

	val top3max = sums.sorted().takeLast(3).sum()

	println("part 2: sum of top 3 wielders is $top3max")
}