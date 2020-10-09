"""
ID: kevinsh4
LANG: PYTHON3
TASK: range
"""

with open('range.in') as read:
	for v, l in enumerate(read):
		if v == 0:
			grass = [[] for _ in range(int(l))]
		else:
			grass[v-1] = [int(i) for i in l[:-1]]
fieldWidth = len(grass)
calcedBefore = set()
squareSizes = {s: 0 for s in range(1, fieldWidth + 1)}


def hugeUpdateSmall(squareSize):
	for i in range(1, squareSize + 1):
		pass


for w in range(fieldWidth, 0, -1):
	pass

with open('range.out', 'w') as written:
	for s in squareSizes:
		if squareSizes[s] > 0:
			written.write(f'{s} {squareSizes[s]}\n')
