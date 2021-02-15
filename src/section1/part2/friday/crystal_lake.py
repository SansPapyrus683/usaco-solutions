"""
ID: kevinsh4
TASK: friday
LANG: PYTHON3
"""
years = int(open('jason.txt').read())

thirteen_amt = [0 for _ in range(7)]
curr_year = 1900
day = 1  # sunday will be 0, monday will be 1... sat will be 6
months = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
for _ in range(years):
    leapYear = curr_year % 400 == 0 or (curr_year % 4 == 0 and curr_year % 100 != 0)

    for m, day_amt in enumerate(months):
        if m == 1 and leapYear:
            day_amt = 29  # change the amount to 29 if it's a leap year and we're processing feb
        thirteen_amt[(day + 12) % 7] += 1
        day = (day + day_amt) % 7  # jump to the first day of next month
    curr_year += 1

good_order = [str(s) for s in [thirteen_amt[6]] + thirteen_amt[:6]]
print(good_order)
print(' '.join(good_order), file=open('outputs.txt', 'w'))
