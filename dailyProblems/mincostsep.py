class Solution:
    def minimumCost(self, nums: List[int]) -> int:
        a=0
        b=0
        result=nums[0]
        if len(nums)==3:
            return sum(nums)
        l=[]
        for i in range(1,len(nums)):
            l.append(nums[i])
        l.sort()
        result=result+l[0]+l[1]
        return result
