# 2017 jan silver
with open('cowcode.in') as read:
    the_string, char_num = [i for i in read.readline().split()]
    char_num = int(char_num)


def find_char(position: int, find_in: str) -> str:
    if position <= len(find_in):
        return find_in[position - 1]  # -1 bc 0-based

    currTwoPower = 0  # this will be how many times we'll have to apply it (given that the original string's there alr)
    while True:
        currTwoPower += 1
        if position <= 2 ** currTwoPower * len(find_in):
            break
    lowerBound = len(find_in) * 2 ** (currTwoPower - 1)

    if position == lowerBound + 1:
        return find_char(lowerBound, find_in)  # bc of the rotation
    return find_char(position - lowerBound - 1, find_in)


answer = find_char(char_num, the_string)
print(answer)
with open('cowcode.out', 'w') as written:
    written.write(str(answer) + '\n')
