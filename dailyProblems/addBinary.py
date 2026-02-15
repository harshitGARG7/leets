class Solution:
    def addBinary(self, a: str, b: str) -> str:
        if a == "0":
            return b
        if b == "0":
            return a

        l1 = []
        l2 = []

        for i in a:
            l1.append(int(i))
        for i in b:
            l2.append(int(i))

        l1.reverse()
        l2.reverse()

        n = max(len(l1), len(l2))
        carry = 0
        result = []

        for i in range(n):
            bit1 = l1[i] if i < len(l1) else 0
            bit2 = l2[i] if i < len(l2) else 0

            total = bit1 + bit2 + carry
            result.append(total % 2)
            carry = total // 2

        if carry:
            result.append(carry)

        result.reverse()
        return "".join(map(str, result))
