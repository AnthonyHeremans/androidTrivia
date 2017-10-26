package app.be.pxl.anthonyheremans.triviagame.DomainClasses;

/**
 * Created by 11401671 on 17/10/2017.
 */

public class RoundOne extends Questions {
    private String correct_answer;
    private String[] incorrect_answers;

    public RoundOne(String category, String type, String difficulty, String question) {
        super(category, type, difficulty, question);
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String[] getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(String[] incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }
}
