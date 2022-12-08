package xyz.ggof.aoc2022.day3

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({

	val input = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
""".trimIndent()

	val line = "vJrwpWtwJgWrhcsFMMfFFhFp"
	val triple = input.split("\n").take(3)

	"should split in half" {
		line.toHalves() shouldBe listOf("vJrwpWtwJgWr", "hcsFMMfFFhFp")
	}

	"should find the only identical char" {
		line.toHalves().map(String::toSet).intersection() shouldBe 'p'
	}

	"should give correct elf code" {
		line.toHalves().map(String::toSet).intersection().elfCode shouldBe ('p'.code - 96)
	}

	"sum of elf codes should be valid" {
		input.split("\n").asSequence().sumOfElfCodes() shouldBe 157
	}

	"intersection of first 3 lines should be r" {
		triple.map { it.toSet() }.intersection() shouldBe 'r'
	}

	"sum of elf badges should be 70" {
		input.split("\n").asSequence().sumOfElfBadges() shouldBe 70
	}
})
