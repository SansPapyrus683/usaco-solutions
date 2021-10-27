"""
2021 us open bronze
4 0
1 100 2 3 should output 2
4 1
1 100 2 3 should output 3
"""
from typing import List


def reachable_score(curr_scores: List[int], score: int, cite_num: int) -> bool:
    met_score = 0
    one_off = 0
    for s in curr_scores:
        if s >= score:
            met_score += 1
        elif s == score - 1:
            one_off += 1
    return met_score + min(cite_num, one_off) >= score


paper_num, citation_num = [int(i) for i in input().split()]
papers = [int(i) for i in input().split()]
assert paper_num == len(papers), f'the lengths {paper_num} and {len(papers)} should match'

lo = 0
hi = paper_num
valid = -1
while lo <= hi:
    mid = (lo + hi) // 2
    if reachable_score(papers, mid, citation_num):
        valid = mid
        lo = mid + 1
    else:
        hi = mid - 1
print(valid)
