package xyz.ggof.aoc2022.day1

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({
	val input = """1000
2000
3000

4000

5000
6000

7000
8000
9000

10000"""

	"should split by elf" {
		val splitted = input.byElf()

		splitted shouldHaveSize 5
		splitted[0] shouldBe "1000\n2000\n3000"
		splitted[1] shouldBe "4000"
		splitted[2] shouldBe "5000\n6000"
		splitted[3] shouldBe "7000\n8000\n9000"
		splitted[4] shouldBe "10000"
	}

	"should split each calorie count" {
		val caloriesByElf = input.byElf().asCalories()

		caloriesByElf shouldHaveSize 5
		caloriesByElf[0] shouldBe listOf(1_000, 2_000, 3_000)
		caloriesByElf[1] shouldBe listOf(4_000)
		caloriesByElf[2] shouldBe listOf(5_000, 6_000)
		caloriesByElf[3] shouldBe listOf(7_000, 8_000, 9_000)
		caloriesByElf[4] shouldBe listOf(10_000)
	}

	"should correctly give sum of calories" {
		val sumByElf = input.byElf().asCalories().sums()

		sumByElf shouldHaveSize 5
		sumByElf[0] shouldBe 6_000
		sumByElf[1] shouldBe 4_000
		sumByElf[2] shouldBe 11_000
		sumByElf[3] shouldBe 24_000
		sumByElf[4] shouldBe 10_000
	}

	"should get the highest amount of calories" {
		val max = input.byElf().asCalories().sums().max()

		max shouldBe 24_000
	}
})
