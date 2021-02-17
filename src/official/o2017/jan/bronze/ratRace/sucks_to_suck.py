# 2017 jan bronze
COWS = [
    "BESSIE",
    "ELSIE",
    "DAISY",
    "GERTIE",
    "ANNABELLE",
    "MAGGIE",
    "HENRIETTA"
]

milk = {c: 0 for c in COWS}
with open('notlast.in') as read:
    for _ in range(int(read.readline())):
        cow, amt = read.readline().split()
        milk[cow.upper()] += int(amt)

standings = sorted(milk.items(), key=lambda c: c[1])
milk_amts = sorted(set(milk.values()))

if len(milk_amts) == 1:
    print('Tie')
    print('Tie', file=open('notlast.out', 'w'))
else:
    second_last_cows = [s for s in standings if s[1] == milk_amts[1]]
    if len(second_last_cows) > 1:
        print('Tie')
        print('Tie', file=open('notlast.out', 'w'))
    else:
        ans = second_last_cows[0][0]
        ans = ans[0] + ans[1:].lower()
        print(ans)
        print(ans, file=open('notlast.out', 'w'))
