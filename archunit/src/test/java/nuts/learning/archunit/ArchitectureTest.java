package nuts.learning.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "nuts.learning.archunit")
class ArchitectureTest {

    /**
     * Application 클래스를 의존하는 클래스는 application, adapter에만 존재해야 한다.
     *
     * 의미: Application 레이어의 클래스들은 오직 Application 레이어 내부나 Adapter 레이어에서만 사용될 수 있다.
     * Domain 레이어에서는 Application을 사용하면 안 된다.
     *
     * 예시:
     * ✅ Adapter → Application (허용)
     * ✅ Application → Application (허용)
     * ❌ Domain → Application (금지)
     */
    @ArchTest
    void application(JavaClasses classes) {
        classes().that().resideInAPackage("..application..")
                .should().onlyHaveDependentClassesThat().resideInAnyPackage("..application..", "..adapter..")
                .check(classes);
    }

    /**
     * Application은 Adapter를 참조하면 안 된다.
     *
     * 의미: Application 레이어의 클래스들은 Adapter 레이어의 클래스들을 직접 참조하면 안 된다.
     * Application은 순수한 비즈니스 로직만 담당하고, 외부 시스템과의 연동은 Adapter가 담당한다.
     *
     * 예시:
     * ❌ Application → Adapter (금지)
     * ✅ Adapter → Application (허용)
     *
     * 위반 사례:
     * - UserService가 UserController를 참조
     * - OrderService가 EmailService를 참조
     */
    @ArchTest
    void applicationShouldNotDependOnAdapter(JavaClasses classes) {
        noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat()
                .resideInAPackage("..adapter..")
                .check(classes);
    }

    /**
     * Domain은 다른 레이어를 참조하면 안 된다.
     *
     * 의미: Domain 레이어는 가장 핵심적인 비즈니스 로직을 담당하며,
     * 다른 어떤 레이어(Application, Adapter)도 참조하면 안 된다.
     * Domain은 순수한 비즈니스 규칙만 포함해야 한다.
     *
     * 예시:
     * ❌ Domain → Application (금지)
     * ❌ Domain → Adapter (금지)
     * ✅ Application → Domain (허용)
     * ✅ Adapter → Domain (허용)
     *
     * 위반 사례:
     * - User 엔티티가 UserService를 참조
     * - Order 엔티티가 EmailService를 참조
     * - Product 엔티티가 ProductController를 참조
     */
    @ArchTest
    void domainShouldNotDependOnOtherLayers(JavaClasses classes) {
        noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..application..", "..adapter..")
                .check(classes);
    }
}