"""
ID: kevinsh4
TASK: rockers
LANG: PYTHON3
"""
from queue import Queue

with open('rockers.in') as read:
    _, capacity, disk_num = [int(i) for i in read.readline().split()]
    songs = [int(i) for i in read.readline().split() if int(i) <= capacity]

visited = {(-1, 0, 0)}
frontier = Queue()
frontier.put((-1, 0, 1, 0))  # last song put in, total songs, disks used, last disk capacity
max_songs = 0
while not frontier.empty():
    last_song, total_song, used_alr, last_cap = frontier.get()
    reachableStates = []
    for v, s in enumerate(songs[last_song + 1:]):
        if last_cap + s <= capacity:
            reachableStates.append((last_song + v + 1, total_song + 1, used_alr, last_cap + s))
        else:
            if used_alr + 1 <= disk_num:
                reachableStates.append((last_song + v + 1, total_song + 1, used_alr + 1, s))

    for s in reachableStates:
        if s not in visited:
            max_songs = max(max_songs, s[1])
            frontier.put(s)
            visited.add(s)

print(max_songs)
with open('rockers.out', 'w') as written:
    written.write(str(max_songs) + '\n')
