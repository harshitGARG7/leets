class Solution:
    def reverseBits(self, n: int) -> int:
        n &= 0xFFFFFFFF   # Force 32-bit representation
        result = 0
        
        for _ in range(32):
            result = (result << 1) | (n & 1)
            n >>= 1
        
        return result
