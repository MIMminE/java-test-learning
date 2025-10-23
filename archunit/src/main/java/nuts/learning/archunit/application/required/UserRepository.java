package nuts.learning.archunit.application.required;

import nuts.learning.archunit.domain.User;

public interface UserRepository {
    User save(User user);

    User findById(Long id);
}
