import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    static class Event {
        double y;
        int x1, x2;
        int type; // +1 add, -1 remove

        Event(double y, int x1, int x2, int type) {
            this.y = y;
            this.x1 = x1;
            this.x2 = x2;
            this.type = type;
        }
    }

    static class SegmentTree {
        int n;
        int[] count;
        double[] length;
        double[] xs;

        SegmentTree(double[] xs) {
            this.xs = xs;
            this.n = xs.length - 1;
            count = new int[n * 4];
            length = new double[n * 4];
        }

        void update(int node, int l, int r, int ql, int qr, int val) {
            if (ql >= r || qr <= l)
                return;
            if (ql <= l && r <= qr) {
                count[node] += val;
            } else {
                int mid = (l + r) / 2;
                update(node * 2, l, mid, ql, qr, val);
                update(node * 2 + 1, mid, r, ql, qr, val);
            }
            if (count[node] > 0) {
                length[node] = xs[r] - xs[l];
            } else if (l + 1 == r) {
                length[node] = 0;
            } else {
                length[node] = length[node * 2] + length[node * 2 + 1];
            }
        }

        double query() {
            return length[1];
        }
    }

    public double separateSquares(int[][] squares) {
        List<Double> xList = new ArrayList<>();
        List<Event> events = new ArrayList<>();

        for (int[] s : squares) {
            double x1 = s[0], x2 = s[0] + s[2];
            double y1 = s[1], y2 = s[1] + s[2];
            xList.add(x1);
            xList.add(x2);
            events.add(new Event(y1, 0, 0, +1));
            events.add(new Event(y2, 0, 0, -1));
        }

        // Compress X
        double[] xs = xList.stream().distinct().sorted().mapToDouble(d -> d).toArray();
        Map<Double, Integer> map = new HashMap<>();
        for (int i = 0; i < xs.length; i++)
            map.put(xs[i], i);

        events.clear();
        for (int[] s : squares) {
            int x1 = map.get((double) s[0]);
            int x2 = map.get((double) (s[0] + s[2]));
            events.add(new Event(s[1], x1, x2, +1));
            events.add(new Event(s[1] + s[2], x1, x2, -1));
        }

        events.sort(Comparator.comparingDouble(e -> e.y));
        SegmentTree st = new SegmentTree(xs);

        // First pass: total area
        double total = 0;
        for (int i = 0; i < events.size() - 1; i++) {
            Event e = events.get(i);
            st.update(1, 0, st.n, e.x1, e.x2, e.type);
            double dy = events.get(i + 1).y - e.y;
            total += st.query() * dy;
        }

        double target = total / 2;
        st = new SegmentTree(xs);
        double curr = 0;

        // Second pass: find Y
        for (int i = 0; i < events.size() - 1; i++) {
            Event e = events.get(i);
            st.update(1, 0, st.n, e.x1, e.x2, e.type);
            double covered = st.query();
            double dy = events.get(i + 1).y - e.y;
            if (curr + covered * dy >= target) {
                return e.y + (target - curr) / covered;
            }
            curr += covered * dy;
        }
        return 0;
    }
}
