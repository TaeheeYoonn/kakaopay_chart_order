# Stock Ranking API

## 목차
1. [프로젝트 개요](#프로젝트-개요)
2. [기술 스택](#기술-스택)
3. [설계 내용과 이유](#설계-내용과-이유)
4. [핵심 문제해결 전략](#핵심-문제해결-전략)
5. [프로젝트 구조](#프로젝트-구조)
6. [프로젝트 설정 및 실행 방법](#프로젝트-설정-및-실행-방법)
7. [API 테스트 방법](#api-테스트-방법)
8. [H2 Console 접속 가이드](#h2-console-접속-가이드)

## 프로젝트 개요

이 프로젝트는 실시간 주식 순위 정보를 제공하는 API 서비스입니다. 사용자는 인기, 상승, 하락, 거래량 등의 태그를 기준으로 주식 정보를 조회할 수 있습니다.

## 기술 스택

- Java 17
- Spring Boot 3.3.2
- Spring Data JPA
- H2 Database (개발 및 테스트용)
- Gradle
- MapStruct (DTO 매핑)
- JUnit 5 (테스트)

## 설계 내용과 이유

1. **계층 구조**: Controller - Service - Repository 패턴 사용

2. **DTO 패턴**: 엔티티와 API 응답을 분리하여 데이터 전송 시 유연성을 확보하고, 불필요한 데이터 노출을 방지

3. **MapStruct**: 반복적인 매핑 코드를 줄이고, 타입 안전성을 보장하며 성능을 최적화

4. **예외 처리**: GlobalExceptionHandler를 구현하여 일관된 에러 응답 형식 제공

5. **페이징**: 대량의 데이터를 효율적으로 처리하기 위해 페이징 구현

6. **CSV 데이터 로딩**: 초기 데이터를 CSV 파일에서 로드하여 개발 및 테스트의 편의성 제공

## 핵심 문제해결 전략

1. **실시간 데이터 업데이트**:
    - 랜덤 데이터 생성 메서드를 구현하여 실시간 데이터 변경을 시뮬레이션합니다.
    - 실제 환경에서는 외부 API나 메시징 시스템을 통한 실시간 데이터 동기화를 고려해야 합니다.

2. **효율적인 정렬 및 조회**:
   - JPA의 쿼리 메서드를 활용하여 데이터베이스 레벨에서 효율적인 정렬을 수행합니다.
   - 조회 성능 최적화를 위해 `viewCount`, `priceChange`, `volume` 컬럼에 인덱스를 적용했습니다.

3. **동시성 처리**:
    - @Transactional 어노테이션을 사용하여 데이터 일관성을 유지합니다.
    - 대량의 데이터 업데이트 시 배치 처리를 고려합니다.

4. **확장성**:
    - 인터페이스 기반 설계로 새로운 기능 추가가 용이하도록 했습니다.
    - 마이크로서비스 아키텍처로의 전환을 고려한 모듈화된 설계를 채택했습니다.

## 프로젝트 구조

```
stock-ranking-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── kakaopaystocks/
│   │   │           ├── controller/
│   │   │           ├── service/
│   │   │           ├── repository/
│   │   │           ├── entity/
│   │   │           ├── dto/
│   │   │           ├── exception/
│   │   │           └── util/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── stocks.csv
│   └── test/
│       └── java/
│           └── com/
│               └── kakaopaystocks/
│                   ├── controller/
│                   ├── service/
│                   └── util/
└── build.gradle
```

## 프로젝트 설정 및 실행 방법

### Git Clone 및 프로젝트 설정

1. 프로젝트를 클론합니다:
   ```bash
   git clone https://github.com/kakaopayseccoding-server/202407-taehee0227-naver.com.git
   cd 202407-taehee0227-naver.com
   ```

2. Gradle을 사용하여 프로젝트를 빌드합니다:
   ```bash
   ./gradlew clean build
   ```

### 애플리케이션 실행

빌드가 완료된 후, 아래 명령어로 애플리케이션을 실행할 수 있습니다:

```bash
java -jar build/libs/kakaotest-0.0.1-SNAPSHOT.jar
```

애플리케이션은 기본적으로 8080 포트에서 실행됩니다.

## API 테스트 방법

API 테스트 curl 명령어:

1. 인기 주식 조회:
   ```bash
   curl -X GET "http://localhost:8080/api/stocks/popular?page=0&size=120"
   ```

2. 상승 주식 조회:
   ```bash
   curl -X GET "http://localhost:8080/api/stocks/rising?page=0&size=120"
   ```

3. 하락 주식 조회:
   ```bash
   curl -X GET "http://localhost:8080/api/stocks/falling?page=0&size=120"
   ```

4. 거래량 기준 주식 조회:
   ```bash
   curl -X GET "http://localhost:8080/api/stocks/volume?page=0&size=120"
   ```

5. 랜덤 데이터 업데이트:
   ```bash
   curl -X POST "http://localhost:8080/api/stocks/update-random"
   ```

## H2 Console 접속 가이드

1. 애플리케이션이 실행 중인 상태에서 웹 브라우저를 열고 아래 URL에 접속합니다:
   ```
   http://localhost:8080/h2-console
   ```

2. 로그인 화면에서 다음 정보를 입력합니다:
    - JDBC URL: `jdbc:h2:mem:testdb`
    - User Name: `sa`
    - Password: `password`

3. "Connect" 버튼을 클릭하여 H2 Console에 접속합니다.

4. 콘솔에서 SQL 쿼리를 실행하여 데이터베이스를 직접 조회하고 조작할 수 있습니다.

주의: H2 Console은 개발 및 테스트 목적으로만 사용해야 하며, 프로덕션 환경에서는 보안상의 이유로 비활성화해야 합니다.
