"""
ID: kevinsh4
LANG: PYTHON3
TASK: range
yeah this is too slow lol
"""

with open('range.in') as read:
	for v, l in enumerate(read):
		if v == 0:
			grass = [[] for _ in range(int(l))]
		else:
			grass[v-1] = [int(i) for i in l.rstrip()]
fieldWidth = len(grass)
calcedBefore = set()
squareSizes = {s: set() for s in range(1, fieldWidth + 1)}


def newSquares(squareSize, r, c):
	for i in range(2, squareSize + 1):
		for a in range(squareSize - i + 1):
			for b in range(squareSize - i + 1):
				squareSizes[i].add((r + a, c + b))


for w in range(fieldWidth, 1, -1):
	for x in range(0, fieldWidth - w + 1):  # the starting x and y
		for y in range(0, fieldWidth - w + 1):
			if (x, y) in squareSizes[w]:
				continue
			valid = True
			for i in range(x, x + w):
				for j in range(y, y + w):
					if not grass[i][j]:
						valid = False
						break
				if not valid:
					break
			else:
				newSquares(w, x, y)

with open('range.out', 'w') as written:
	for s in squareSizes:
		if len(squareSizes[s]) > 0:
			print(f'{s} {len(squareSizes[s])}')
			written.write(f'{s} {len(squareSizes[s])}\n')
