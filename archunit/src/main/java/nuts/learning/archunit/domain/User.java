package nuts.learning.archunit.domain;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private Long id;
    private String name;
    private String email;

    public void changeEmail(String newEmail) {
        if (newEmail == null || newEmail.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        this.email = newEmail;
    }
}
