"""
ID: kevinsh4
TASK: wormhole
LANG: PYTHON3
"""


class Wormholes:  # i don't like global variables, so i made this class lol
    def __init__(self, wormholes):
        self.__wormholes = wormholes
        self.__partners = [-1 for _ in range(len(wormholes))]  # the index of the wormhole that's paired up
        self.__toTheRight = [-1 for _ in range(len(wormholes))]  # the index of the next wormhole (going right)
        self.processWormholes()

    @property
    def wormholes(self):  # all this is to prevent monkeys from misusing my class
        return self.__wormholes

    @wormholes.setter
    def wormholes(self, value):
        self.__wormholes = value
        self.processWormholes()

    @property
    def partners(self):
        return self.__partners

    @property
    def toTheRight(self):
        return self.__toTheRight

    def processWormholes(self) -> None:
        for v, wh in enumerate(self.wormholes):
            possNext = [w for w in self.wormholes if w[1] == wh[1] and wh[0] < w[0]]
            if possNext:
                self.toTheRight[v] = self.wormholes.index(min(possNext, key=lambda a: a[0]))

    def countValid(self) -> int:
        for i in range(len(self.wormholes)):
            if self.partners[i] == -1:
                break
        else:  # ok, everyone has a partner, let's test
            if self.infLoopPossible():
                return 1
            return 0

        total = 0
        for j in range(i + 1, len(self.wormholes)):
            if self.partners[j] == -1:  # assign a random partner and keep on going
                self.partners[i] = j
                self.partners[j] = i
                total += self.countValid()
                self.partners[i] = self.partners[j] = -1  # reset the partners
        return total

    def infLoopPossible(self) -> bool:
        for s in range(len(self.wormholes)):  # try all start positions
            pos = s
            for _ in range(len(self.wormholes)):  # we can only go through so many wormholes before a loop is reached
                if self.toTheRight[pos] == -1:  # frick, from this start we just wander off into no where
                    break
                pos = self.partners[self.toTheRight[pos]]
                if pos == s:  # oh wow, we ended back where we started!
                    return True
        return False  # not a single start works, heck


with open('quantumSuffering.txt') as read:
    wormholeNum = int(read.readline())
    positions = []
    for _ in range(wormholeNum):
        positions.append([int(i) for i in read.readline().split()])

validPairings = Wormholes(positions).countValid()
print(validPairings)
print(validPairings, file=open('outputs.txt', 'w'))
