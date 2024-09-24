# --- Day 10: Pipe Maze ---
# https://adventofcode.com/2023/day/10

from data import data
from typing import Tuple
import sys


def part1(data: data) -> None:
    maze = dict(parse(data))
    start = [i for i, k in maze.items() if k == "S"][0]

    def walk(current: Tuple[int, int], visited: dict = {}, dist: int = 0) -> int:
        visited.update({current: dist})

        options = []

        for a in adj(maze, current):
            if a not in visited or visited.get(a) > dist:
                options.append(walk(a, visited, dist + 1))
        return min(options) if options else dist

    print(walk(start))


def parse(data: data) -> iter:
    for y, line in enumerate(data.getlines()):
        for x, char in enumerate(line):
            yield (x, y), char


def adj(maze: dict, source: Tuple[int, int]) -> iter:
    x, y = source

    # Above
    if maze.get((x, y - 1), "") in "|7F":
        yield (x, y - 1)
    # Below
    if maze.get((x, y + 1), "") in "|LJ":
        yield (x, y + 1)
    # Left
    if maze.get((x - 1, y), "") in "-LF":
        yield (x - 1, y)
    # Right
    if maze.get((x + 1, y), "") in "-J7":
        yield (x + 1, y)
