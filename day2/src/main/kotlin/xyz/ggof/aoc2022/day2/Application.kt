package xyz.ggof.aoc2022.day2

import arrow.core.tail
import xyz.ggof.aoc2022.day2.Choice.*
import xyz.ggof.aoc2022.day2.Result.*
import java.io.File


enum class Result(val score: Int) {
	Lose(0),
	Draw(3),
	Win(6);

	companion object {
		fun ofString(s: String): Result = when (s) {
			"X" -> Lose
			"Y" -> Draw
			"Z" -> Win
			else -> error("invalid entry for Result: $s")
		}
	}
}

enum class Choice(val score: Int) {
	Rock(1),
	Paper(2),
	Scissors(3);

	companion object {
		fun ofString(s: String) = when (s) {
			"A" -> Rock
			"X" -> Rock
			"B" -> Paper
			"Y" -> Paper
			"C" -> Scissors
			"Z" -> Scissors
			else -> error("invalid entry for Choice: $s")
		}

	}
}

object CheatSheet {
	private val board = arrayOf(
		Rock.score + Draw.score, Paper.score + Win.score, Scissors.score + Lose.score,
		Rock.score + Lose.score, Paper.score + Draw.score, Scissors.score + Win.score,
		Rock.score + Win.score, Paper.score + Lose.score, Scissors.score + Draw.score,
	)

	private val playerMove = arrayOf(
		Scissors, Rock, Paper,
		Rock, Paper, Scissors,
		Paper, Scissors, Rock
	)

	operator fun get(op: Choice, me: Choice): Int = board[(op.ordinal) * 3 + (me.ordinal)]
	operator fun get(op: Choice, re: Result): Choice = playerMove[(op.ordinal) * 3 + (re.ordinal)]
}

typealias Round = Pair<Choice, Choice>

fun Round.score() = CheatSheet[first, second]

fun part1(s: String): Round {
	val (op, me) = s.split(" ").map(Choice::ofString)
	return op to me
}

fun part2(s: String): Round {
	val (fst, snd) = s.split(" ")
	val op = Choice.ofString(fst)
	val me = Result.ofString(snd)
	return op to CheatSheet[op, me]
}

fun String.toRounds(mapper: (String) -> Round): List<Round> = split("\n").map { mapper(it) }

fun List<Round>.total(): Int = sumOf { it.score() }

fun main() {
	val input = File("input.txt").readText()

	val total1 = input.toRounds(::part1).total()
	println("part 1: total is $total1")

	val total2 = input.toRounds(::part2).total()
	println("total for part 2 is $total2")
}