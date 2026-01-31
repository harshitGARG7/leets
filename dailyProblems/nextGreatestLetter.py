class Solution:
    def nextGreatestLetter(self, letters: List[str], target: str) -> str:
        s= letters[0]
        for i in letters :
            if ascii(target)<ascii(i):
                return i
        return s