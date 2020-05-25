import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;
import java.util.stream.Collectors;

public class main {
    public static String class1Text = "AI for medicine Specialization from deeplearningai. " +
//        "Postman enterprise e-commerce webinar series, episode 2. " +
        "Technical issues on Coursera. " +
//        "Postman enterprise e-commerce webinar series, episode 1. " +
//        "Exclusive invitation: Duolingo Saint-Petersburg events. " +
            "Russia confirmed 10,102 new coronavirus infections Tuesday, bringing the country’s official number of cases to 155,370. " +
        "New Duolingo event scheduled.";

//    public static String class2Text = "International Sport Collection. " +
//            "Forever Floatride is here. " +
////            "Let is play. " +
////            "Update of the TeamViewer and list of Sub-processors. " +
//            "New asset store notification. " +
//            "Welcome to YouTube Premium! ";

    public static String class2Text = "Which countries have confirmed cases? " +
            "All you need to know about symptoms and risks. " +
            "How to make sense of the virus numbers and charts. " +
            "How the new virus spread. ";

    private static List<String> messagesOfClass1 = new ArrayList<>();
    private static List<String> messagesOfClass2 = new ArrayList<>();

    public static void main(String[] args) {
        IntrervalNorris intrervalNorris = new IntrervalNorris();
//        System.out.println(intrervalNorris.getNorris(getIntervalData()).toString());

        List<Tree> class1 = getDataTree(class1Text);
        List<Tree> class2 = getDataTree(class2Text);

        TreeNorris treeNorris = new TreeNorris();
        TreeCbO treeCbO = new TreeCbO();
        TreeUtils treeUtils = new TreeUtils();
        treeUtils.treesIntersection(class1.get(0), class1.get(1));
        treeUtils.treesIntersection(class1.get(0), class1.get(0));
//        treeNorris.intersect(class1.get(0), class1.get(1), new TreeGraphNode(class1.get(0))).pennPrint();
//        treeNorris.equalsTrees(class1.get(0), class1.get(1));
//        treeNorris.equalsTrees(class1.get(0), class1.get(0));
//        treeNorris.intersect(class1.get(0), class1.get(1), new TreeGraphNode(class1.get(0))).pennPrint();
//        treeNorris.intersect(class1.get(0), class1.get(0), new TreeGraphNode(class1.get(0))).pennPrint();
//        treeNorris.intersect(class1.get(0), class1.get(2), new TreeGraphNode(class1.get(0))).pennPrint();
//        treeNorris.intersect(class1.get(1), class1.get(2), new TreeGraphNode(class1.get(0))).pennPrint();
//        TreeImpl.intersect(class1.get(2), class1.get(2), new TreeGraphNode(class1.get(0))).pennPrint();
        Map<Set<String>, Tree> resultNorris = treeNorris.getNorris(getTreeMatrix(class2));
        Map<Set<String>, Tree> result = treeCbO.CbO(getTreeMatrix(class2));
        Map<Set<String>, Tree> result2 = treeCbO.CbO(getTreeMatrix(class1));
        classification(result, result2);
        System.out.println("++++++++++++++++++++++++++++++");
        List<Tree> treesVal = result.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        for (int i = 0; i < treesVal.size(); i++){
            treesVal.get(i).pennPrint();
            System.out.println("----------");
        }

        System.out.println("////////////////////");
        List<Tree> treesValNorris = resultNorris.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        for (int i = 0; i < treesValNorris.size(); i++){
            treesValNorris.get(i).pennPrint();
            System.out.println("----------");
        }

        System.out.println();
    }

    static void classification(Map<Set<String>, Tree> class1, Map<Set<String>, Tree> class2) {
        String text = "Welcome to the open university. " +
                "Diploma of higher education in data analysis. " +
                "Swiss to see families again as lockdown continues to ease. " +
                "Life as normal despite ravaging virus epidemic. ";
        List<Tree> listOfTrees = new ArrayList<>();
        Map<String, Map<Set<String>, Tree>> dataForClassification = new HashMap<>();
        Map<String, Tree> classifiedData = new HashMap<>();
        Map<Set<String>, Tree> temp = new HashMap<>();
        class1.forEach(temp::put);
        dataForClassification.put("class1", temp);
        Map<Set<String>, Tree> temp1 = new HashMap<>();
        class2.forEach(temp1::put);
        dataForClassification.put("class2", temp1);

        listOfTrees = getDataTree(text);
        for (int i = 0; i < listOfTrees.size(); i++) {
            classifiedData.put(Integer.toString(i), listOfTrees.get(i));
        }

        ClassificationUtils.classification(dataForClassification, classifiedData);
    }

