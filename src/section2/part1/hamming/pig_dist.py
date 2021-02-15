"""
ID: kevinsh4
TASK: hamming
LANG: PYTHON3
"""
with open('buta.txt') as read:
    for v, i in enumerate([int(x) for x in read.read().split()]):
        if v == 0:
            codeNum = i
        elif v == 1:
            bitLen = i
        elif v == 2:
            hamDist = i


def hamming(a, b):  # outputs hamming dist between two numbers (base 10)
    first, second = [int(i) for i in str(bin(a)[2:])], [int(i) for i in str(bin(b)[2:])]
    conform_len = max(len(i) for i in [first, second])
    for n in first, second:
        while len(n) < conform_len:
            n.insert(0, 0)
    ham_count = 0
    for index, n in enumerate(first):
        if second[index] != n:
            ham_count += 1
    return ham_count


def chunks(lst, n):  # just for formatting output
    for i in range(0, len(lst), n):
        yield lst[i: i + n]


codewords = []
for i in range(codeNum):  # only generate the amt of codewords specified
    for x in range(2**bitLen):  # for each number in the range of the bit length the problem gives
        for testAgainst in codewords:
            if not hamming(x, testAgainst) >= hamDist:  # checks if this codeword is valid against all the previous ones
                break
        else:
            codewords.append(x)  # add if valid
            break

with open('outputs.txt', 'w') as written:
    for chunk in chunks(codewords, 10):
        written.write(' '.join([str(s) for s in chunk]) + '\n')
