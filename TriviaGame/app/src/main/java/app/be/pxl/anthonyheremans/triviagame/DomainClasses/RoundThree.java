package app.be.pxl.anthonyheremans.triviagame.DomainClasses;

import java.util.List;

/**
 * Created by 11401671 on 17/10/2017.
 */

public class RoundThree extends Questions {
    private List<String> answers;


    public RoundThree(String category, String type, String difficulty, String question, List<String> answers) {
        super(category, type, difficulty, question);
        this.answers = answers;
    }
}
