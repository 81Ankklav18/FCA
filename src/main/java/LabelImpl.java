import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.LabelFactory;

public class LabelImpl implements Label {
    String value;

    LabelImpl(String value) {
        this.value = value;
    }

    @Override
    public String value(){
        return this.value;
    };

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setFromString(String labelStr) { /*...*/ }

    public LabelFactory labelFactory() {
        return null;
    };
}
