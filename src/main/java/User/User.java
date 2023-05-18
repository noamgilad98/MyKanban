package User;

public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String safetyQuestion;
    private String safetyAnswer;

    public User() {
    }
    public User(int id, String email, String password, String firstName, String lastName, String safetyQuestion, String safetyAnswer) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.safetyQuestion = safetyQuestion;
        this.safetyAnswer = safetyAnswer;
    }

}
