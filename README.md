# AOC 2022

This is my take on the Advent of Code puzzles, for the year 2022. All days are solved in Kotlin. This repository is a
multimodule gradle project, where each day is a project. Every day also has unit tests to validate most parts of the
solution with the provided example. This way, I can be sure the value I submit is the right one on the first try.

## How to run
simply use `./gradlew test` to run all tests, or `./gradlew run` to run all solutions. If you wish to run a single solution, you can always specify it before the command. For example, running only the tests of the project `day1` would result in the following command: `./gradlew :day1:test`.
