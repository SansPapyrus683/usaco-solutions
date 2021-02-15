"""
ID: kevinsh4
TASK: gift1
LANG: PYTHON3
"""
with open('not_santa.txt') as read:
    people = {}  # dictionaries preserve insertion order now: we'll use that
    transactions = []
    people_num = int(read.readline())
    for _ in range(people_num):
        people[read.readline().rstrip()] = 0
    for _ in range(people_num):
        giver = read.readline().rstrip()
        money, receiver_num = [int(i) for i in read.readline().split()]
        receivers = []
        for _ in range(receiver_num):
            receivers.append(read.readline().rstrip())
        # a bit more explicit this way
        transactions.append({'giver': giver, 'money': money, 'receivers': receivers})

# i think there's a better way to do this with some math, but simulating it is good enough
for t in transactions:
    if not t['receivers']:
        continue
    to_give = t['money'] // len(t['receivers'])
    people[t['giver']] -= t['money']
    for r in t['receivers']:
        people[r] += to_give
    
    leftOver = t['money'] - len(t['receivers']) * to_give
    people[t['giver']] += leftOver

with open('outputs.txt', 'w') as written:
    for p, m in people.items():
        print(p, m)
        written.write(str(p) + ' ' + str(m) + '\n')
