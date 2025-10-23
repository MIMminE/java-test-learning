package nuts.learning.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@DisplayName("레이어 의존성 테스트")
class LayerDependencyTest {

    private static JavaClasses importedClasses;

    @BeforeAll
    static void setUp() {
        importedClasses = new ClassFileImporter()
                .importPackages("nuts.learning.archunit");
    }

    @Test
    @DisplayName("헥사고날 아키텍처 레이어 규칙")
    void hexagonalArchitectureLayerRules() {

        Architectures.LayeredArchitecture architecture = layeredArchitecture().consideringAllDependencies()
                .layer("Domain").definedBy("..domain..")
                .layer("Application").definedBy("..application..")
                .layer("Adapter").definedBy("..adapter..");


        architecture.check(importedClasses);
    }

    @Test
    @DisplayName("도메인은 다른 레이어를 참조하면 안 된다")
    void domainShouldNotDependOnOtherLayers() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..application..", "..adapter..");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Application은 Adapter를 참조하면 안 된다")
    void applicationShouldNotDependOnAdapter() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat()
                .resideInAPackage("..adapter..");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Domain은 프레임워크를 의존하면 안 된다")
    void domainShouldNotDependOnFrameworks() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "jakarta.persistence..",
                        "org.springframework..",
                        "org.hibernate.."
                );

        rule.check(importedClasses);
    }
}