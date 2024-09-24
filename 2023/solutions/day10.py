# --- Day 10: Pipe Maze ---
# https://adventofcode.com/2023/day/10

from data import data


def part1(data: data) -> None:
    maze, visited, todo = {}, set(), set()
    for x, line in enumerate(data.getlines()):
        for y, char in enumerate(line):
            adj = []
            if char in "-J7S":
                adj.append((x, y - 1))
            if char in "-FLS":
                adj.append((x, y + 1))
            if char in "|F7S":
                adj.append((x + 1, y))
            if char in "|LJS":
                adj.append((x - 1, y))
            if char == "S":
                visited.add((x, y))
                todo.add((x, y))
            maze[(x, y)] = adj

    print(maze[(1, 1)])

    steps = -1
    while todo:
        next = set()
        for x1, y1 in todo:
            for x2, y2 in maze[(x1, y1)]:
                if (x2, y2) not in visited and (x1, y1) in maze.get((x2, y2), []):
                    next.add((x2, y2))
                    visited.add((x2, y2))
        todo = next
        steps += 1
    print(steps)


# def part1(data: data) -> None:
#     grid = data.getlines()
#     graph = {}
#     for x, line in enumerate(grid):
#         for y, tile in enumerate(line):
#             adjacent = []
#             if tile in "-J7S":
#                 adjacent.append((x, y - 1))
#             if tile in "-FLS":
#                 adjacent.append((x, y + 1))
#             if tile in "|F7S":
#                 adjacent.append((x + 1, y))
#             if tile in "|LJS":
#                 adjacent.append((x - 1, y))
#             if tile == "S":
#                 visited = set([(x, y)])
#                 q = set([(x, y)])
#             graph[(x, y)] = adjacent

#     steps = -1
#     while q:
#         nxt = set()
#         for x1, y1 in q:
#             for x2, y2 in graph[(x1, y1)]:
#                 if (x2, y2) not in visited and (x1, y1) in graph.get((x2, y2), []):
#                     nxt.add((x2, y2))
#                     visited.add((x2, y2))
#         q = nxt
#         steps += 1

#     print(steps)
