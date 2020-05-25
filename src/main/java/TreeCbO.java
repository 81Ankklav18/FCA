import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeGraphNode;

import java.util.*;
import java.util.stream.Collectors;

public class TreeCbO extends Lattice {
    private Map<Set<String>, Tree> resultList = new HashMap<>();
    private Map<Set<String>, Tree> listOfNonClosableElements = new HashMap<>();
    private List<Map<Set<String>, Tree>> valueList = new ArrayList<>();
    int count = 0;

    public Map<Set<String>, Tree> CbO(List<Map<Set<String>, Tree>> matrix) {
        prepare(matrix);
        return recursiveCbO(valueList);
    }

    void prepare(List<Map<Set<String>, Tree>> matrix) {
        valueList = matrix;
        for (int i = 0; i < matrix.size(); i++) {
            resultList.putAll(matrix.get(i));
        }
        count = matrix.size();
    }

    public Map<Set<String>, Tree> recursiveCbO(List<Map<Set<String>, Tree>> matrix) {
        List<Map<Set<String>, Tree>> stage;

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = i + 1; j < matrix.size(); j++) {
                Map<Set<String>, Tree> resultOfIntersection = intersection(matrix.get(i), matrix.get(j));
//                for (int k = 0; k < resultList.size(); k++) {
//                    if (equalsMapa(resultOfIntersection, resultList) &&
//                            !resultOfIntersection.keySet().equals(resultList.keySet())) {
//                        listOfNonClosableElements.putAll(resultList);
//                    }
//                }
                resultList.forEach((key, value) ->
                        resultOfIntersection.forEach((key1, value1) -> {
                            if (TreeUtils.equalsTrees(value, value1) && !key.equals(key1) && key.size() < key1.size()) {
                                listOfNonClosableElements.put(key, value);
                            }
                }));
                resultList.keySet().removeAll(listOfNonClosableElements.keySet());
                resultList.putAll(resultOfIntersection);
            }
        }

//        resultList = resultList
//                .entrySet()
//                .stream()
//                .filter(x -> !(x.getValue().depth() == 0 && x.getKey().size() != count))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                        (e1, e2) -> e1, LinkedHashMap::new));

        stage = convertMapToListOfMap(resultList);
        while (!resultList.keySet().stream().filter(x -> x.size() == count).findFirst().isPresent()) {
            recursiveCbO(stage);
        }

        resultList = resultList
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(e -> e.getKey().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println("//////////////");
        List<Tree> treesVal = resultList.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        for (int i = 0; i < treesVal.size(); i++){
            treesVal.get(i).pennPrint();
            System.out.println("----------");
        }
        return this.resultList;
    }

    boolean equalsSetsOfString(Set<String> s1, Set<String> s2) {
        List<String> l1 = new ArrayList<>(s1);
        List<String> l2 = new ArrayList<>(s2);

        if (l1.size() != l2.size()) {
            return Boolean.FALSE;
        } else {
            for (int i = 0; i < l1.size(); i++) {
                if (l1.get(i) != l2.get(i)) {
                    return Boolean.FALSE;
                }
            }
        }

        return Boolean.TRUE;
    }

    List<Map<Set<String>, Tree>> convertMapToListOfMap(Map<Set<String>, Tree> stage) {
        List<Map<Set<String>, Tree>> convertedList = new ArrayList<>();
        stage.forEach((k, v) -> {
            Map<Set<String>, Tree> temp = new HashMap<>();
            temp.put(k, v);
            convertedList.add(new HashMap<Set<String>, Tree>(temp));
        });

        return convertedList;
    }

    Map<Set<String>, Tree> intersection(Map<Set<String>, Tree> val1, Map<Set<String>, Tree> val2) {
        Map<Set<String>, Tree> result = new HashMap<>();
        TreeUtils treeUtils = new TreeUtils();

        val1.forEach((k1, v1) -> {
            val2.forEach((k2, v2) -> {
                Set<String> set = new HashSet();
                set.addAll(k1);
                set.addAll(k2);
                Tree resultTree = treeUtils.treesIntersection(v1, v2);
                result.put(set, resultTree);
            });
        });

        return result;
    }

    boolean equalsMapa(Map<Set<String>, Tree> val1, Map<Set<String>, Tree> val2) {
        boolean result = Boolean.FALSE;
        for (Map.Entry<Set<String>, Tree> e : val1.entrySet()) {
            Tree v1 = e.getValue();
            for (Map.Entry<Set<String>, Tree> entry : val2.entrySet()) {
                Tree v2 = entry.getValue();
                result = TreeUtils.equalsTrees(v1, v2);
            }
        }

        return result;
    }
}
