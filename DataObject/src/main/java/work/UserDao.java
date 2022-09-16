package work;

import com.mysql.cj.xdevapi.Table;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    //😃
    private static final String CREATE_USER_QUERY = "INSERT INTO users.usersTable(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY = "SELECT * FROM users.usersTable where id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE usersTable SET username = ?, email = ?, password = ? where id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM usersTable WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY = "SELECT * FROM usersTable";

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

//    private static boolean exist(int id){
//        return read(id) != null ? true : false;
//    }
    public User read(int userId) {

        try (Connection conn = DbUtil.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
                }
            } catch (SQLException e){
                e.printStackTrace();
                return null;
            }
            return null;

    }

    public void update(User user){
        try (Connection conn = DbUtil.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, this.hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public static void delete (int userId){
            try (Connection conn = DbUtil.getConnection()) {
                PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
                statement.setInt(1, userId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    private static User[] addToArray (User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf (users, users.length + 1); //kopia o 1+
        tmpUsers[users.length] = u; //do ostatniej
        return tmpUsers; //nowa tab
    }


    public User[] findAll() {
        User[] usersArr = new User[0];
        try (Connection conn = DbUtil.getConnection()) {
            usersArr = new User[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                usersArr = addToArray(user, usersArr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersArr;
    }

    public void showUsers(User[] users) {
        for (User user : users) {
            System.out.println(user);
        }
    }


}

