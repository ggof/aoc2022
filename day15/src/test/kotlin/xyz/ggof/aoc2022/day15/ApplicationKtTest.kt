package xyz.ggof.aoc2022.day15

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({
	val input = """
Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3
	""".trimIndent().lines()

	"should build all sensors" {
		input.first().toSensorAndBeacon() shouldBe (Sensor(2, 18, 7) to Pos(-2, 15))
		input.last().toSensorAndBeacon() shouldBe (Sensor(20, 1, 7) to Pos(15, 3))
	}

	"should find unusable positions" {
		val unusable0 = input.toInput().first.first().unusableAt(10)
		val unusable7 = input.toInput().first[6].unusableAt(10)

		unusable0.size shouldBe 0
		unusable7.size shouldBe 13
	}

	"should count all unusable positions" {
		input.toInput().part1(10) shouldBe 26
	}
})
