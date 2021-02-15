# 2019 jan bronze
with open('sleepy.in') as read:
    cow_num = int(read.readline())
    cows = [int(c) - 1 for c in read.readline().split()]

last = float('inf')
bad_up_to = cow_num - 1
while bad_up_to >= 0:
    if cows[bad_up_to] > last:
        break
    last = cows[bad_up_to]
    bad_up_to -= 1
bad_up_to += 1  # we went too far for on time, so let's just back up one

print(bad_up_to)
print(bad_up_to, file=open('sleepy.out', 'w'))
