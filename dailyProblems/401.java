import java.util.*;

class Solution {
    public List<String> readBinaryWatch(int turnedOn) {
        List<String> result = new ArrayList<>();
        
        for (int hour = 0; hour < 12; hour++) {
            for (int minute = 0; minute < 60; minute++) {
                
                // Count total LEDs ON
                int bits = Integer.bitCount(hour) + Integer.bitCount(minute);
                
                if (bits == turnedOn) {
                    // Format minute to always have 2 digits
                    result.add(hour + ":" + String.format("%02d", minute));
                }
            }
        }
        
        return result;
    }
}
