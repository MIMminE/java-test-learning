package nuts.learning.archunit.application;

import nuts.learning.archunit.adapter.UserScheduler;
import nuts.learning.archunit.application.provided.UserRegister;
import nuts.learning.archunit.domain.User;

public class UserService implements UserRegister {

    @Override
    public User createUser(String name, String email) {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public void changeUserEmail(Long id, String newEmail) {

    }
}
