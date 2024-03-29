with open('cowsignal.in') as read:
    height, _, factor = [int(i) for i in read.readline().split()]
    logo = []
    for _ in range(height):
        logo.append(read.readline().rstrip())

enlargedLogo = []
for r in logo:
    new_row = ''
    for c in r:
        new_row += c * factor
    for _ in range(factor):
        print(new_row)
        enlargedLogo.append(new_row)

with open('cowsignal.out', 'w') as written:
    for r in enlargedLogo:
        written.write(str(r) + '\n')
