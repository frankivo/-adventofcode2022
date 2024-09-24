# --- Day 10: Pipe Maze ---
# https://adventofcode.com/2023/day/10

from data import data
from typing import Tuple


def part1(data: data) -> None:
    maze = dict(parse(data))
    start = [i for i, k in maze.items() if k == "S"][0]

    def walk(current: Tuple[int, int], visited: list = [], dist: int = 0) -> int:
        visited.append(current)

        options = []

        for a in [a for a in adj(maze, current) if a not in visited]:
            options.append(walk(a, visited, dist + 1))
        return max(options) if options else dist

    print(walk(start))


def parse(data: data) -> iter:
    for y, line in enumerate(data.getlines()):
        for x, char in enumerate(line):
            yield (x, y), char


def adj(maze: dict, source: Tuple[int, int]) -> iter:
    x, y = source

    # Above
    if maze[(x, y - 1)] in "|7F":
        yield (x, y - 1)
    # Below
    if maze[(x, y + 1)] in "|LJ":
        yield (x, y + 1)
    # Left
    if maze[(x - 1, y)] in "-LF":
        yield (x - 1, y)
    # Right
    if maze[(x + 1, y)] in "-J7":
        yield (x + 1, y)
