package xyz.ggof.aoc2022.day18

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({
	val input = """2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5""".trimIndent().lines()


	"should build a set" {
		input.toSet() shouldBe setOf(
			Pos(2, 2, 2),
			Pos(1, 2, 2),
			Pos(3, 2, 2),
			Pos(2, 1, 2),
			Pos(2, 3, 2),
			Pos(2, 2, 1),
			Pos(2, 2, 3),
			Pos(2, 2, 4),
			Pos(2, 2, 6),
			Pos(1, 2, 5),
			Pos(3, 2, 5),
			Pos(2, 1, 5),
			Pos(2, 3, 5),
		)
	}

	"should calculate surface area" {
		input.toSet().surface() shouldBe 64
	}

	"should only count external surface" {
		input.toSet().externalSurface() shouldBe 58
	}
})
