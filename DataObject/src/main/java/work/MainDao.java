package work;

public class MainDao {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
//dodawanie
//
//        User user = new User();
//        user.setUserName("Michalina");
//        user.setEmail("pudlomichalina203@gamil.com");
//        user.setPassword("dzik");
//        userDao.create(user);


        // czytani
        //   User read = userDao.read(1);
//        User read = userDao.read(2);
//        System.out.println(read);

          //3niema
//        User readNotExist = userDao.read(3);
//        System.out.println(readNotExist);

        //zmiana 1 id z null na dane
//        User userToUpdate = userDao.read(1);
//        userToUpdate.setUserName("Ania");
//        userToUpdate.setEmail("aniamakota@gmail.com");
//        userToUpdate.setPassword("kottokrzys");
//        userDao.update(userToUpdate);


        //usuwanie Ani z id 1
//        userDao.delete(1);



//
//        User secondUser = new User();
//        secondUser.setUserName("Ania");
//        secondUser.setEmail("aaniamakota@wp.pl");
//        secondUser.setPassword("ania");
//        userDao.create(secondUser);
//        User[] all = userDao.findAll();
//        for (User u : all) {
//            System.out.println(u);
//        }

        User[] users = userDao.findAll();
        userDao.showUsers(users);

    }
}
