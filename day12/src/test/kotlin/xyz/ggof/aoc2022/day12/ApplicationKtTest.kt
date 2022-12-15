package xyz.ggof.aoc2022.day12

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import xyz.ggof.aoc2022.day12.*

class ApplicationKtTest : StringSpec({
	val input = """Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi""".trimIndent().lines()

	"should find shortest path to top" {

		val grid = input.toGrid()
		val start = grid.replace(s, a)
		val end = grid.replace(e, z)

		val sp = grid.shortestTo(start, end).shouldNotBeNull()

		println(sp.map { grid[it.x, it.y] })

		sp.steps shouldBe 31
	}

	"should find shortest path from top to first a" {
		val grid = input.toGrid()
		val end = grid.replace(e, z)
		grid.replace(s, a)

		val sp = grid.part2(end)

		sp shouldBe 29

	}
})
