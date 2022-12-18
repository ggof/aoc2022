package xyz.ggof.aoc2022.day07

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationKtTest : StringSpec({
	val input = """$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k""".split("\n")

	"should calculate output" {
		Dir.ofCommands(input).sumUnder(100_000) shouldBe 95_437
	}

	"should calculate smallest over treshold" {
		Dir.ofCommands(input).minOver(8_381_165) shouldBe 24_933_642
	}
})
