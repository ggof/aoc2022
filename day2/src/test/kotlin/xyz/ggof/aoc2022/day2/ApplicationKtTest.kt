package xyz.ggof.aoc2022.day2

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({

	val input = """A Y
B X
C Z""".split("\n").asSequence()

	"should create a round" {
		input.first().part1() shouldBe Pair(Choice.Rock, Choice.Paper)
	}

	"should create all the rounds" {
		input.toRounds(String::part1).toList() shouldBe listOf(
			Choice.Rock to Choice.Paper,
			Choice.Paper to Choice.Rock,
			Choice.Scissors to Choice.Scissors
		)
	}

	"should calculate score for round" {
		"A Y".part1().score() shouldBe 8
	}

	"should calculate total for all rounds" {
		input.toRounds(String::part1).total() shouldBe 15
	}

	"should correctly calculate total for part 2" {
		input.toRounds(String::part2).total() shouldBe 12
	}
})
