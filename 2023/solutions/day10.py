# --- Day 10: Pipe Maze ---
# https://adventofcode.com/2023/day/10

from data import data


def part1(data: data) -> None:
    maze = dict(parse(data))
    start = [i for i, k in maze.items() if k == "S"][0]
    print(start)


def parse(data: data) -> iter:
    for y, line in enumerate(data.getlines()):
        for x, char in enumerate(line):
            yield (x, y), char
