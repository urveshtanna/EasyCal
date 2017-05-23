package in.urveshtanna.easycal.model;

/**
 * Adapter used to search product with pricing and navigate to product details
 *
 * @author urveshtanna
 * @version <Current-Version>
 * @see <Usage>
 * @since 1.2.0
 */

public class Calculation {

    private String symbol;

    private Double value;

    private Double answer;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getAnswer() {
        return answer;
    }

    public void setAnswer(Double answer) {
        this.answer = answer;
    }
}
