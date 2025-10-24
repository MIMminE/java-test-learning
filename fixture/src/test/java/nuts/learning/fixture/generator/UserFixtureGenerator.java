package nuts.learning.fixture.generator;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import net.jqwik.api.Arbitraries;
import nuts.learning.fixture.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * FixtureMonkey + Jqwik을 활용한 영문 테스트 데이터 생성기
 *
 * 주요 기능:
 * - 영문자만 생성하는 문자열 필터링
 * - Jqwik Arbitraries를 활용한 조건부 데이터 생성
 * - FixtureMonkey의 JakartaValidationPlugin으로 유효한 객체 생성
 */
public class UserFixtureGenerator {

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    /**
     * 기본 영문 User 생성 (성능 최적화)
     * - name: 영문자 3-10글자
     * - email: 영문자 8-15글자 + @gmail.com
     * - age: 20-70세
     */
    public User generateEnglishUser() {
        return fixtureMonkey.giveMeBuilder(User.class)
                .set("name", Arbitraries.strings()
                        .alpha()  // ✅ 영문자만 생성
                        .ofMinLength(3)
                        .ofMaxLength(10))
                .set("email", Arbitraries.strings()
                        .alpha()  // ✅ 영문자만 생성
                        .ofMinLength(8)
                        .ofMaxLength(15) + "@gmail.com")
                .set("age", Arbitraries.integers().between(20, 70))
                .set("createdAt", LocalDateTime.now().minusDays((int) (Math.random() * 365)))
                .sample();
    }

    /**
     * 지정된 개수만큼 영문 User 리스트 생성
     */
    public List<User> generateEnglishUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(generateEnglishUser());
        }
        return users;
    }

    /**
     * 특정 나이 범위의 영문 User 생성 (성능 최적화)
     */
    public User generateEnglishUserWithAge(int minAge, int maxAge) {
        return fixtureMonkey.giveMeBuilder(User.class)
                .set("name", Arbitraries.strings()
                        .alpha()  // ✅ 영문자만 생성
                        .ofMinLength(3)
                        .ofMaxLength(10))
                .set("email", Arbitraries.strings()
                        .alpha()  // ✅ 영문자만 생성
                        .ofMinLength(8)
                        .ofMaxLength(15) + "@gmail.com")
                .set("age", Arbitraries.integers().between(minAge, maxAge))
                .set("createdAt", LocalDateTime.now().minusDays((int) (Math.random() * 365)))
                .sample();
    }
}
