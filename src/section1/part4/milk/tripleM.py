"""
ID: kevinsh4
TASK: milk
LANG: PYTHON3
"""
with open('milkHeaven.txt') as read:
    req, farmerNum = [int(i) for i in read.readline().split()]
    prices = [[int(i) for i in read.readline().split()] for _ in range(farmerNum)]
    assert sum(p[1] for p in prices) >= req, "there should be enough milk i mean"

prices.sort()
spent = 0
gottenMilk = 0
while gottenMilk < req:
    thisPrice, milkAmt = prices.pop(0)
    buyAmt = min(milkAmt, req - gottenMilk)
    gottenMilk += buyAmt
    spent += buyAmt * thisPrice

print(spent)
print(spent, file=open('outputs.txt', 'w'))
