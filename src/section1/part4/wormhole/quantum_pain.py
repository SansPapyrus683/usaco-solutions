"""
ID: kevinsh4
TASK: wormhole
LANG: PYTHON3
"""


# i don't like global variables, so i made this class lol
class Wormholes:
    def __init__(self, wormholes):
        self._wormholes = wormholes
        # the index of the wormhole that's paired up
        self._partners = [-1 for _ in range(len(wormholes))]
        # the index of the next wormhole (going right)
        self._to_the_right = [-1 for _ in range(len(wormholes))]
        self.process_wormholes()

    def process_wormholes(self) -> None:
        for v, wh in enumerate(self._wormholes):
            poss_next = [w for w in self._wormholes if w[1] == wh[1] and wh[0] < w[0]]
            if poss_next:
                self._to_the_right[v] = self._wormholes.index(min(poss_next, key=lambda a: a[0]))

    def count_valid(self) -> int:
        for i in range(len(self._wormholes)):
            if self._partners[i] == -1:
                break
        else:  # ok, everyone has a partner, let's test
            if self.inf_loop_possible():
                return 1
            return 0

        total = 0
        for j in range(i + 1, len(self._wormholes)):
            if self._partners[j] == -1:  # assign a random partner and keep on going
                self._partners[i] = j
                self._partners[j] = i
                total += self.count_valid()
                self._partners[i] = self._partners[j] = -1  # reset the partners
        return total

    def inf_loop_possible(self) -> bool:
        # try all start positions
        for s in range(len(self._wormholes)):
            pos = s
            # we can only go through so many wormholes before a loop is reached
            for _ in range(len(self._wormholes)):
                # we'll just wander off into infinity (and beyond) from here
                if self._to_the_right[pos] == -1:
                    break
                pos = self._partners[self._to_the_right[pos]]
                if pos == s:  # oh wow, we ended back where we started!
                    return True
        return False  # not a single start works, heck


with open('quantum_suffering.txt') as read:
    wormhole_num = int(read.readline())
    positions = []
    for _ in range(wormhole_num):
        positions.append([int(i) for i in read.readline().split()])

valid_pairings = Wormholes(positions).count_valid()
print(valid_pairings)
print(valid_pairings, file=open('outputs.txt', 'w'))
