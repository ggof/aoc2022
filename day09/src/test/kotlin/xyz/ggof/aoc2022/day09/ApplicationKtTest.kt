package xyz.ggof.aoc2022.day09

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldStartWith
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({
	val input = """R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
	""".trimIndent().lines()

	"should give a list of all visited positions" {
		val positions = input.unfold().allPositions(2)
		positions shouldStartWith listOf(
			Rope(Position(0, 0), listOf(Position(0, 0))),
			Rope(Position(1, 0), listOf(Position(0, 0))),
			Rope(Position(2, 0), listOf(Position(1, 0))),
			Rope(Position(3, 0), listOf(Position(2, 0))),
			Rope(Position(4, 0), listOf(Position(3, 0))),
			Rope(Position(4, 1), listOf(Position(3, 0))),
			Rope(Position(4, 2), listOf(Position(4, 1))),
			Rope(Position(4, 3), listOf(Position(4, 2))),
			Rope(Position(4, 4), listOf(Position(4, 3))),
			Rope(Position(3, 4), listOf(Position(4, 3))),
		)
	}

	"should keep only second" {
		val tails = input.unfold().allPositions(2).distinctTailPositions()
		tails.size shouldBe 13
	}

	"should print something" {
		input.unfold().allPositions(10).map(::println)
	}
})
