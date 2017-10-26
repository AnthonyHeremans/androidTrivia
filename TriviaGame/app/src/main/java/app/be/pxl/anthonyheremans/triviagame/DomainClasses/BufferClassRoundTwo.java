package app.be.pxl.anthonyheremans.triviagame.DomainClasses;

/**
 * Created by 11401671 on 24/10/2017.
 */
//Class to get Round two from database, split responsecode from data
public class BufferClassRoundTwo {
    private int response_code;
    private RoundTwo[] results;

    public BufferClassRoundTwo() {
    }

    public BufferClassRoundTwo(int response_code, RoundTwo[] results) {
        this.response_code = response_code;
        this.results = results;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public RoundTwo[] getResults() {
        return results;
    }

    public void setResults(RoundTwo[] results) {
        this.results = results;
    }
}
