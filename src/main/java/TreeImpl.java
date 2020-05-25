import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeGraphNode;

public class TreeImpl {
    public static boolean equalsTrees(Tree v1, Tree v2) {
        Tree vv1 = v1;
        Tree vv2 = v2;
        if (vv1.depth() != vv2.depth()) {
            return Boolean.FALSE;
        }
        if (vv1.numChildren() != vv2.numChildren()) {
            return Boolean.FALSE;
        }
        for (int i = 0; i < vv1.numChildren(); i++) {
            if (!vv1.getChild(i).label().toString().equals(vv2.getChild(i).label().toString())) {
                return Boolean.FALSE;
            }
            if (!equalsTrees(vv1.getChild(i), vv2.getChild(i))) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }

    public static Tree intersect(Tree v1, Tree v2, Tree result) {
        Tree vv1 = v1;
        Tree vv2 = v2;
        for (int i = 0; i < vv1.numChildren(); i++) {
            for (int j = 0; j < vv2.numChildren(); j++) {
                if (vv1.getChild(i).label().toString().equals(vv2.getChild(j).label().toString())) {
                    result.addChild(new TreeGraphNode(vv1.getChild(i)));
                    if (result.numChildren() > Integer.min(vv1.numChildren(), vv2.numChildren())) {
                        result = deleteDuplicate(result, Integer.min(vv1.numChildren(), vv2.numChildren()));
                    }
                    if (result.numChildren() != 0)
                        intersect(vv1.getChild(i), vv2.getChild(j), result.getChild(result.numChildren() - 1));
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
