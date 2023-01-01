from typing import List
from itertools import product

RANGE = 1, 10 + 1
FACE_NUM = 4


class Die:
    def __init__(self, faces: List[int]):
        assert all(RANGE[0] <= i < RANGE[1] for i in faces)
        self.faces = faces

    def can_beat(self, other: "Die"):
        beat_num = 0
        eq_num = 0
        for i in self.faces:
            for j in other.faces:
                if i > j:
                    beat_num += 1
                elif i == j:
                    eq_num += 1

        return beat_num * 2 > len(self.faces) * len(other.faces) - eq_num


for _ in range(int(input())):
    nums = [int(i) for i in input().split()]
    a = Die(nums[:FACE_NUM])
    b = Die(nums[FACE_NUM:])
    assert len(a.faces) == len(b.faces) == FACE_NUM

    # a should always be beating b
    if b.can_beat(a):
        a, b = b, a

    for c in product(range(*RANGE), repeat=FACE_NUM):
        die_c = Die(c)
        if die_c.can_beat(a) and b.can_beat(die_c):
            print('yes')
            break
    else:
        print('no')
