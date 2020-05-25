import edu.stanford.nlp.trees.Tree;

import java.util.*;
import java.util.stream.Collectors;

public class TreeNorris extends Lattice {
    private Map<Set<String>, Tree> resultList = new HashMap<>();
    private final Map<Set<String>, Tree> listOfNonClosableElements = new HashMap<>();

    public Map<Set<String>, Tree> getNorris(List<Map<Set<String>, Tree>> matrix) {
        resultList.putAll(matrix.get(0));
        for (int i = 1; i < matrix.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (equalsMapa(matrix.get(i), matrix.get(j))) {
                    resultList.putAll(union(matrix.get(i), matrix.get(j)));
                    listOfNonClosableElements.putAll(matrix.get(j));
                } else {
                    resultList.putAll(matrix.get(i));
                    Map<Set<String>, Tree> intersection;
                    intersection = intersection(matrix.get(i), matrix.get(j));
                    intersection.putAll(intersection(resultList, matrix.get(j)));
                    resultList.putAll(union(resultList, intersection));
                    //TODO: отладка
//                    resultList.entrySet().stream().map(x -> x.getValue()).collect(Collectors.toList()).get(6).pennPrint();
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

    boolean equalsMapa(Map<Set<String>, Tree> val1, Map<Set<String>, Tree> val2) {
        for (Map.Entry<Set<String>, Tree> e : val1.entrySet()) {
            Tree v1 = e.getValue();
            for (Map.Entry<Set<String>, Tree> entry : val2.entrySet()) {
                Tree v2 = entry.getValue();
                return TreeUtils.equalsTrees(v1, v2);
            }
        }

        return Boolean.FALSE;
    }

    //TODO: следует вынести в отдельный класс как переопределенный
//    public boolean equalsTrees(Tree v1, Tree v2) {
//        Tree vv1 = v1;
//        Tree vv2 = v2;
//        if (vv1.depth() != vv2.depth()) {
//            return Boolean.FALSE;
//        }
//        if (vv1.numChildren() != vv2.numChildren()){
//            return Boolean.FALSE;
//        }
//        for (int i = 0; i < vv1.numChildren(); i++) {
//            if (!vv1.getChild(i).label().toString().equals(vv2.getChild(i).label().toString())) {
//                return Boolean.FALSE;
//            }
//            if (!equalsTrees(vv1.getChild(i), vv2.getChild(i))){
//                return Boolean.FALSE;
//            }
//        }
//
//        return Boolean.TRUE;
//    }

    Map<Set<String>, Tree> union(Map<Set<String>, Tree> val1, Map<Set<String>, Tree> val2) {
        Map<Set<String>, Tree> result = new HashMap<>();

        val1.forEach((k1, v1) -> val2.forEach((k2, v2) -> {
            if (TreeUtils.equalsTrees(v1, v2)) {
                Set<String> set = new HashSet<>();
                set.addAll(k1);
                set.addAll(k2);
                result.put(set, v1);
                if (!k1.containsAll(k2))
                    listOfNonClosableElements.put(k1, v1);
            } else {
                result.put(k2, v2);
            }
        }));

        return result;
    }

    Map<Set<String>, Tree> intersection(Map<Set<String>, Tree> val1, Map<Set<String>, Tree> val2) {
        Map<Set<String>, Tree> result = new HashMap<>();
        TreeUtils treeUtils = new TreeUtils();

        val1.forEach((k1, v1) -> val2.forEach((k2, v2) -> {
            Set<String> set = new HashSet<>();
            set.addAll(k1);
            set.addAll(k2);
            Tree resultTree;
            if (set.size() > 1){
                resultTree = treeUtils.treesIntersection(v1, v2);
                result.put(set, resultTree);
            } else {
                result.put(k1, v1);
            }
        }));

        return result;
    }

    //TODO: следует вынести в отдельный класс
//    public Tree intersect(Tree v1, Tree v2, Tree result) {
//        Tree vv1 = v1;
//        Tree vv2 = v2;
//        for (int i = 0; i < vv1.numChildren(); i++) {
//            for (int j = 0; j < vv2.numChildren(); j++) {
//                if (vv1.getChild(i).label().toString().equals(vv2.getChild(j).label().toString())) {
//                    result.addChild(new TreeGraphNode(vv1.getChild(i)));
//                    if (result.numChildren() > Integer.min(vv1.numChildren(), vv2.numChildren())) {
//                        result = deleteDuplicate(result, Integer.min(vv1.numChildren(), vv2.numChildren()));
//                    }
//                    if (result.numChildren() != 0)
//                        intersect(vv1.getChild(i), vv2.getChild(j), result.getChild(result.numChildren() - 1));
//                }
//            }
//        }
//
//        return result;
//    }

//    Tree deleteDuplicate(Tree result, int minChildren) {
//        int minDepth = Integer.MAX_VALUE;
//        int maxDepth = Integer.MIN_VALUE;
//        boolean flag = Boolean.FALSE;
//        while (result.numChildren() > minChildren) {
//            for (int i = 0; i < result.numChildren() - 1; i++) {
//                for (int j = result.numChildren() - 1; j > 0; j--) {
//                    if (result.getChild(i).label().toString().equals(result.getChild(j).label().toString())
//                            && i != j) {
//                        if (minDepth > result.getChild(i).depth()) {
//                            minDepth = result.getChild(i).depth();
//                        }
//                        if (maxDepth < result.numChildren()) {
//                            maxDepth = result.getChild(i).depth();
//                        }
//                    }
//                }
//            }
//
//            if (minDepth == maxDepth) {
//                for (int i = result.numChildren() - 1; i > 0; i--) {
//                    for (int j = result.numChildren() - 1; j > 0; j--) {
//                        if (result.getChild(i).label().toString().equals(result.getChild(j).label().toString())
//                                && i != j) {
//                            if (result.getChild(i).depth() == minDepth) {
//                                result.removeChild(i);
//                                flag = Boolean.TRUE;
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (!flag) {
//                for (int i = result.numChildren() - 1; i > 0; i--) {
//                    for (int j = result.numChildren() - 1; j > 0; j--) {
//                        if (result.getChild(i).label().toString().equals(result.getChild(j).label().toString())
//                                && i != j) {
//                            if (result.getChild(i).depth() == minDepth) {
//                                result.removeChild(i);
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//
//        return result;
//    }
}
