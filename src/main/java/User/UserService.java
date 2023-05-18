package User;
import Data.DTOs.UserDTO;
import MyUtil.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class UserService {
    private UserController userController;
    public UserService(UserController userController) {
        this.userController = userController;
    }

    public String getAllUsers() {
        Gson gson = new Gson();
        try {
            return gson.toJson(new Response<>(true, "Success", userController.getAllUsers()));
        } catch (Exception e) {
            return gson.toJson(new Response<>(false, e.getMessage(), null));
        }
    }

    public String addNewUser(String jsonData) {
        Gson gson = new Gson();
        try {
            JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
            String email = jsonObject.get("email").getAsString();
            String password = jsonObject.get("password").getAsString();
            String firstName = jsonObject.get("firstName").getAsString();
            String lastName = jsonObject.get("lastName").getAsString();
            String safetyQuestion = jsonObject.get("safetyQuestion").getAsString();
            String safetyAnswer = jsonObject.get("safetyAnswer").getAsString();
            return gson.toJson(new Response<>(true, "Success", userController.addNewUser(email, password, firstName, lastName, safetyQuestion, safetyAnswer)));
        } catch (Exception e) {
            return gson.toJson(new Response<>(false, e.getMessage(), null));
        }
    }



    public String getPassword(String jsonData) {
        Gson gson = new Gson();
        try {
            JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
            String email = jsonObject.get("email").getAsString();
            String safetyQuestion = jsonObject.get("safetyQuestion").getAsString();
            String safetyAnswer = jsonObject.get("safetyAnswer").getAsString();
            return gson.toJson(new Response<>(true, "Success", userController.getPassword(email, safetyQuestion, safetyAnswer)));
        } catch (Exception e) {
            return gson.toJson(new Response<>(false, e.getMessage(), null));
        }
    }

    public String login(String jsonData) {
        Gson gson = new Gson();
        try {
            JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
            String email = jsonObject.get("email").getAsString();
            String password = jsonObject.get("password").getAsString();
            return gson.toJson(new Response<UserDTO>(true, "Success", userController.login(email, password)));
        } catch (Exception e) {
            return gson.toJson(new Response<>(false, e.getMessage(), null));
        }
    }
}



