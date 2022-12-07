package xyz.ggof.aoc2022.day2

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

typealias Round = Pair<Choice, Choice>

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

fun Round.score() = CheatSheet[first, second]

fun String.toRound(): Round {
	val splitted = split(" ").map(Choice::ofString)
	return splitted[0] to splitted[1]
}

fun String.toRoundPart2(): Round {
	val (fst, snd) = split(" ")
	val op = Choice.ofString(fst)
	val me = Result.ofString(snd)
	return op to CheatSheet[op, me]
}

fun String.toRounds(): List<Round> = split("\n").map { it.toRound() }
fun String.toRoundsPart2(): List<Round> = split("\n").map { it.toRoundPart2() }

fun List<Round>.total(): Int = sumOf { it.score() }

fun main() {
	println("hello world!")
	val input = File("input.txt").readText()

	val total = input.toRounds().total()
	println("part 1: total is $total")

	val total2 = input.toRoundsPart2().total()
	println("total for part 2 is $total2")
}