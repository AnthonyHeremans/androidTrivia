package app.be.pxl.anthonyheremans.triviagamenavigator.DomainClasses;

/**
 * Created by 11401671 on 17/10/2017.
 */

public class Questions {
    private String category;
    private String type;
    private String difficulty;
    private String question;

    //Super Class
    public Questions(String category, String type, String difficulty, String question) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
