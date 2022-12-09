package xyz.ggof.aoc2022.day7

import java.io.File

data class Dir(val name: String, val entries: List<Dir> = emptyList(), val files: Int = 0) {
	val size: Int by lazy { entries.sumOf { it.size } + files }
	val sizes: List<Int> by lazy { entries.flatMap { it.sizes } + size }

	fun minOver(value: Int) = sizes.filter { it >= value }.min()

	fun sumUnder(value: Int) = sizes.filter { it < value }.sum()
	data class Builder(val name: String, val entries: MutableList<Builder> = mutableListOf(), var files: Int = 0) {
		fun build(): Dir = Dir(name, entries.map { it.build() }, files)
	}
}


fun List<String>.buildTree(): Dir {
	val path: MutableList<Dir.Builder> = mutableListOf(Dir.Builder("/"))

	for (cmd in this) {
		val parts = cmd.split(" ")
		if (parts[0] == "$") {
			if (parts[1] == "ls") continue
			else if (parts[2] == "..") path.removeLast()
			else if (parts[2] == "/") path.removeAll(path.drop(1))
			else {
				val cwd = path.last()
				val nxt = cwd.entries.first { it.name == parts[2] }
				path.add(nxt)
			}
		} else {
			val cwd = path.last()
			if (parts[0] == "dir") cwd.entries.add(Dir.Builder(parts[1]))
			else cwd.files += parts[0].toInt()
		}
	}

	return path.first().build()
}

fun main() {
	val commands = File("input.txt").readLines()
	val tree = commands.buildTree()
	val part1 = tree.sumUnder(100_000)
	val neededSpace = 30_000_000 - (70_000_000 - tree.size)
	val part2 = tree.minOver(neededSpace)
	println("part 1: $part1")
	println("part 2: $part2")
}