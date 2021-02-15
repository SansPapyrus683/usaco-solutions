import re


# 2019 dec bronze
def unique_substr(string: str, subLen: int):
    for i in range(subLen, len(string)):
        # sauce: https://stackoverflow.com/questions/11430863/how-to-find-overlapping-matches-with-a-regexp
        if len(re.findall("(?=%s)" % string[i - subLen:i], string)) > 1:
            return False
    return True


with open('whereami.in') as read:
    read.readline()
    mailboxes = read.readline().strip()

# completely unnecessary, but let's use binary search because why the hell not
lo = 0
hi = len(mailboxes)
valid = -1
while lo <= hi:
    mid = (lo + hi) // 2
    if unique_substr(mailboxes, mid):
        valid = mid
        hi = mid - 1
    else:
        lo = mid + 1

print(valid)
print(valid, file=open('whereami.out', 'w'))
