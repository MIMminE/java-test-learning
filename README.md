# Java 테스트 학습 코드북 📚

## 📋 개요
Java 테스트 및 개발 관련 다양한 학습 자료와 코드 예제를 제공하는 저장소입니다.
ArchUnit, FixtureMonkey, JUnit 등 실무에서 유용한 테스트 프레임워크와 도구들에 대한
재사용 가능한 패턴과 모범 사례를 담고 있습니다.

## 📚 학습 자료

### 1. [ArchUnit 코드북](./archunit/README.md)
아키텍처 검증 테스트 패턴 모음
- Clean Architecture, Hexagonal Architecture, Layered Architecture 검증
- 레이어 의존성 검증
- 프레임워크 의존성 검증
- 네이밍 컨벤션 검증

**주요 내용:**
- Domain, Application, Adapter 계층 의존성 규칙
- 프레임워크 의존성 제한
- 컨트롤러, 서비스, 리포지토리 네이밍 규칙

### 2. [FixtureMonkey 커스텀 생성기 코드북](./fixture/README.md)
테스트 데이터 생성기 구현 패턴
- FixtureMonkey + Jqwik 활용
- 커스텀 픽스처 생성기 구현
- 영문 데이터 생성 최적화

**주요 내용:**
- UserFixtureGenerator 구현 패턴
- 조건부 데이터 생성
- 대량 테스트 데이터 생성
- 도메인별 생성기 조합

### 3. [JUnit 학습 자료](./junit/)
JUnit 테스트 프레임워크 학습 자료
- 단위 테스트 작성
- 통합 테스트 패턴
- 테스트 더블 활용

### 4. [GitHub Copilot Agent 가이드](./copilot-agent/README.md)
AI 기반 코딩 어시스턴트 활용 가이드
- 코드 작성 및 생성
- 테스트 코드 자동 생성
- 리팩토링 및 코드 개선
- 버그 수정 및 디버깅
- 문서화 자동화

**주요 내용:**
- Copilot Agent 10가지 주요 기능
- 실전 활용 패턴 (TDD, 레거시 코드 현대화 등)
- 지원하는 프로그래밍 언어 및 프레임워크
- 모범 사례 및 효율적인 사용 팁

## 🎯 사용 방법

각 디렉토리의 README.md 파일을 참고하여 학습하세요.
각 코드북은 독립적으로 사용할 수 있으며, 실무 프로젝트에 바로 적용 가능한
코드 패턴과 예제를 제공합니다.

## 🛠️ 기술 스택

- **Java 17+**
- **Spring Boot 3.x**
- **Gradle**
- **JUnit 5**
- **ArchUnit**
- **FixtureMonkey**
- **Jqwik**
- **Lombok**

## 📖 학습 순서 추천

1. **JUnit 기초** → 테스트 작성의 기본
2. **FixtureMonkey** → 효율적인 테스트 데이터 생성
3. **ArchUnit** → 아키텍처 품질 관리
4. **GitHub Copilot Agent** → AI를 활용한 생산성 향상

## 🤝 기여

이 저장소는 학습 목적으로 만들어졌으며, 개선 사항이나 추가하고 싶은 내용이 있다면
이슈나 PR을 통해 기여해주세요.

## 📝 라이선스

이 프로젝트의 라이선스 정보는 별도로 명시되지 않았습니다.

---

**Happy Testing! 🚀**
