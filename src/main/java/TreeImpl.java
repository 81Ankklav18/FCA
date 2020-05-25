import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeGraphNode;

public class TreeImpl {
    public static boolean equalsTrees(Tree v1, Tree v2) {
        if (v1.depth() != v2.depth()) {
            return Boolean.FALSE;
        }
        if (v1.numChildren() != v2.numChildren()) {
            return Boolean.FALSE;
        }
        for (int i = 0; i < v1.numChildren(); i++) {
            if (!v1.getChild(i).label().toString().equals(v2.getChild(i).label().toString())) {
                return Boolean.FALSE;
            }
            if (!equalsTrees(v1.getChild(i), v2.getChild(i))) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }

    public static Tree intersect(Tree v1, Tree v2, Tree result) {
        for (int i = 0; i < v1.numChildren(); i++) {
            for (int j = 0; j < v2.numChildren(); j++) {
                if (v1.getChild(i).label().toString().equals(v2.getChild(j).label().toString())) {
                    result.addChild(new TreeGraphNode(v1.getChild(i)));
                    if (result.numChildren() > Integer.min(v1.numChildren(), v2.numChildren())) {
                        deleteDuplicate(result, Integer.min(v1.numChildren(), v2.numChildren()));
                    }
                    if (result.numChildren() != 0)
                        intersect(v1.getChild(i), v2.getChild(j), result.getChild(result.numChildren() - 1));
                }
            }
        }

        return result;
    }

    private static Tree deleteDuplicate(Tree result, int minChildren) {
        int minDepth = Integer.MAX_VALUE;
        int maxDepth = Integer.MIN_VALUE;
        boolean flag = Boolean.FALSE;
        boolean flagInf = Boolean.TRUE;
        while (result.numChildren() > minChildren) {
            for (int i = 0; i < result.numChildren() - 1; i++) {
                for (int j = result.numChildren() - 1; j >= 0; j--) {
                    if (result.getChild(i).label().toString().equals(result.getChild(j).label().toString())
                            && i != j) {
                        if (minDepth > result.getChild(i).depth()) {
                            minDepth = result.getChild(i).depth();
                        }
                        if (maxDepth < result.numChildren()) {
                            maxDepth = result.getChild(i).depth();
                        }
                    }
                }
            }

            if (minDepth == maxDepth) {
                for (int i = result.numChildren() - 1; i >= 0; i--) {
                    for (int j = result.numChildren() - 1; j >= 0; j--) {
                        if (result.getChild(i).label().toString().equals(result.getChild(j).label().toString())
                                && i != j) {
                            if (result.getChild(i).depth() == minDepth && result.numChildren() > minChildren) {
                                result.removeChild(i);
                                flag = Boolean.TRUE;
                                break;
                            }
                        }
                    }
                }
            }

            if (!flag) {
                for (int i = result.numChildren() - 1; i >= 0; i--) {
                    for (int j = result.numChildren() - 1; j >= 0; j--) {
                        if (result.getChild(i).label().toString().equals(result.getChild(j).label().toString())
                                && i != j) {
                            if (result.getChild(i).depth() == minDepth && result.numChildren() > minChildren) {
                                result.removeChild(i);
                                flagInf = Boolean.FALSE;
                            }
                        }
                    }
                }
            }

//            if (flagInf) {
//                for (int i = result.numChildren() - 1; i > 0; i--) {
//                    for (int j = result.numChildren() - 1; j > 0; j--) {
//                        if (result.getChild(i).label().toString().equals(result.getChild(j).label().toString())
//                                && i != j) {
//                            result.removeChild(i);
//                            flagInf = Boolean.FALSE;
//
//                        }
//                    }
//                }
//            }
        }

        return result;
    }
}
