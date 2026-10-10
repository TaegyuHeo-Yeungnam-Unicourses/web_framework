# 은행 계좌 관리 과제

## 제출 및 실행
- `bank/`가 제출 대상 Java 패키지이며, `TestDriver.java`는 제공된 원본과 동일합니다.
- `pom.xml`과 `verification/`은 실행·검증용 보조 자료입니다.
- JDK 17과 Maven이 있는 환경에서 압축을 풀고 이 파일이 있는 폴더에서 실행합니다.
  ```sh
  mvn compile exec:java
  ```
- 기존 수업 프로젝트를 사용한다면 소스 폴더에 `bank/`를 복사하고 Spring Context/AOP, AspectJ Weaver, Lombok 의존성을 설정합니다. IDE의 Lombok annotation processing을 활성화합니다.
- Maven 실행 자체는 이 환경에 Maven이 없어 검증하지 않았습니다. 동일 버전의 실제 의존성 JAR을 사용하여 Java 17 컴파일러로 컴파일하고 실행했습니다.

## 자료별 구현 대응
근거: home_work.png의 bank 패키지 구현·ZIP 제출·전체 기능 테스트 지시, 실습2.pdf의 슬라이드 Page 6~14, 제공된 TestDriver.java.

| 자료의 슬라이드 | 구현 |
|---|---|
| Page 6~7 | Account, CreateReq, 두 HashMap을 갖는 AccountDAO, 조회·삽입·갱신 |
| Page 8 | AccountCreateService: 생성자 DI, owner 중복 거부 |
| Page 9 | TransactionService: 생성자 DI, 입출금 및 두 예외 |
| Page 10 | ReportTool, printf 기반 BasicReport |
| Page 11 | 개별·전체 조회, setter DI, 없는 사용자 오류 출력 |
| Page 12 | Java 설정 Config2, 원본 TestDriver 호환 |
| Page 13 | @Autowired setter, @NoArgsConstructor, @Component, SummaryReport 및 @Qualifier |
| Page 14 | 생성·거래 서비스의 @Before 호출 시각, @AfterReturning 반환값 |

PDF의 최종 단계에 맞춰 두 조회 서비스는 SummaryReport를 사용합니다. BasicReport도 Bean으로 등록되어 있으며 이름·사용자 ID까지 출력합니다. 상세 출력으로 변경하려면 조회 서비스 setter의 qualifier를 `basicReport`로 변경합니다.

## 검증
- `verification/commands.txt`: 원본 드라이버 입력 시나리오.
- `verification/driver-output.txt`: 실제 원본 드라이버 실행 기록.
- `verification/BankVerification.java`: 직접 실행 가능한 별도 검증 코드. 제출 패키지 밖에 두었습니다.
- `verification/test-results.txt`: 25개 검증 통과 기록.
- 검증 내용: 빈 계좌 목록, 생성·중복 방지·순차 ID, 입출금·전액 출금, 없는 계좌 예외, 잔액 부족 및 잔액 유지, DAO 갱신 성공과 잘못된 매핑 거부, 개별·전체 조회, 두 출력 방식, 실제 Spring DI와 AOP. 예외 발생 시 AfterReturning 로그가 출력되지 않는 것도 확인했습니다.

## 구현 범위
- PDF에 맞춰 메모리의 HashMap에 저장하므로 종료 후 계좌는 유지되지 않습니다.
- 최초 계좌 ID는 1입니다. 중복 생성 실패 시 ID를 소비하지 않습니다.
- 금액은 자료와 동일하게 int이며, 명세에 없는 음수 금액·오버플로 방지 정책은 추가하지 않았습니다.
- TestDriver 원본을 보존했으므로 명령은 안내된 인자 수와 정수 형식으로 입력하고, 종료는 exit 또는 quit을 사용합니다.
- 학번·성명 또는 지정 ZIP 파일명은 제공 자료에 요구되어 있지 않아 임의로 추가하지 않았습니다.
