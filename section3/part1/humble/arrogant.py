"""
ID: kevinsh4
TASK: humble
LANG: PYTHON3
"""
with open('whyHumble.txt') as read:
    for v, line in enumerate(read):
        line = [int(i) for i in line.split()]
        if v == 0:
            nthNum = line[1]
        elif v == 1:
            primeList = line.copy()

def humble(n, primes):
    h = [1] * (n + 1)  # n + 1 because the first element will be like [1 ....]
    primeCounters = {p: p for p in primes}  # and 1 isn't a humble
    expCounters = {p: 0 for p in primes}
    for n in range(1, n+1):
        h[n] = min(primeCounters.values())
        for p, c in primeCounters.items():
            if c == h[n]:
                expCounters[p] += 1
                primeCounters[p] = p * h[expCounters[p]]
    return h[-1]

with open('outputs.txt', 'w') as written:
    written.write(str(humble(nthNum, primeList)) + '\n')
