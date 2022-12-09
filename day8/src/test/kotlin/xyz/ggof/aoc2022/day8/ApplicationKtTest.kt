package xyz.ggof.aoc2022.day8

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ApplicationKtTest : StringSpec({
	val input = """30373
25512
65332
33549
35390""".split("\n")
	"should split in grid" {
		val grid = input.toGrid()
		grid shouldBe listOf(
			listOf(3, 0, 3, 7, 3),
			listOf(2, 5, 5, 1, 2),
			listOf(6, 5, 3, 3, 2),
			listOf(3, 3, 5, 4, 9),
			listOf(3, 5, 3, 9, 0),
		)
	}

	"should correctly validate if a tree is visible" {
		val grid = input.toGrid()

		grid.visible(1, 1) shouldContainExactlyInAnyOrder  listOf(Direction.North, Direction.West)
		grid.visible(0, 0) shouldContainExactlyInAnyOrder  listOf(Direction.North, Direction.West)
		grid.visible(0, 2) shouldContainExactlyInAnyOrder  listOf(Direction.North, Direction.East, Direction.South, Direction.West)
		grid.visible(2, 2) shouldBe emptyList()
		grid.visible(3, 2) shouldContainExactlyInAnyOrder listOf(Direction.East)

	}

	"should correctly calculate scenic score" {
		val grid = input.toGrid()

		grid.scenicScore(2, 1) shouldBe 4
	}
})
