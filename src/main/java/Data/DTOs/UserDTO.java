package Data.DTOs;
import Data.DTOs.DTO;

import java.util.List;

public class UserDTO implements DTO {
    private int id;
    private String email;

    private String password;
    private String firstName;
    private String lastName;
    private String safetyQuestion;
    private String safetyAnswer;


    public UserDTO() {
    }

    public UserDTO(int id, String email, String password, String firstName, String lastName, String safetyQuestion, String safetyAnswer) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.safetyQuestion = safetyQuestion;
        this.safetyAnswer = safetyAnswer;
    }

    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public List<String> getPrimaryKeyFields() {
        return List.of("id");
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getSafetyQuestion() {
        return safetyQuestion;
    }

    public void setSafetyQuestion(String safetyQuestion) {
        this.safetyQuestion = safetyQuestion;
    }

    public String getSafetyAnswer() {
        return safetyAnswer;
    }

    public void setSafetyAnswer(String safetyAnswer) {
        this.safetyAnswer = safetyAnswer;
    }

}
