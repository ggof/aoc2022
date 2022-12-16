package xyz.ggof.aoc2022.day14

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import xyz.ggof.aoc2022.day14.*

class ApplicationKtTest : StringSpec({
	val input = """
498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9
	""".trimIndent().lines()

	"should transform to list of pos" {
		input.first().toPoints() shouldBe listOf(Pos(498, 4), Pos(498, 6), Pos(496, 6))
	}

	"should fill lines completly" {
		input.first().toPoints().fill() shouldBe listOf(
			Pos(498, 4),
			Pos(498, 5),
			Pos(498, 6),
			Pos(497, 6),
			Pos(496, 6)
		)
	}

	"should correctly calculate max amount of sand" {
		val points = input.flatMap { it.toPoints().fill() }.toMutableSet()
		val max = points.maxSand(Pos(500, 0))
		max shouldBe 24
	}
})
