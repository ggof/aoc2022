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

private val board = listOf(
	Rock.score + Draw.score, Paper.score + Win.score, Scissors.score + Lose.score,
	Rock.score + Lose.score, Paper.score + Draw.score, Scissors.score + Win.score,
	Rock.score + Win.score, Paper.score + Lose.score, Scissors.score + Draw.score,
)

private val playerMove = listOf(
	Scissors, Rock, Paper,
	Rock, Paper, Scissors,
	Paper, Scissors, Rock
)

infix fun Result.from(op: Choice) = playerMove[op.ordinal * 3 + ordinal]
infix fun Choice.against(other: Choice) = board[ordinal * 3 + other.ordinal]

typealias Round = Pair<Choice, Choice>

fun Round.score() = first against second

fun String.part1(): Round {
	val (op, me) = split(" ").map(Choice::ofString)
	return op to me
}

fun String.part2(): Round {
	val (fst, snd) = split(" ")
	val op = Choice.ofString(fst)
	val re = Result.ofString(snd)
	return op to (re from op)
}

fun Iterable<String>.toRounds(mapper: (String) -> Round): Iterable<Round> = map { mapper(it) }

fun Iterable<Round>.total(): Int = sumOf { it.score() }

fun main() {
	val input = File("input.txt").readLines()

	val total1 = input.toRounds(String::part1).total()
	println("part 1: total is $total1")

	val total2 = input.toRounds(String::part2).total()
	println("part 2: total is $total2")
}