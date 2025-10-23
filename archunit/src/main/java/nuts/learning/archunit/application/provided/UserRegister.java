package nuts.learning.archunit.application.provided;

import nuts.learning.archunit.domain.User;

public interface UserRegister {
    User createUser(String name, String email);

    User getUserById(Long id);

    void changeUserEmail(Long id, String newEmail);
}
