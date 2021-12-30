# 2021 dec gold
from typing import List
from collections import defaultdict
from enum import Enum


# i did code a java version but it was too messy
def still_unbroken(scans: List[List[int]], bracelets: int) -> bool:
    # if you go [relation] of the first one you'll get to the second one
    Relation = Enum('Relation', 'IN OUT LEFT RIGHT')

    # input validation, this will never be set off but it gives me closure
    for s in scans:
        occ_num = defaultdict(int)
        for b in s:
            occ_num[b] += 1
        # they said a bracelet would only intersect a line 2 or 0 times
        if (any(b >= bracelets for b in occ_num)
                or not set(occ_num.values()).issubset({2})):
            return False

    state = [0 for _ in range(bracelets)]
    for s in scans:
        for b in range(bracelets):
            if b in s:
                if state[b] == 2:  # bruh you were supposed to be gone
                    return False
                elif state[b] == 0:  # oh, the thing's here!
                    state[b] += 1
            else:
                if state[b] == 1:  # alright, it should be gone
                    state[b] += 1

    rel = [[None for _ in range(bracelets)] for _ in range(bracelets)]
    for s in scans:
        intervals = []
        for b in range(bracelets):
            if b in s:
                start = s.index(b)
                intervals.append([start, s.index(b, start + 1), b])
        intervals.sort()

        for i in range(len(intervals)):
            il, ir, ib = intervals[i]  # left end, right end, and color for i
            # note that j is gonna be to the right of i bc we sorted the intervals
            for j in range(i + 1, len(intervals)):
                jl, jr, jb = intervals[j]  # same for j

                # well j just all the way to the right of i ig
                if ir < jl:
                    # ... last time you said i was the one to the right...
                    if rel[ib][jb] is not None and rel[ib][jb] != Relation.RIGHT:
                        return False
                    rel[ib][jb] = Relation.RIGHT
                    rel[jb][ib] = Relation.LEFT
                else:
                    # ir < jr for testing if it's like [(]) (an INVALID config)
                    if (ir < jr or
                            (rel[ib][jb] is not None
                             and rel[ib][jb] != Relation.IN)):
                        return False
                    rel[ib][jb] = Relation.IN
                    rel[jb][ib] = Relation.OUT

    # an inside bracelet needs its outside bracelet to appear but not vice versa
    for s in scans:
        for b1 in range(bracelet_num):
            if b1 not in s:
                continue
            for b2 in range(bracelet_num):
                if b2 not in s and rel[b2][b1] == Relation.IN:
                    return False

    return True


for _ in range(int(input())):
    input()
    bracelet_num, line_num = [int(i) for i in input().split()]
    lines = []
    for _ in range(line_num):
        line = [int(i) for i in input().split()]
        assert line[0] == len(line[1:])
        lines.append([b - 1 for b in line[1:]])
    print('YES' if still_unbroken(lines, bracelet_num) else 'NO')
