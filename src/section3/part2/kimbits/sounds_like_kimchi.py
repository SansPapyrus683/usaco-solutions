"""
ID: kevinsh4
TASK: kimbits
LANG: PYTHON3
"""
from math import comb

with open('stinky.txt') as read:
    length, oneBitNum, nthBitThing = [int(i) for i in read.read().split()]
    nthBitThing -= 1  # -1 because arrays start at 0 and the problem doesn't have the set start @ 0


def all_strings(max_string_len, most_bits):
    return sum([comb(max_string_len, i) for i in range(most_bits + 1)])


def find_index(string_len, most_bits, ind, history=''):
    """
    ok, so what this does is that it tests if the string (with the given index) should start with 0 or 1
    it does that by testing the lengths of each set with 0 at the start or 1, and comparing with the index
    then it recurses deeper until all we have is a string of length 1, and that's it lol
    """
    if string_len == 1:
        if most_bits == 0:
            return history + '0'  # i mean, there's only one possibility so why not
        else:
            return [history + '0', history + '1'][ind]

    # oh, it's < and not <= bc arrays start at 0
    if ind < all_strings(string_len - 1, most_bits):  # the recursive part
        return find_index(string_len - 1, most_bits, ind, history + '0')
    else:
        return find_index(string_len - 1, most_bits - 1, ind - all_strings(string_len - 1, most_bits), history + '1')


with open('outputs.txt', 'w') as written:
    written.write(str(find_index(length, oneBitNum, nthBitThing)) + '\n')
