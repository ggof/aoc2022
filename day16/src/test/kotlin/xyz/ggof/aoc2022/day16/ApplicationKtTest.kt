package xyz.ggof.aoc2022.day16

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({
	val text = """
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
	""".trimIndent()

	"should find path with most pressure release" {
		val first = text.startIndex()
		val input = text.replaceNames().toCave()
		val pr = CachedSolver(input, 30).solve(first, 30, 0UL)
		pr shouldBe 1651
	}

	"should find double path with most pressure release" {
		val first = text.startIndex()
		val input = text.replaceNames().toCave()
		val pr = CachedSolver(input, first).solve(first, 26, 0UL, true)
		pr shouldBe 1707

	}
})
