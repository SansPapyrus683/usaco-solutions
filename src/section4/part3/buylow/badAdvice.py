"""
ID: kevinsh4
TASK: buylow
LANG: PYTHON3
(times out for the last few test cases, but imma still leave this here)
"""
with open('buylow.in') as read:
    stock_num = int(read.readline())
    stocks = []
    for li in read.readlines():
        stocks.extend(int(s) for s in li.split())
    assert len(stocks) == stock_num, "you sure the numbers are right?"

longest_so_far = [1 for _ in range(stock_num)]
number_of_longest = [1 for _ in range(stock_num)]
for i in range(stock_num - 1, -1, -1):
    for j in range(i + 1, stock_num):  # so sue me for using i and j
        if stocks[j] < stocks[i]:
            longest_so_far[i] = max(longest_so_far[i], longest_so_far[j] + 1)

    if longest_so_far[i] == 1:
        continue

    # if we alr have the amt of long sequences ending at one point, 
    # and there's another point whose stock price is the same 
    # as that point, we don't need to calculate it
    number_of_longest[i] = 0
    calced_before = set()
    for j in range(i + 1, stock_num):  # go through them again and accumulate the combs
        if stocks[i] > stocks[j] and longest_so_far[j] + 1 == longest_so_far[i] and \
                stocks[j] not in calced_before:
            number_of_longest[i] += number_of_longest[j]
            calced_before.add(stocks[j])

longest = max(longest_so_far)
combs = 0
calced_before = set()  # we still have to use this set
for i in range(stock_num):
    if longest_so_far[i] == longest and stocks[i] not in calced_before:
        combs += number_of_longest[i]
        calced_before.add(stocks[i])
print(longest, combs)
print(longest, combs, file=open('buylow.out', 'w'))
