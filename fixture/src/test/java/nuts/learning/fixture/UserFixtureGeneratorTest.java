package nuts.learning.fixture;

import nuts.learning.fixture.domain.User;
import nuts.learning.fixture.generator.UserFixtureGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UserFixtureGenerator를 활용한 FixtureMonkey + Jqwik 코드북
 *
 * 주요 기능:
 * - UserFixtureGenerator를 통한 객체 생성
 * - 영문자만 생성하는 문자열 필터링
 * - 조건부 데이터 생성
 * - 성능 최적화된 데이터 생성
 */
class UserFixtureGeneratorTest {

    private final UserFixtureGenerator generator = new UserFixtureGenerator();

    /**
     * 기본 영문 User 생성
     * UserFixtureGenerator의 기본 기능을 사용하여 영문 데이터 생성
     */
    @Test
    void generateBasicUser() {
        // Given & When
        User user = generator.generateEnglishUser();

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getName()).matches("[a-zA-Z]+");
        assertThat(user.getEmail()).contains("@");
        assertThat(user.getAge()).isBetween(20, 70);
        assertThat(user.getCreatedAt()).isNotNull();
    }

    /**
     * 특정 나이 범위의 영문 User 생성
     * UserFixtureGenerator의 generateEnglishUserWithAge() 메서드 사용
     */
    @Test
    void generateUserWithAgeRange() {
        // Given & When
        User user = generator.generateEnglishUserWithAge(18, 25);

        // Then
        assertThat(user.getAge()).isBetween(18, 25);
        assertThat(user.getName()).matches("[a-zA-Z]+");
        assertThat(user.getEmail()).contains("@");
    }

    /**
     * 여러 개의 영문 User 생성
     * UserFixtureGenerator의 generateEnglishUsers() 메서드 사용
     */
    @Test
    void generateMultipleUsers() {
        // Given & When
        List<User> users = generator.generateEnglishUsers(5);

        // Then
        assertThat(users).hasSize(5);
        assertThat(users).allMatch(user -> user.getName().matches("[a-zA-Z]+"));
        assertThat(users).allMatch(user -> user.getEmail().contains("@"));
        assertThat(users).allMatch(user -> user.getAge() >= 20 && user.getAge() <= 70);
    }

    /**
     * 성능 최적화된 영문 User 생성
     * UserFixtureGenerator를 통한 효율적인 데이터 생성
     */
    @Test
    void generateOptimizedEnglishUsers() {
        // Given & When
        long start = System.currentTimeMillis();

        List<User> users = generator.generateEnglishUsers(100);

        long end = System.currentTimeMillis();

        // Then
        assertThat(users).hasSize(100);
        assertThat(users).allMatch(user -> user.getName().matches("[a-zA-Z]+"));
        assertThat(users).allMatch(user -> user.getEmail().contains("@"));
        assertThat(end - start).isLessThan(1000); // 1초 이내
    }

    /**
     * 다양한 나이 범위의 User 생성
     * UserFixtureGenerator의 generateEnglishUserWithAge() 메서드 활용
     */
    @Test
    void generateUsersWithDifferentAgeRanges() {
        // Given & When
        User youngUser = generator.generateEnglishUserWithAge(18, 25);
        User middleUser = generator.generateEnglishUserWithAge(26, 50);
        User seniorUser = generator.generateEnglishUserWithAge(51, 70);

        // Then
        assertThat(youngUser.getAge()).isBetween(18, 25);
        assertThat(middleUser.getAge()).isBetween(26, 50);
        assertThat(seniorUser.getAge()).isBetween(51, 70);

        // 모든 User가 영문자만 포함하는지 확인
        assertThat(youngUser.getName()).matches("[a-zA-Z]+");
        assertThat(middleUser.getName()).matches("[a-zA-Z]+");
        assertThat(seniorUser.getName()).matches("[a-zA-Z]+");
    }

    /**
     * 대량의 영문 User 생성 테스트
     * UserFixtureGenerator의 성능과 안정성 검증
     */
    @Test
    void generateLargeNumberOfEnglishUsers() {
        // Given & When
        List<User> users = generator.generateEnglishUsers(1000);

        // Then
        assertThat(users).hasSize(1000);

        // 모든 User가 영문자만 포함하는지 확인
        assertThat(users).allMatch(user -> user.getName().matches("[a-zA-Z]+"));
        assertThat(users).allMatch(user -> user.getEmail().contains("@"));
        assertThat(users).allMatch(user -> user.getAge() >= 20 && user.getAge() <= 70);

        // 중복되지 않는 이름들인지 확인 (실제로는 중복 가능하지만 테스트 목적)
        assertThat(users.stream().map(User::getName).distinct().count()).isGreaterThan(500);
    }
}