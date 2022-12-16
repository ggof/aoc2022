package xyz.ggof.aoc2022.day13

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({
	val input = """
[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]""".trimIndent().split("\n\n").map { it.split("\n") }

	"should transform to L and L" {
		val f = input[1].first()
		val (lst, _) = f.lParser()

		lst shouldBe L(listOf(L(listOf(V(1))), L(listOf(V(2), V(3), V(4)))))
	}

	"should figure out which one is valid" {
		val (ls, rs) = input.first().map { it.lParser().first }

		ls.compare(rs) shouldBe true
	}

	"should get indexes of only valid ones" {
		val indexes =
			input
				.asSequence()
				.map { it.map { l -> l.lParser().first } }
				.map { (l, r) -> l.compare(r) }
				.withIndex()
				.filter { it.value }.map { it.index }
				.toList()

		indexes shouldBe listOf(true, true, false, true, false, true, false, false)


	}
})
