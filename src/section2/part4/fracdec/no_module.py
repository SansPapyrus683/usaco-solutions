"""
ID: kevinsh4
TASK: fracdec
LANG: PYTHON3
"""
n, d = [int(i) for i in open('fractionsBetter.txt').read().rstrip().split()]
int_part = 0
while n >= d:  # get out the whole number part
    int_part += 1
    n -= d

remains = [n * 10]
member_check = {n * 10}  # set member checks faster
new_remain = n * 10
dec_part = ''
while True:
    decimal = 0
    while new_remain >= d:
        new_remain -= d
        decimal += 1
    new_remain *= 10  # "drop the zero", which is really just mul-ing the inital one by 10
    dec_part += str(decimal)
    if new_remain in member_check:  # did we reach something already reached?
        for v, c in enumerate(remains):
            if new_remain == c:  # see where to cut the repeat off
                repeat_start = v
        break
    remains.append(new_remain)
    member_check.add(new_remain)


def chunks(lst, n):
    for i in range(0, len(lst), n):
        yield lst[i: i + n]


with open('outputs.txt', 'w') as written:
    if dec_part[repeat_start:] != '0':
        output = str(int_part) + '.' + dec_part[:repeat_start] + '(' + dec_part[repeat_start:] + ')'
    else:
        if not dec_part[:-1]:  # add a zero to prevent outputs like 3.
            dec_part += '0'
        output = str(int_part) + '.' + dec_part[:-1]
    for l in chunks(output, 76):
        written.write(l + '\n')
