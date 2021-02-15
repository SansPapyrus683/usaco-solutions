"""
ID: kevinsh4
TASK: kimbits
LANG: PYTHON3
"""
# (documentation on other file)
import operator as op  # this file is if for you're running earlier versions of python (i mean pre 3.8)
from functools import reduce  # THE FUTURE IS NOW, OLD MAN.

with open('stinky.txt') as read:
    length, one_bit_num, nth_bit_thing = [int(i) for i in read.read().split()]
    nth_bit_thing -= 1


def ncr(n, r):
    r = min(r, n - r)
    numer = reduce(op.mul, range(n, n - r, -1), 1)
    denom = reduce(op.mul, range(1, r + 1), 1)
    return numer // denom


def all_strings(max_string_len, most_bits):
    most_bits = max_string_len if most_bits > max_string_len else most_bits
    return sum([ncr(max_string_len, i) for i in range(most_bits + 1)])


def find_index(string_len, most_bits, the_index, history=''):
    if string_len == 1:
        if most_bits == 0:
            return history + '0'  # i mean, there's only one possibility so why not
        else:
            return [history + '0', history + '1'][the_index]

    if the_index < all_strings(string_len - 1, most_bits):  # the recursive part
        return find_index(string_len - 1, most_bits, the_index, history + '0')
    else:
        return find_index(string_len - 1, most_bits - 1,
                          the_index - all_strings(string_len - 1, most_bits), history + '1')


with open('outputs.txt', 'w') as written:
    written.write(str(find_index(length, one_bit_num, nth_bit_thing)) + '\n')
