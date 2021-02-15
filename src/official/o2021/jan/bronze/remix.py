"""
2021 jan gold
abcdefghijklmnopqrstuvwxyz
mood should output 3
"""
cowphabet = input().lower()
testament = input().lower()

heard_at = 0
min_said = 1  # bessie's at least hummed the cowphabet one time i mean come on
for c in testament:
    next_ind = -1
    for i in range(len(cowphabet)):
        if cowphabet[(heard_at + i) % len(cowphabet)] == c:
            next_ind = (heard_at + i) % len(cowphabet)
            break
    if next_ind == -1:
        raise ValueError("uh bessie hummed something that isn't in the cowphabet lol")

    min_said += next_ind <= heard_at  # we cycled back, so that's another time
    heard_at = next_ind
print(min_said)
