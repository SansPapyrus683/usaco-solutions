"""
ID: kevinsh4
TASK: prefix
LANG: PYTHON3
"""
prefixes = []
with open('bioTrash.txt') as read:
    detectPrefix = True
    for v, line in enumerate(read):
        if line.rstrip() == '.':
            detectPrefix = False
            continue
        if detectPrefix:
            prefixes.extend([s for s in line.rstrip().split()])
        else:
            
