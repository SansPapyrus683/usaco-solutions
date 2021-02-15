"""
ID: kevinsh4
TASK: fact4
LANG: PYTHON3
"""
the_fact = int(open('bigBigBigO.txt').read())
five_count, curr_five_power = 0, 5

while the_fact // curr_five_power > 0:  # these two loops get how many zeroes there are in the factorial
    five_count += the_fact // curr_five_power
    curr_five_power *= 5

two_zeroes = five_zeroes = five_count
unit_digits = 1
for i in range(1, the_fact + 1):
    while i % 5 == 0 and five_zeroes > 0:
        i /= 5
        five_zeroes -= 1

    while i % 2 == 0 and two_zeroes > 0:
        i /= 2
        two_zeroes -= 1

    unit_digits = (unit_digits * i) % 10

print(unit_digits)
print(unit_digits, open('outputs.txt', 'w'))
