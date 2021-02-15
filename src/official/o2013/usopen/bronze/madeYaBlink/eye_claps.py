with open('blink.in') as read:
    bulb_num, move_amt = [int(i) for i in read.readline().split()]
    bulbs = [bool(int(read.readline())) for _ in range(bulb_num)]

seen = {}
states = []
toggle_time = 0
while tuple(bulbs) not in seen:
    states.append(bulbs)
    seen[tuple(bulbs)] = toggle_time
    toggle_time += 1
    updated = []
    for v, b in enumerate(bulbs):
        updated.append(not b if bulbs[v - 1] else b)
    bulbs = updated

offset = seen[tuple(bulbs)]
cycle_len = toggle_time - offset
move_amt = (move_amt - offset) % cycle_len
final_state = states[move_amt + offset]

with open('blink.out', 'w') as written:
    for b in final_state:
        print(int(b))
        written.write(str(int(b)) + '\n')
