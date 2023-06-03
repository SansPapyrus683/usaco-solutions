from collections import deque

INVALID = 420696969


class Testimonies:
    def __init__(self, heard):
        self.testimonies = {}
        self.cows = set()
        for s in heard:
            # so not that if it's like a b T, that means a and b are the same thing (otherwise they're different)
            if s[0] not in self.testimonies:
                self.testimonies[s[0]] = []
            if s[1] not in self.testimonies:
                self.testimonies[s[1]] = []
            self.testimonies[s[0]].append([s[1], s[2]])
            self.testimonies[s[1]].append([s[0], s[2]])
            self.cows.add(s[0])
            self.cows.add(s[1])

    def consistent(self):
        hypothetical = {c: INVALID for c in self.cows}  # 0 is lying, 1 is truthful (tbh it doesn't matter)
        for c in self.cows:
            if hypothetical[c] != INVALID:
                continue

            hypothetical[c] = 1  # i mean it doesn't matter which one we assign it
            frontier = deque([c])
            while frontier:
                curr = frontier.popleft()
                this_config = hypothetical[curr]
                if this_config == INVALID:
                    raise RuntimeError("this shouldn't happen lol")

                for other, good in self.testimonies.get(curr, []):
                    supposed = this_config if good else (0 if this_config == 1 else 1)
                    if hypothetical[other] == INVALID:
                        hypothetical[other] = supposed
                        frontier.append(other)
                    elif supposed != hypothetical[other]:
                        return False
        return True


statements = []
with open('truth.in') as read:
    cow_num, statement_num = [int(i) for i in read.readline().split()]
    for _ in range(statement_num):
        said = read.readline().split()
        said[0], said[1] = int(said[0]), int(said[1])
        said[2] = said[2].upper() == 'T'
        statements.append(said)

lo = 1
hi = len(statements)
valid = 0
while lo <= hi:
    mid = (lo + hi) // 2
    if Testimonies(statements[:mid]).consistent():
        lo = mid + 1
        valid = mid
    else:
        hi = mid - 1
print(valid)
print(valid, file=open('truth.out', 'w'))
