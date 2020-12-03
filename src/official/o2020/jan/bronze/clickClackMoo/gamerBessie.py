# 2020 jan bronze
with open('word.in') as read:
    maxWidth = int(read.readline().split()[1])
    essay = read.readline().split()

formatted = ""
rnLength = 0
for v, w in enumerate(essay):
    if rnLength + len(w) > maxWidth:  # ok, this word exceeds the limit, let's wrap it around
        formatted += "\n"
        rnLength = 0
    formatted += w + ' '
    rnLength += len(w)

with open('word.out', 'w') as written:
    for line in formatted.split("\n"):
        print(line.strip())
        written.write(str(line.strip()) + '\n')
