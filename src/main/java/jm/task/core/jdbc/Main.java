package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {

        userService.createUsersTable();

        userService.saveUser("Стас", "Сильвесторов", (byte) 18);
        userService.saveUser("Миша", "Бегинс", (byte) 24);
        userService.saveUser("Елена", "Иванова", (byte) 43);
        userService.saveUser("Гриша", "Гришнов", (byte) 14);

        for (User user: userService.getAllUsers()){
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
