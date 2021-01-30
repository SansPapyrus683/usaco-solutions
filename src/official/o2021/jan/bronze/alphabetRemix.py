"""
2021 jan gold
abcdefghijklmnopqrstuvwxyz
mood should output 3
"""
cowphabet = input().lower()
testament = input().lower()

heardAt = 0
minSaid = 1  # bessie's at least hummed the cowphabet one time i mean come on
for c in testament:
    nextInd = -1
    for i in range(len(cowphabet)):
        if cowphabet[(heardAt + i) % len(cowphabet)] == c:
            nextInd = (heardAt + i) % len(cowphabet)
            break
    if nextInd == -1:
        raise ValueError("uh bessie hummed something that isn't in the cowphabet lol")

    minSaid += nextInd <= heardAt  # we cycled back, so that's another time
    heardAt = nextInd
print(minSaid)
