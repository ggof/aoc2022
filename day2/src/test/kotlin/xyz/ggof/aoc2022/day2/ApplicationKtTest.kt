package xyz.ggof.aoc2022.day2

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({

	val input = """A Y
B X
C Z"""


	"should create a round" {
		"A Y".toRound() shouldBe Pair(Choice.Rock, Choice.Paper)
	}

	"should create all the rounds" {
		input.toRounds() shouldBe listOf(
			Choice.Rock to Choice.Paper,
			Choice.Paper to Choice.Rock,
			Choice.Scissors to Choice.Scissors
		)
	}

	"should calculate score for round" {
		"A Y".toRound().score() shouldBe 8
	}

	"should calculate total for all rounds" {
		input.toRounds().total() shouldBe 15
	}

	"should correctly calculate total for part 2" {
		input.toRoundsPart2().total() shouldBe 12
	}
})
