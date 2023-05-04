"""
ID: kevinsh4
TASK: frameup
LANG: PYTHON3
"""
from dataclasses import dataclass


@dataclass
class FrameBounds:
    min_r: int
    max_r: int
    min_c: int
    max_c: int


def all_topo_orders(graph: dict[str, set[str]]) -> list[str]:
    rev_graph = {n: set() for n in graph}
    for n in graph:
        for to in graph[n]:
            rev_graph[to].add(n)
    orders = []
    curr_order = []

    def gen_order():
        nonlocal orders, curr_order
        if len(curr_order) == len(rev_graph):
            orders.append(''.join(curr_order))
            return

        for poss, prereqs in rev_graph.items():
            if prereqs or poss in curr_order:
                continue

            add_back = []
            for o in rev_graph:
                if poss in rev_graph[o]:
                    rev_graph[o].remove(poss)
                    add_back.append(o)

            curr_order.append(poss)
            gen_order()
            curr_order.pop()

            for o in add_back:
                rev_graph[o].add(poss)

    gen_order()
    return orders


with open('frameup.in') as read:
    row_num, col_num = [int(i) for i in read.readline().split()]
    arr = []
    for _ in range(row_num):
        # assumes the input abides by the rules of the problem
        arr.append(read.readline().strip())

frames = {}
for r in range(row_num):
    for c in range(col_num):
        cell = arr[r][c]
        if cell != '.':
            if cell not in frames:
                frames[cell] = FrameBounds(r, r, c, c)
            frames[cell].min_r = min(frames[cell].min_r, r)
            frames[cell].max_r = max(frames[cell].max_r, r)
            frames[cell].min_c = min(frames[cell].min_c, c)
            frames[cell].max_c = max(frames[cell].max_c, c)

on_top = {f: set() for f in frames}
for f, b in frames.items():
    for r in range(b.min_r, b.max_r + 1):
        on_top[f].add(arr[r][b.min_c])
        on_top[f].add(arr[r][b.max_c])
    for c in range(b.min_c, b.max_c + 1):
        on_top[f].add(arr[b.min_r][c])
        on_top[f].add(arr[b.max_r][c])
    on_top[f].remove(f)

valid = sorted(all_topo_orders(on_top))
print('\n'.join(valid), file=open('frameup.out', 'w'))
