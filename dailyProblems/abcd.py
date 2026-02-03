from typing import List

class Solution:
    def isTrionic(self, nums: List[int]) -> bool:
        n = len(nums)
        if n < 4:
            return False

        i = 1

        while i < n and nums[i] > nums[i - 1]:
            i += 1
        if i == 1 or i == n:
            return False


        while i < n and nums[i] < nums[i - 1]:
            i += 1
        if i == n:
            return False


        while i < n and nums[i] > nums[i - 1]:
            i += 1

        return i == n
