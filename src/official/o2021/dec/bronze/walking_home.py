"""2021 dec bronze"""
from functools import lru_cache


for _ in range(int(input())):
    side, change_amt = [int(i) for i in input().split()]
    blocked = [[c == "H" for c in input()] for _ in range(side)]
    assert all(len(r) == side for r in blocked)

    @lru_cache(maxsize=None)  # rare instance of something from advent of code here
    def path_num(r: int, c: int, runs: int, last_down: bool):
        if r == c == side - 1:
            return 1
        if runs == change_amt + 1:
            return 0

        total = 0
        if last_down:  # was the last move down?
            for nc in range(c + 1, side):  # move to the right
                if blocked[r][nc]:
                    break
                total += path_num(r, nc, runs + 1, False)
        else:
            for nr in range(r + 1, side):  # move to the left
                if blocked[nr][c]:
                    break
                total += path_num(nr, c, runs + 1, True)
        return total

    total_paths = path_num(0, 0, 0, False) + path_num(0, 0, 0, True)
    print(total_paths)
