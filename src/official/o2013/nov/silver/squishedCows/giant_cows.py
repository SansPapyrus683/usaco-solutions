"""
although this python implementation times out for like half the test cases,
i'm still going to leave it here lol because why not
"""
from bisect import bisect_left, bisect_right


class MaxSegTree:
    def __init__(self, init_len):
        self.arr_size = init_len
        self.size = 1
        while self.size < self.arr_size:
            self.size *= 2
        self.segtree = [0 for _ in range(self.size * 2)]

    def set(self, index: int, val: int, curr_node: int = 0, left: int = 0, right: int = -1):
        right = self.size if right == -1 else right
        if right - left == 1:
            self.segtree[curr_node] = val
        else:
            mid = (left + right) // 2
            if index < mid:
                self.set(index, val, 2 * curr_node + 1, left, mid)
            else:
                self.set(index, val, 2 * curr_node + 2, mid, right)
            self.segtree[curr_node] = max(self.segtree[2 * curr_node + 1], self.segtree[2 * curr_node + 2])

    # start is inclusive, end isn't
    def max(self, start: int, end: int, curr_node: int = 0, left: int = 0, right: int = -1):
        right = self.size if right == -1 else right
        if right <= start or end <= left:
            return -float('inf')
        if start <= left and right <= end:
            return self.segtree[curr_node]
        mid = (left + right) // 2
        left = self.max(start, end, 2 * curr_node + 1, left, mid)
        right = self.max(start, end, 2 * curr_node + 2, mid, right)
        return max(left, right)


with open('crowded.in') as read:
    cow_num, dist_threshold = [int(i) for i in read.readline().split()]
    cows = [[int(i) for i in read.readline().split()] for _ in range(cow_num)]  # position and height
cows.sort()
positions = [c[0] for c in cows]
heights = [c[1] for c in cows]

seg_tree = MaxSegTree(len(heights))
for v, h in enumerate(heights):
    seg_tree.set(v, h)

total_crowded = 0
for i in range(1, cow_num - 1):  # the first cow and last cow don't really matter
    farthest_left = bisect_left(positions, positions[i] - dist_threshold)
    farthest_right = bisect_right(positions, positions[i] + dist_threshold) - 1
    total_crowded += seg_tree.max(i + 1, farthest_right + 1) >= heights[i] * 2 and \
                     seg_tree.max(farthest_left, i) >= heights[i] * 2

print(total_crowded)
print(total_crowded, file=open('crowded.out', 'w'))
