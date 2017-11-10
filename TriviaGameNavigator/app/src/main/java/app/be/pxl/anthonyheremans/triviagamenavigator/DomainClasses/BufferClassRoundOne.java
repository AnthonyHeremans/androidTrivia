package app.be.pxl.anthonyheremans.triviagamenavigator.DomainClasses;

/**
 * Created by 11401671 on 24/10/2017.
 */
//Class to get Round One from database, split responsecode from data
public class BufferClassRoundOne {

    private int response_code;
    private RoundOne[] results;

    public BufferClassRoundOne() {
    }

    public BufferClassRoundOne(int response_code, RoundOne[] results) {
        this.response_code = response_code;
        this.results = results;
    }


    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public RoundOne[] getResults() {
        return results;
    }

    public void setResults(RoundOne[] results) {
        this.results = results;
    }
}
