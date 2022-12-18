package xyz.ggof.aoc2022.day16

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.maps.shouldContainAll
import io.kotest.matchers.shouldBe
import xyz.ggof.aoc2022.day16.*

class ApplicationKtTest : StringSpec({
	val input = """
Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II
	""".trimIndent().lines()

	"should create a  map" {
		input.toCave() shouldContainAll mapOf(
			"AA" to Valve(0, listOf("DD", "II", "BB")),
			"BB" to Valve(13, listOf("CC", "AA")),
			"CC" to Valve(2, listOf("DD", "BB")),
			"DD" to Valve(20, listOf("CC", "AA", "EE")),
		)
	}

	"should find every possible way" {

		val everyPath = CachedSolver(input.toCave()).solve("AA", 30, emptySet())
		everyPath shouldBe 1651
	}
})
