with open('cownomics.in') as read:
    typeNum, genomeLen = [int(i) for i in read.readline().split()]
    spotty = [read.readline().strip().upper() for _ in range(typeNum)]
    plain = [read.readline().strip().upper() for _ in range(typeNum)]

valid = 0
for i in range(genomeLen):
    spottyLetters = {g[i] for g in spotty}
    plainLetters = {g[i] for g in plain}
    if spottyLetters.isdisjoint(plainLetters):
        valid += 1

print(valid)
print(valid, file=open('cownomics.out', 'w'))
