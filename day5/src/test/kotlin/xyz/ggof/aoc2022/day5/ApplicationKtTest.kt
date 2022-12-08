package xyz.ggof.aoc2022.day5

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({
	val input = """    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
"""

	"should split in towers and moves list" {
		val (t, m) = input.toInputs()
		t shouldBe listOf(listOf('Z', 'N'), listOf('M', 'C', 'D'), listOf('P'))
		m shouldBe listOf(listOf(1, 2, 1), listOf(3, 1, 3), listOf(2, 2, 1), listOf(1, 1, 2))
	}

	"should play moves correctly" {
		val (t, m) = input.toInputs()
		t.applyMoves(m, List<Char>::move9000).topBoxes()  shouldBe "CMZ"
	}

	"should play 2nd part moves correctly" {
		val (t, m) = input.toInputs()
		t.applyMoves(m, List<Char>::move9001).topBoxes()  shouldBe "MCD"
	}
})
