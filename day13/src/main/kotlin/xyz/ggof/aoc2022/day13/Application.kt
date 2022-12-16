package xyz.ggof.aoc2022.day13

sealed interface G {
	fun compare(right: G): Boolean
}

data class L(val inner: List<G>) : G {

	override fun compare(right: G): Boolean = when (right) {
		is L -> {
			inner.zip(right.inner).firstOrNull { (l, r) -> l.compare(r) } != null

			if (inner.size < right.inner.size) true
			else if (inner.size > right.inner.size) false
			else inner.zip(right.inner).all { (l, r) -> l.compare(r) }
		}

		is V -> compare(L(listOf(right)))
	}
}

data class V(val inner: Int) : G {
	override fun compare(right: G): Boolean = when (right) {
		is L -> L(listOf(this)).compare(right)
		is V -> inner <= right.inner
	}
}

fun String.lParser(): Pair<L, Int> {
	var i = 1
	val inner = mutableListOf<G>()
	while (i <= lastIndex) {
		when (this[i]) {
			',' -> i++

			']' -> {
				i++
				break
			}

			'[' -> {
				val (l, n) = drop(i).lParser()
				inner.add(l)
				i += n
			}

			else -> {
				val (v, n) = (drop(i).vParser())
				inner.add(v)
				i += n
			}
		}
	}

	return L(inner) to i
}

fun String.vParser(): Pair<V, Int> {
	val d = takeWhile { it.isDigit() }
	return V(d.toInt()) to d.length
}

fun main() {
}