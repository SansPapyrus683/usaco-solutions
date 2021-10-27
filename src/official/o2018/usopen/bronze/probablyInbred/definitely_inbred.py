# 2018 us open bronze
from collections import deque

REDACTED = '█'

children = {}
mother = {}
with open('family.in') as read:
    relationshipNum, first, second = read.readline().strip().split()
    relationshipNum = int(relationshipNum)
    for _ in range(relationshipNum):
        mom, kid = read.readline().strip().split()
        if mom not in children:
            children[mom] = []
        children[mom].append(kid)
        mother[kid] = mom

relation = 'NOT RELATED'
# same mother = siblings
if mother.get(first, REDACTED) == mother.get(second, REDACTED + 'asdf'):
    relation = 'SIBLINGS'
else:
    for c in [first, second]:
        dist = 0
        at = c
        poss_ancestor = second if c == first else first
        while at in mother:  # go up the family tree
            dist += 1
            if mother[at] == poss_ancestor:  # right up the ancestor line = mother or grandmother or whatever
                words = ['great' for _ in range(dist - 2)]
                if dist > 1:
                    words.append('grand')
                words.append('mother')
                relation = '%s is the %s of %s' % (poss_ancestor, '-'.join(words), c)
                break
            # a direct child of a direct ancestor = aunt
            elif poss_ancestor in children[mother[at]]:
                words = ['great' for _ in range(dist - 2)] + ['aunt']
                relation = '%s is the %s of %s' % (poss_ancestor, '-'.join(words), c)
                break
            at = mother[at]

    if relation == 'NOT RELATED':
        # do a bfs to see if they're even related at all (in which they would be cousins)
        frontier = deque([first])
        visited = {first, REDACTED}
        while frontier:
            curr = frontier.popleft()
            if curr == second:
                relation = 'COUSINS'
                break
            for c in children.get(curr, []) + [mother.get(curr, REDACTED)]:
                if c not in visited:
                    visited.add(c)
                    frontier.append(c)

print(relation)
print(relation, file=open('family.out', 'w'))
