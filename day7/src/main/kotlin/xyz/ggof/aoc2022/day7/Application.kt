package xyz.ggof.aoc2022.day7

import java.io.File

data class Dir(val name: String, private val entries: MutableList<Dir>) {
	private var files: Int = 0

	val size: Int get() = entries.sumOf { it.size } + files

	fun addFile(file: Int) {
		files += file
	}

	fun addDir(dir: Dir) = entries.add(dir)

	fun childWithName(name: String) = entries.first { it.name == name }

	private fun sizes(): List<Int> = entries.flatMap { it.sizes() } + size

	fun minOver(value: Int) = sizes().filter { it >= value }.min()

	fun sumUnder(value: Int) = sizes().filter { it < value }.sum()

	companion object {
		fun ofCommands(commands: List<String>): Dir {
			val path: MutableList<Dir> = mutableListOf(Dir("/", mutableListOf()))

			for (cmd in commands) {
				val parts = cmd.split(" ")
				when (parts.first()) {
					"$" -> handleCommand(path, parts)
					else -> handleAddPart(path, parts)
				}
			}

			return path.first()
		}

		private fun handleCommand(path: MutableList<Dir>, parts: List<String>) {
			if (parts[1] == "ls") return
			else if (parts[2] == "..") path.removeLast()
			else if (parts[2] == "/") path.removeAll(path.drop(1))
			else {
				val cwd = path.last()
				val nxt = cwd.childWithName(parts[2])
				path.add(nxt)
			}
		}

		private fun handleAddPart(path: MutableList<Dir>, parts: List<String>) {
			val cwd = path.last()
			if (parts[0] == "dir") cwd.addDir(Dir(parts[1], mutableListOf()))
			else cwd.addFile(parts[0].toInt())
		}
	}
}

fun main() {
	val commands = File("input.txt").readLines()
	val tree = Dir.ofCommands(commands)
	val part1 = tree.sumUnder(100_000)
	val neededSpace = 30_000_000 - (70_000_000 - tree.size)
	val part2 = tree.minOver(neededSpace)
	println("part 1: $part1")
	println("part 2: $part2")
}