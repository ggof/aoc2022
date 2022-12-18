package xyz.ggof.aoc2022.day17

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import xyz.ggof.aoc2022.day17.*

class ApplicationKtTest : StringSpec({
	val input = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>".toList()

	"should play 2022 moves " {
		val game = Game(input)
		game.play(2022) shouldBe 3068
	}

})
