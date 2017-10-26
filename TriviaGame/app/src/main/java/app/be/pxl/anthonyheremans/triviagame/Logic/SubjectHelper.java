package app.be.pxl.anthonyheremans.triviagame.Logic;

/**
 * Created by 11401671 on 26/10/2017.
 */

public class SubjectHelper {

    public static String subjectIdToString(String subjectId)
    {
        String categoryValue = "";
        //match category's with the ID's
        switch (subjectId) {
            case "9":
                categoryValue = "General Knowledge";
                break;
            case "10":
                categoryValue = "Entertainment: Books";
                break;
            case "11":
                categoryValue = "Entertainment: Film";
                break;
            case "12":
                categoryValue = "Entertainment: Music";
                break;
            case "14":
                categoryValue = "Entertainment: Television";
                break;
            case "17":
                categoryValue = "Science &amp; Nature";
                break;
            case "18":
                categoryValue = "Science: Computer";
                break;
            case "19":
                categoryValue = "Science: Mathematics";
                break;
            case "20":
                categoryValue = "Mythology";
                break;
            case "21":
                categoryValue = "Sports";
                break;
            case "22":
                categoryValue = "Geography";
                break;
            case "23":
                categoryValue = "History";
                break;
            case "24":
                categoryValue = "Politics";
                break;
            case "25":
                categoryValue = "Art";
                break;
//                    case "Celebrities":
//                        categoryValue = "26";
//                        break;
//                   case "Animals":
//                        categoryValue = "27";
//                        break;
        }
        return categoryValue;

    }
    public static String subjectStringToId(String subjectString)
    {
        String categoryValue = "";
        //match category's with the ID's
        switch (subjectString) {
            case "General Knowledge":
                categoryValue = "9";
                break;
            case "Entertainment: Books":
                categoryValue = "10";
                break;
            case "Entertainment: Film":
                categoryValue = "11";
                break;
            case "Entertainment: Music":
                categoryValue = "12";
                break;
            case "Entertainment: Television":
                categoryValue = "14";
                break;
            case "Science &amp; Nature":
                categoryValue = "17";
                break;
            case "Science: Computer":
                categoryValue = "18";
                break;
            case "Science: Mathematics":
                categoryValue = "19";
                break;
            case "Mythology":
                categoryValue = "20";
                break;
            case "Sports":
                categoryValue = "21";
                break;
            case "Geography":
                categoryValue = "22";
                break;
            case "History":
                categoryValue = "23";
                break;
            case "Politics":
                categoryValue = "24";
                break;
            case "Art":
                categoryValue = "25";
                break;
//                    case "Celebrities":
//                        categoryValue = "26";
//                        break;
//                   case "Animals":
//                        categoryValue = "27";
//                        break;
        }
        return categoryValue;

    }

}
