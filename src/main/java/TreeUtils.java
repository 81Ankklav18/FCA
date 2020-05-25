import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeGraphNode;

import java.util.LinkedList;
import java.util.List;

public class TreeUtils {
    private String data;
    private Tree parent;
    List<Tree> children;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Tree getParent() {
        return parent;
    }

    public void setParent(Tree parent) {
        this.parent = parent;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

//    public int level(){
//        if (this.isRoot()){
//            return  0;
//        }
//
//        return level(parent);
//    }

    public TreeUtils() {
        this.children = new LinkedList<>();
    }

    public TreeUtils(String data) {
        this.data = data;
        this.children = new LinkedList<>();
    }

    public Tree addChild(String child, Tree ret) {
        Tree childNode = new TreeGraphNode(new LabelImpl(child));
        ((TreeGraphNode) childNode).setParent(new TreeGraphNode(ret));
        this.children.add(childNode);

        ret.addChild(childNode);
        return childNode;
    }

    static Tree getRoot(Tree t) {
        return t.parent();

    }

    public Tree treesIntersection(Tree t1, Tree t2) {
        Tree t1Root = t1;
        Tree t2Root = t2;

        if (!t1Root.label().toString().equals(t2Root.label().toString())) {
            return null;
        }

        Tree ret = new TreeGraphNode(t1Root.label());

        inspectChildren(t1, t2, ret);

        return ret;
    }

    public void inspectChildren(Tree t1, Tree t2, Tree ret) {
        List<Tree> t1List = t1.getChildrenAsList();
        List<Tree> t2List = t2.getChildrenAsList();

        for (Tree firstNode : t1List
        ) {
            Tree secondNode = t2List.stream()
                    .filter(x -> x.label().toString().equals(firstNode.label().toString()))
                    .findFirst()
                    .orElse(null);
            //TODO: здесь может быть ошибка
//TODO: пеледелать на телналку
            if (secondNode != null) {
                Tree newNode = addChild(secondNode.label().value(), ret);
                inspectChildren(firstNode, secondNode, newNode);

                t2List.remove(secondNode);
            }
        }
    }

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
            if (!vv1.getChild(i).label().value().equals(vv2.getChild(i).label().value())) {
                return Boolean.FALSE;
            }
            if (!equalsTrees(vv1.getChild(i), vv2.getChild(i))) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }
}
