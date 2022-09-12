from typing import Union


def min_steps(s: int, e: int) -> int:
    def pref_diff(pref: int, big: int):
        start = big.bit_length() - pref.bit_length()
        assert start >= 0
        mask = ((1 << pref.bit_length()) - 1) << start
        return ((big & mask) >> start) - pref

    def mul_add_steps(s_: int, e_: int) -> Union[float, int]:
        if s_ > e_:
            return float('inf')

        if pref_diff(s_, e_) < 0:
            target_len = s_.bit_length() + 1
            start = e_.bit_length() - target_len
            mask = ((1 << target_len) - 1) << start
            target = (e & mask) >> start

            remaining_len = e_.bit_length() - s_.bit_length() - 1
            remaining = e_ & ((1 << remaining_len) - 1)

            return (target - s_) + remaining_len + bin(remaining).count('1')

        remaining_len = e_.bit_length() - s_.bit_length()
        remaining = e_ & ((1 << remaining_len) - 1)
        return pref_diff(s_, e_) + remaining_len + bin(remaining).count('1')

    assert s > 0 and e > 0

    steps = 0
    best_steps = mul_add_steps(s, e)
    while s:  # while True should work as well?
        if s % 2 == 1:
            s += 1
        else:
            s //= 2
        steps += 1

        best_steps = min(best_steps, steps + mul_add_steps(s, e))
        if s == 1:
            break
    return best_steps


for _ in range(int(input())):
    c1, c2 = [int(i) for i in input().split()]
    print(min_steps(c1, c2))
