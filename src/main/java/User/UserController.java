package User;
import Data.DAOs.DAO;
import Data.DAOs.UserDAO;
import Data.DTOs.DTO;
import Data.DTOs.UserDTO;

import java.util.List;


public class UserController {
    private UserDAO userDAO;
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    private int counterId = 0;

    public List<UserDTO> getAllUsers() throws Exception {
        return userDAO.getAll(UserDTO.class);
    }
    public UserDTO addNewUser( String email, String password, String firstName, String lastName, String safetyQuestion, String safetyAnswer) throws Exception {
        UserDTO userDTO = new UserDTO(counterId, email, password, firstName, lastName, safetyQuestion, safetyAnswer);
        userDAO.add(userDTO);
        this.counterId++;
        return userDTO;
    }

    public String getPassword(String email, String safetyQuestion, String safetyAnswer) throws Exception {
        UserDTO userDTO = userDAO.getUserByEmail(email);
        if (userDTO.getSafetyQuestion().equals(safetyQuestion) && userDTO.getSafetyAnswer().equals(safetyAnswer)) {
            return userDTO.getPassword();
        } else {
            throw new Exception("Wrong safety question or answer");
        }
    }

    public UserDTO login(String email, String password) throws Exception {
        UserDTO userDTO = userDAO.getUserByEmail(email);
        if (userDTO.getPassword().equals(password)) {
            return userDTO;
        } else {
            throw new Exception("Wrong password");
        }

    }
}
