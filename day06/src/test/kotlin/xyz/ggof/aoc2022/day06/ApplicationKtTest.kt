package xyz.ggof.aoc2022.day06

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({

	"should correctly identify strings with all different chars" {
		"abcdefghijklmnop".isMarker() shouldBe true
		"abcdefghijklmnopj".isMarker() shouldBe false
		"abjslqo".isMarker() shouldBe true
	}

	"should get the correct part 1" {
		"bvwbjplbgvbhsrlpgdmjqwftvncz".part1() shouldBe 5
		"nppdvjthqldpwncqszvftbrmjlhg".part1() shouldBe 6
		"nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".part1() shouldBe 10
		"zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".part1() shouldBe 11
	}

	"should get the correct part 2" {
		"bvwbjplbgvbhsrlpgdmjqwftvncz".part2() shouldBe 23
		"nppdvjthqldpwncqszvftbrmjlhg".part2() shouldBe 23
		"nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".part2() shouldBe 29
		"zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".part2() shouldBe 26
	}
})
