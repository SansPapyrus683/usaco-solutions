with open('sleepy.in') as read:
    cowNum = int(read.readline())
    cows = [int(c) - 1 for c in read.readline().split()]

last = float('inf')
badUpTo = cowNum - 1
while badUpTo >= 0:
    if cows[badUpTo] > last:
        break
    last = cows[badUpTo]
    badUpTo -= 1
badUpTo += 1  # we went too far for on time, so let's just back up one

print(badUpTo)
print(badUpTo, file=open('sleepy.out', 'w'))
