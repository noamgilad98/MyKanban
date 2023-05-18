package Data.DAOs;

import Data.DTOs.UserDTO;

import java.sql.Connection;


public class UserDAO extends DAO {

    public UserDAO(Connection connection) {
        super(connection);
    }

    public UserDTO getUserByEmail(String email) throws Exception {
        UserDTO userDTO = (UserDTO) getByUnique(UserDTO.class, "email", email);
        if (userDTO != null) {
            return userDTO;
        } else {
            throw new Exception("did not find user with email: " + email);
        }

    }

}
