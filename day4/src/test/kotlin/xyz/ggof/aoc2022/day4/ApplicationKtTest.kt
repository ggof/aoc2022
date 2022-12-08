package xyz.ggof.aoc2022.day4

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({
	val inputs = """2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8
	""".trimIndent().split("\n")

	"should correctly creates a pair" {
		"1-4,2-6".toPair() shouldBe ((1 to 4) to (2 to 6))
	}

	"should correctly check for full overlap" {
		inputs.map { full(it.toPair()) } shouldBe listOf(false, false, false, true, true, false)
	}

	"part 1 should calculate sum of fully contained" {
		inputs.overlapping(::full) shouldBe 2
	}

	"should correctly check for part overlap" {
		inputs.map { some(it.toPair()) } shouldBe listOf(false, false, true, true, true, true)
	}

	"part 2 should calculate sum of partly contained" {
		inputs.overlapping(::some) shouldBe 4
	}
})
