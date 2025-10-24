# FixtureMonkey 커스텀 생성기 코드북

## 📋 개요
**FixtureMonkey**를 활용하여 **커스텀 픽스쳐 생성기**를 만들고 활용하는 패턴 모음입니다.
재사용 가능한 테스트 데이터 생성기를 구축하여 다양한 프로젝트에서 활용할 수 있습니다.

## 🏗️ 커스텀 생성기 장점
- **재사용성**: 여러 테스트에서 동일한 생성기 활용
- **일관성**: 통일된 테스트 데이터 생성 규칙

## 🧪 커스텀 생성기 구현 패턴

### 1. UserFixtureGenerator 클래스 구조
```java
/**
 * 영문 데이터만 생성하는 테스트용 User 생성기
 * FixtureMonkey와 Jqwik을 활용하여 영문자로만 구성된 테스트 데이터를 생성합니다.
 */
public class UserFixtureGenerator {
    
    private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    /**
     * 기본 영문 User 생성
     * - name: 영문자 3-10글자
     * - email: 영문자 8-15글자 + @gmail.com
     * - age: 20-70세
     */
    public User generateEnglishUser() {
        return fixtureMonkey.giveMeBuilder(User.class)
                .set("name", Arbitraries.strings()
                        .alpha()  // 영문자만 생성 (필터링 없음)
                        .ofMinLength(3)
                        .ofMaxLength(10))
                .set("email", Arbitraries.strings()
                        .alpha()  // 영문자만 생성 (필터링 없음)
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
     * 특정 나이 범위의 영문 User 생성
     */
    public User generateEnglishUserWithAge(int minAge, int maxAge) {
        return fixtureMonkey.giveMeBuilder(User.class)
                .set("name", Arbitraries.strings()
                        .alpha()  // 영문자만 생성 (필터링 없음)
                        .ofMinLength(3)
                        .ofMaxLength(10))
                .set("email", Arbitraries.strings()
                        .alpha()  // 영문자만 생성 (필터링 없음)
                        .ofMinLength(8)
                        .ofMaxLength(15) + "@gmail.com")
                .set("age", Arbitraries.integers().between(minAge, maxAge))
                .set("createdAt", LocalDateTime.now().minusDays((int) (Math.random() * 365)))
                .sample();
    }
}
```

### 2. 커스텀 생성기 활용 테스트
```java
/**
 * UserFixtureGenerator를 활용한 FixtureMonkey + Jqwik 코드북
 * 
 * 주요 기능:
 * - UserFixtureGenerator를 통한 객체 생성
 * - 영문자만 생성하는 문자열 필터링
 * - 조건부 데이터 생성
 * - 성능 최적화된 데이터 생성
 */
@SpringBootTest
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
}
```

### 3. 고급 커스텀 생성기 패턴
```java
/**
 * 다양한 도메인별 커스텀 생성기 예시
 */
public class DomainFixtureGenerator {
    
    private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    /**
     * 주문 생성기
     */
    public Order generateOrder() {
        return fixtureMonkey.giveMeBuilder(Order.class)
                .set("productName", Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(15))
                .set("price", Arbitraries.bigDecimals().between(new BigDecimal("10.00"), new BigDecimal("1000.00")))
                .set("status", Arbitraries.of(OrderStatus.values()))
                .sample();
    }

    /**
     * 주소 생성기
     */
    public Address generateAddress() {
        return fixtureMonkey.giveMeBuilder(Address.class)
                .set("city", Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(15))
                .set("country", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                .set("street", Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(20))
                .set("zipCode", Arbitraries.strings().alpha().ofMinLength(5).ofMaxLength(5))
                .sample();
    }

    /**
     * 복합 객체 생성기
     */
    public User generateUserWithOrders(int orderCount) {
        return fixtureMonkey.giveMeBuilder(User.class)
                .set("name", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                .set("email", Arbitraries.strings().alpha().ofMinLength(8).ofMaxLength(15) + "@gmail.com")
                .set("age", Arbitraries.integers().between(20, 70))
                .set("address", generateAddress())
                .set("orders", generateOrders(orderCount))
                .set("createdAt", LocalDateTime.now().minusDays((int) (Math.random() * 365)))
                .sample();
    }

    private List<Order> generateOrders(int count) {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            orders.add(generateOrder());
        }
        return orders;
    }
}
```

## 🚀 커스텀 생성기 활용 패턴

### 1. 기본 활용법
```java
// 생성기 인스턴스 생성
private final UserFixtureGenerator generator = new UserFixtureGenerator();

// 기본 영문 User 생성
User user = generator.generateEnglishUser();

// 특정 나이 범위 User 생성
User youngUser = generator.generateEnglishUserWithAge(18, 25);

// 여러 User 생성
List<User> users = generator.generateEnglishUsers(5);
```

### 2. 고급 활용법
```java
// 다양한 나이 범위의 User 생성
User youngUser = generator.generateEnglishUserWithAge(18, 25);
User middleUser = generator.generateEnglishUserWithAge(26, 50);
User seniorUser = generator.generateEnglishUserWithAge(51, 70);

// 대량 데이터 생성
List<User> users = generator.generateEnglishUsers(1000);

// 성능 테스트
long start = System.currentTimeMillis();
List<User> users = generator.generateEnglishUsers(100);
long end = System.currentTimeMillis();
assertThat(end - start).isLessThan(1000);
```

### 3. 도메인별 생성기 조합
```java
// 여러 도메인 생성기 조합
private final UserFixtureGenerator userGenerator = new UserFixtureGenerator();
private final DomainFixtureGenerator domainGenerator = new DomainFixtureGenerator();

@Test
void 복합_도메인_테스트() {
    // User와 관련된 Order 생성
    User user = userGenerator.generateEnglishUser();
    Order order = domainGenerator.generateOrder();
    
    // 복합 객체 생성
    User userWithOrders = domainGenerator.generateUserWithOrders(3);
    
    assertThat(userWithOrders.getOrders()).hasSize(3);
}
```

## 🔧 의존성 설정

### 1. Gradle (build.gradle.kts)
```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    
    // FixtureMonkey + Jqwik (호환 버전)
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter:1.1.15")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-jakarta-validation:1.1.15")
    testImplementation("net.jqwik:jqwik:1.7.3")  // 호환 버전
    
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
```


## 🎯 핵심 개념

### 1. FixtureMonkey 설정
```java
private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
        .plugin(new JakartaValidationPlugin())
        .defaultNotNull(true)
        .build();
```

### 2. Jqwik Arbitraries 활용
```java
// 영문자만 생성 (성능 최적화)
Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10)

// 숫자 범위
Arbitraries.integers().between(20, 70)

// BigDecimal 범위
Arbitraries.bigDecimals().between(new BigDecimal("10.00"), new BigDecimal("1000.00"))
```
