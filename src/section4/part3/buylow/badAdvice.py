"""
ID: kevinsh4
TASK: buylow
LANG: PYTHON3
(times out for the last few test cases, but imma still leave this here)
"""
with open('buylow.in') as read:
    stockNum = int(read.readline())
    stocks = []
    for li in read.readlines():
        stocks.extend(int(s) for s in li.split())
    assert len(stocks) == stockNum, "you sure the numbers are right?"

longestSoFar = [1 for _ in range(stockNum)]
numberOfLongest = [1 for _ in range(stockNum)]
for i in range(stockNum - 1, -1, -1):
    for j in range(i + 1, stockNum):  # so sue me for using i and j
        if stocks[j] < stocks[i]:
            longestSoFar[i] = max(longestSoFar[i], longestSoFar[j] + 1)

    if longestSoFar[i] == 1:
        continue

    # if we alr have the amt of long sequences ending at one point, 
    # and there's another point whose stock price is the same 
    # as that point, we don't need to calculate it
    numberOfLongest[i] = 0
    calcedBefore = set()
    for j in range(i + 1, stockNum):  # go through them again and accumulate the combs
        if stocks[i] > stocks[j] and longestSoFar[j] + 1 == longestSoFar[i] and \
                stocks[j] not in calcedBefore:
            numberOfLongest[i] += numberOfLongest[j]
            calcedBefore.add(stocks[j])

longest = max(longestSoFar)
combs = 0
calcedBefore = set()  # we still have to use this set
for i in range(stockNum):
    if longestSoFar[i] == longest and stocks[i] not in calcedBefore:
        combs += numberOfLongest[i]
        calcedBefore.add(stocks[i])
print(longest, combs)
print(longest, combs, file=open('buylow.out', 'w'))
