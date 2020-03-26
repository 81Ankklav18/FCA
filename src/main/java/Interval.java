import java.util.ArrayList;
import java.util.List;

public class Interval {
    int min;
    int max;

    public Interval(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public Interval(String interval) {
        String delimeter = "[\\[,\\],\\,\\ ]";
        String[] data = interval.split(delimeter);
        this.min = Integer.parseInt(data[1]);
        this.max = Integer.parseInt(data[2]);
    }

    @Override
    public String toString() {
        return "[" + min + "; " + max + "]";
    }

    static private int getMin(int i1, int i2) {
        if (i1 < i2)
            return i1;
        else
            return i2;
    }

    static private int getMax(int i1, int i2) {
        if (i1 > i2)
            return i1;
        else
            return i2;
    }

    static public Interval getLargestInterval(Interval i1, Interval i2){
        return new Interval(getMin(i1.min, i2.min), getMax(i1.max, i2.max));
    }

    static public List<Interval> getLargestInterval(List<Interval> l1, List<Interval> l2){
        List<Interval> result = new ArrayList<>();
        for (int i = 0; i < l1.size(); i++){
            result.add(getLargestInterval(l1.get(i), l2.get(i)));
        }
        return result;
    }

    static public boolean equals(List<Interval> i1, List<Interval> i2){
        boolean flag = true;
        for (int i = 0; i < i1.size(); i++){
            if (!equals(i1.get(i), i2.get(i))){
                flag = false;
            }
        }
        return flag;
    }

    static public boolean equals(Interval i1, Interval i2){
        if(i1.min == i2.min && i1.max == i2.max){
            return true;
        } else {
            return false;
        }
    }
}
