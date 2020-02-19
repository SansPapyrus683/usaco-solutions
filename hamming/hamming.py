"""
ID: kevinsh4
TASK: hamming
LANG: PYTHON3
"""
with open('hamming.in') as read:
    for v, i in enumerate([int(x) for x in read.read().split()]):
        if v == 0:
            codeNum = i
        elif v == 1:
            bitLen = i
        elif v == 2:
            hamDist = i


def hamming(a, b):
    first, second = [int(i) for i in str(bin(a)[2:])], [int(i) for i in str(bin(b)[2:])]
    conformLen = max(len(i) for i in [first, second])
    for n in first, second:
        while len(n) < conformLen:
            n.insert(0, 0)
    hamCount = 0
    for index, n in enumerate(first):
        if second[index] != n:
            hamCount += 1
    return hamCount


def chunks(lst, n):
    for i in range(0, len(lst), n):
        yield lst[i: i + n]


codewords = []
for i in range(codeNum):
    for x in range(2**bitLen):
        for testAgainst in codewords:
            if not hamming(x, testAgainst) >= hamDist:
                break
        else:
            codewords.append(x)
            break


print(codewords)
with open('hamming.out', 'w') as written:
    for chunk in chunks(codewords, 10):
        written.write(' '.join([str(s) for s in chunk]) + '\n')
