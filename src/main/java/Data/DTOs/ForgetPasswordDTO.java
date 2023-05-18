package Data.DTOs;

public class ForgetPasswordDTO {
    private String email;
    private String safetyQuestion;
    private String safetyAnswer;
    public ForgetPasswordDTO() {
    }
    public ForgetPasswordDTO(String email, String safetyQuestion, String safetyAnswer) {
        this.email = email;
        this.safetyQuestion = safetyQuestion;
        this.safetyAnswer = safetyAnswer;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setSafetyQuestion(String safetyQuestion) {
        this.safetyQuestion = safetyQuestion;
    }
    public void setSafetyAnswer(String safetyAnswer) {
        this.safetyAnswer = safetyAnswer;
    }
    public String getEmail() {
        return email;
    }
    public String getSafetyQuestion() {
        return safetyQuestion;
    }
    public String getSafetyAnswer() {
        return safetyAnswer;
    }

}