    public static List<Map<Set<String>, Tree>> getTreeMatrix(List<Tree> objects){
        List<Map<Set<String>, Tree>> matrix = new ArrayList<>();
        Map<Set<String>, Tree> mapa1 = new HashMap<>();
        Map<Set<String>, Tree> mapa2 = new HashMap<>();
        Map<Set<String>, Tree> mapa3 = new HashMap<>();
        Map<Set<String>, Tree> mapa4 = new HashMap<>();
        Map<Set<String>, Tree> mapa5 = new HashMap<>();
        Map<Set<String>, Tree> mapa6 = new HashMap<>();
        HashSet o1 = new HashSet();
        o1.add("o1");
        HashSet o2 = new HashSet();
        o2.add("o2");
        HashSet o3 = new HashSet();
        o3.add("o3");
        HashSet o4 = new HashSet();
        o4.add("o4");
        HashSet o5 = new HashSet();
        o5.add("o5");
        HashSet o6 = new HashSet();
        o6.add("o6");

        mapa1.put(o1, objects.get(0));
        mapa2.put(o2, objects.get(1));
        mapa3.put(o3, objects.get(2));
        mapa4.put(o4, objects.get(3));
//        mapa5.put(o5, objects.get(4));
//        mapa6.put(o6, objects.get(5));

        matrix.add(mapa1);
        matrix.add(mapa2);
        matrix.add(mapa3);
        matrix.add(mapa4);
//        matrix.add(mapa5);
//        matrix.add(mapa6);

        return matrix;
    }

    public static List<Tree> getDataTree(String text){
        Annotation document =
                new Annotation(text);
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        pipeline.annotate(document);
        List<Tree> treeList = new ArrayList<>();
        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree constituencyParse = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            constituencyParse.pennPrint();
            treeList.add(constituencyParse);
            System.out.println();
        }

        return treeList;
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
        o1.add("ЗИ");
        List<Interval> line1 = new ArrayList<>();
        Interval invV1l1 = new Interval(-30, -30);
        Interval invV2l1 = new Interval(-34, -34);
        Interval invV3l1 = new Interval(-29, -29);
        line1.add(invV1l1);
        line1.add(invV2l1);
        line1.add(invV3l1);
        //----------------------------------------------
        HashSet o2 = new HashSet();
        o2.add("ЗМ");
        List<Interval> line2 = new ArrayList<>();
        Interval invV1l2 = new Interval(-21, -21);
        Interval invV2l2 = new Interval(-18, -18);
        Interval invV3l2 = new Interval(-10, -10);
        line2.add(invV1l2);
        line2.add(invV2l2);
        line2.add(invV3l2);
        //----------------------------------------------
        HashSet o3 = new HashSet();
        o3.add("ЗЯ");
        List<Interval> line3 = new ArrayList<>();
        Interval invV1l3 = new Interval(-54, -54);
        Interval invV2l3 = new Interval(-52, -52);
        Interval invV3l3 = new Interval(-46, -46);
        line3.add(invV1l3);
        line3.add(invV2l3);
        line3.add(invV3l3);
        //----------------------------------------------
//        HashSet o4 = new HashSet();
//        o4.add("3");
//        List<Interval> line4 = new ArrayList<>();
//        Interval invV1l4 = new Interval(-54, -54);//new Interval(0, 0);
//        Interval invV2l4 = new Interval(-52, -52);//new Interval(-4, -4);
//        Interval invV3l4 = new Interval(-46, -46);//new Interval(3, 3);
//        line4.add(invV1l4);
//        line4.add(invV2l4);
//        line4.add(invV3l4);
        //----------------------------------------------
        Map<Set<String>, List<Interval>> lineOfObject1 = new HashMap();
        Map<Set<String>, List<Interval>> lineOfObject2 = new HashMap();
        Map<Set<String>, List<Interval>> lineOfObject3 = new HashMap();
//        Map<Set<String>, List<Interval>> lineOfObject4 = new HashMap();
        lineOfObject1.put(o1, line1);
        lineOfObject2.put(o2, line2);
        lineOfObject3.put(o3, line3);
//        lineOfObject4.put(o4, line4);

        List<Map<Set<String>, List<Interval>>> matrix = new ArrayList<>();
        matrix.add(lineOfObject1);
        matrix.add(lineOfObject2);
        matrix.add(lineOfObject3);
//        matrix.add(lineOfObject4);

        return matrix;
    }
}
