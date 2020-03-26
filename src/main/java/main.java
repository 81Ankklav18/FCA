import java.util.*;

public class main {
    public static void main(String[] args) {
        Norris norris = new Norris();
        System.out.println(norris.getNorris(getIntervalData()).toString());
    }

    List<List<Integer>> getDataForBinary(){
        List<Integer> line1 = new ArrayList<>();
        line1.add(1);
        line1.add(0);
        line1.add(1);
        line1.add(1);
        line1.add(0);
        List<Integer> line2 = new ArrayList<>();
        line2.add(0);
        line2.add(0);
        line2.add(1);
        line2.add(1);
        line2.add(1);
        List<Integer> line3 = new ArrayList<>();
        line3.add(1);
        line3.add(0);
        line3.add(0);
        line3.add(1);
        line3.add(1);
        List<Integer> line4 = new ArrayList<>();
        line4.add(0);
        line4.add(0);
        line4.add(0);
        line4.add(1);
        line4.add(1);
        List<List<Integer>> matrix = new ArrayList<>();
        matrix.add(line1);
        matrix.add(line2);
        matrix.add(line3);
        matrix.add(line4);

        return matrix;
    }

    static public List<Map<Set<String>, List<Interval>>> getIntervalData(){
        HashSet o1 = new HashSet();
        o1.add("0");
        List<Interval> line1 = new ArrayList<>();
        Interval invV1l1 = new Interval(-30, -30);
        Interval invV2l1 = new Interval(-34, -34);
        Interval invV3l1 = new Interval(-29, -29);
        line1.add(invV1l1);
        line1.add(invV2l1);
        line1.add(invV3l1);
        //----------------------------------------------
        HashSet o2 = new HashSet();
        o2.add("1");
        List<Interval> line2 = new ArrayList<>();
        Interval invV1l2 = new Interval(-21, -21);
        Interval invV2l2 = new Interval(-18, -18);
        Interval invV3l2 = new Interval(-10, -10);
        line2.add(invV1l2);
        line2.add(invV2l2);
        line2.add(invV3l2);
        //----------------------------------------------
        HashSet o3 = new HashSet();
        o3.add("2");
        List<Interval> line3 = new ArrayList<>();
        Interval invV1l3 = new Interval(-54, -54);
        Interval invV2l3 = new Interval(-52, -52);
        Interval invV3l3 = new Interval(-46, -46);
        line3.add(invV1l3);
        line3.add(invV2l3);
        line3.add(invV3l3);
        //----------------------------------------------
        HashSet o4 = new HashSet();
        o4.add("3");
        List<Interval> line4 = new ArrayList<>();
        Interval invV1l4 = new Interval(-54, -54);//new Interval(0, 0);
        Interval invV2l4 = new Interval(-52, -52);//new Interval(-4, -4);
        Interval invV3l4 = new Interval(-46, -46);//new Interval(3, 3);
        line4.add(invV1l4);
        line4.add(invV2l4);
        line4.add(invV3l4);
        //----------------------------------------------
        Map<Set<String>, List<Interval>> lineOfObject1 = new HashMap();
        Map<Set<String>, List<Interval>> lineOfObject2 = new HashMap();
        Map<Set<String>, List<Interval>> lineOfObject3 = new HashMap();
        Map<Set<String>, List<Interval>> lineOfObject4 = new HashMap();
        lineOfObject1.put(o1, line1);
        lineOfObject2.put(o2, line2);
        lineOfObject3.put(o3, line3);
        lineOfObject4.put(o4, line4);

        List<Map<Set<String>, List<Interval>>> matrix = new ArrayList<>();
        matrix.add(lineOfObject1);
        matrix.add(lineOfObject2);
        matrix.add(lineOfObject3);
        matrix.add(lineOfObject4);

        return matrix;
    }
}
