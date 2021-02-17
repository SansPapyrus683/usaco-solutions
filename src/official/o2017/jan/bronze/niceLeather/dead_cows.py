# 2017 feb bronze (bruh i thought up until now that cow tipping was killing a cow)
# 0 is good, 1 is bad
with open('cowtip.in') as read:
    cows = [[int(c) == 0 for c in read.readline().strip()] for _ in range(int(read.readline()))]

switch_times = 0
# start from the bottom right corner and move to the left, then go to the above row
# it doesn't matter if we go up and then move a row to the left btw
for r in range(len(cows) - 1, -1, -1):
    for c in range(len(cows) - 1, -1, -1):
        if cows[r][c]:
            continue
        switch_times += 1
        for change_r in range(r, -1, -1):
            for change_c in range(c, -1, -1):
                cows[change_r][change_c] = not cows[change_r][change_c]

print(switch_times)
print(switch_times, file=open('cowtip.out', 'w'))
