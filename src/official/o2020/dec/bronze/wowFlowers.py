"""
2020 dec bronze
4
1 1 2 3 should output 6
"""
input()
daises = [int(i) for i in input().split()]

averageCount = 0
for start in range(len(daises)):
    for end in range(start, len(daises)):
        photo = daises[start:end + 1]
        if sum(photo) / (end - start + 1) in photo:
            averageCount += 1
print(averageCount)
