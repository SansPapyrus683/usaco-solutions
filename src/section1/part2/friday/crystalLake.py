"""
ID: kevinsh4
TASK: friday
LANG: PYTHON3
"""
years = int(open('jason.txt').read())

thirteenAmt = [0 for _ in range(7)]
currYear = 1900
day = 1  # sunday will be 0, monday will be 1... sat will be 6
months = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
for _ in range(years):
    leapYear = currYear % 400 == 0 or (currYear % 4 == 0 and currYear % 100 != 0)

    for m, dayAmt in enumerate(months):
        if m == 1 and leapYear:
            dayAmt = 29  # change the amount to 29 if it's a leap year and we're processing feb
        thirteenAmt[(day + 12) % 7] += 1
        day = (day + dayAmt) % 7  # jump to the first day of next month
    currYear += 1

goodOrder = [str(s) for s in [thirteenAmt[6]] + thirteenAmt[:6]]
print(goodOrder)
print(' '.join(goodOrder), file=open('outputs.txt', 'w'))
