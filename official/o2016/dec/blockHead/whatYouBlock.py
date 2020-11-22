from string import ascii_lowercase

words = []
with open('blocks.in') as read:
    for i in range(int(read.readline())):
        words.append(read.readline().rstrip().split())

needed = []
for c in ascii_lowercase:  # for each character calculate the "worst case scenario"
    maxCount = 0
    for w in words:
        maxCount += max(w[0].count(c), w[1].count(c))
    needed.append(maxCount)

with open('blocks.out', 'w') as written:
    for i in needed:
        print(i)
        written.write(str(i) + '\n')
