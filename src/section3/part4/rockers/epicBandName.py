"""
ID: kevinsh4
TASK: rockers
LANG: PYTHON3
"""
from queue import Queue

with open('rockers.in') as read:
    _, capacity, diskNum = [int(i) for i in read.readline().split()]
    songs = [int(i) for i in read.readline().split() if int(i) <= capacity]

visited = {(-1, 0, 0)}
frontier = Queue()
frontier.put((-1, 0, 1, 0))  # last song put in, total songs, disks used, last disk capacity
maxSongs = 0
while not frontier.empty():
    lastSong, totalSong, usedAlr, lastCap = frontier.get()
    reachableStates = []
    for v, s in enumerate(songs[lastSong + 1:]):
        if lastCap + s <= capacity:
            reachableStates.append((lastSong + v + 1, totalSong + 1, usedAlr, lastCap + s))
        else:
            if usedAlr + 1 <= diskNum:
                reachableStates.append((lastSong + v + 1, totalSong + 1, usedAlr + 1, s))

    for s in reachableStates:
        if s not in visited:
            maxSongs = max(maxSongs, s[1])
            frontier.put(s)
            visited.add(s)

print(maxSongs)
with open('rockers.out', 'w') as written:
    written.write(str(maxSongs) + '\n')
