"""
ID: kevinsh4
TASK: milk
LANG: PYTHON3
"""
with open('milkHeaven.txt') as read:
    req, farmer_num = [int(i) for i in read.readline().split()]
    prices = [[int(i) for i in read.readline().split()] for _ in range(farmer_num)]
    assert sum(p[1] for p in prices) >= req, "there should be enough milk i mean"

prices.sort()
spent = 0
gotten_milk = 0
while gotten_milk < req:
    this_price, milk_amt = prices.pop(0)
    buyAmt = min(milk_amt, req - gotten_milk)
    gotten_milk += buyAmt
    spent += buyAmt * this_price

print(spent)
print(spent, file=open('outputs.txt', 'w'))
