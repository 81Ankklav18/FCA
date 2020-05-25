import edu.stanford.nlp.trees.Tree;

import java.util.*;

public class ClassificationUtils {
    public static void classification(Map<String, Map<Set<String>, Tree>> classes,
                                      Map<String, Tree> objects) {
        TreeUtils treeUtils = new TreeUtils();
        Map<String, Map<String, Tree>> classifiedObjects = new HashMap<>();
        Map<String, List<Integer>> mapOfDepth = new HashMap<>();
        List<Integer> listOfDepth = new ArrayList<>();

        objects.forEach((k, v) -> {
            classes.forEach((k1, v1) -> {
                v1.forEach((k2, v2) -> {
                    if (TreeUtils.equalsTrees(v, v2)) {
                        listOfDepth.add(v2.depth());
                        mapOfDepth.put(k1, listOfDepth);
                    }
                });
                if (mapOfDepth.size() == 0) {
                    v1.forEach((k2, v2) -> {
                        listOfDepth.add(treeUtils.treesIntersection(v, v2).depth());
                    });
                    mapOfDepth.put(k1, listOfDepth);
                }
            });
        });

        System.out.println();
    }
}
