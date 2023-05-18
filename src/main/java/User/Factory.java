package User;

import Data.DAOs.UserDAO;
import MyUtil.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class Factory {
    private static UserDAO userDAO;
    private static UserController userController;
    private static UserService userService;


    public  Factory() {
        this.userDAO = new UserDAO(makeCon());
        this.userController = new UserController(this.userDAO);
        this.userService = new UserService(this.userController);
        Gson gson = new Gson();
        String jsonUsers = userService.getAllUsers();
        Type responseType = new TypeToken<Response<List<JsonObject>>>() {}.getType();
        Response<List<JsonObject>> response = gson.fromJson(jsonUsers, responseType);

//        if (response.isSuccess()) {
//            List<JsonObject> users = response.getData();
//            for (JsonObject user : users) {
//                String jsonUser = gson.toJson(user);
//                System.out.println(jsonUser);
//            }
//        } else {
//            System.out.println(response.getMessage());
//        }


    }

    public static UserService getUserService() {
        return userService;
    }

    private Connection makeCon() {
        try {
            String dbFile = "src/main/DataBase/MyKanbanDB.db";
            String url = "jdbc:sqlite:" + dbFile;
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch (Exception var3) {
            System.out.println(var3.getMessage());
            return null;
        }
    }



}
