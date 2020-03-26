import java.util.*;
import java.util.stream.Collectors;

public class Norris {
    private Map<Set<String>, List<Interval>> resultList = new HashMap<>();
    private Map<Set<String>, List<Interval>> listOfNonClosableElements = new HashMap<>();

    public Map<Set<String>, List<Interval>> getNorris(List<Map<Set<String>, List<Interval>>> matrix){
        resultList.putAll(matrix.get(0));
        for (int i = 1; i < matrix.size(); i++){
                for (int j = 0; j < i; j++){
                    if (equalsMapa(matrix.get(i), matrix.get(j))){
                        resultList.putAll(union(matrix.get(i), matrix.get(j)));
                        listOfNonClosableElements.putAll(matrix.get(j));
                    } else {
                        resultList.putAll(matrix.get(i));
                        Map<Set<String>, List<Interval>> intersection;
                        intersection = intersection(matrix.get(i), matrix.get(j));
                        intersection.putAll(intersection(resultList, matrix.get(j)));
                        resultList.putAll(union(resultList, intersection));
                    }
                }
        }

        resultList = union(resultList, intersection(resultList, resultList));
        resultList.keySet().removeAll(listOfNonClosableElements.keySet());

        resultList = resultList
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(e -> e.getKey().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return this.resultList;
    }

    boolean equalsMapa(Map<Set<String>, List<Interval>> val1, Map<Set<String>, List<Interval>> val2){
        for (Map.Entry<Set<String>, List<Interval>> e : val1.entrySet()) {
            List<Interval> v1 = e.getValue();
            for (Map.Entry<Set<String>, List<Interval>> entry : val2.entrySet()) {
                List<Interval> v2 = entry.getValue();
                return Interval.equals(v1, v2);
            }
        }

        return Boolean.FALSE;
    }

    Map<Set<String>, List<Interval>> union(Map<Set<String>, List<Interval>> val1, Map<Set<String>, List<Interval>> val2){
        Map<Set<String>, List<Interval>> result = new HashMap<>();

        val1.forEach((k1, v1) -> val2.forEach((k2, v2) -> {
            if (Interval.equals(v1, v2)){
                Set<String> set = new HashSet();
                set.addAll(k1);
                set.addAll(k2);
                List<Interval> intervals = new ArrayList<>();
                intervals.addAll(v1);
                result.put(set, v1);
                if (!k1.containsAll(k2))
                    listOfNonClosableElements.put(k1, v1);
            } else {
                result.put(k2, v2);
            }
        }));

        return result;
    }

    Map<Set<String>, List<Interval>> intersection(Map<Set<String>, List<Interval>> val1, Map<Set<String>, List<Interval>> val2){
        Map<Set<String>, List<Interval>> result = new HashMap<>();

        val1.forEach((k1, v1) -> {
            val2.forEach((k2, v2) -> {
                Set<String> set = new HashSet();
                set.addAll(k1);
                set.addAll(k2);
                List<Interval> intervals = new ArrayList<>();
                intervals.addAll(Interval.getLargestInterval(v1, v2));
                result.put(set, intervals);
            });
        });

        return result;
    }
}
