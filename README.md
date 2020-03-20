# Jpa-Study

### JPA 구동 방식
- 설정 정보 조회
    - META-INF/persistence.xml
- 생성
    - EntityManagerFactory
- 생성
    - EntityManager
---

### 어플리케이션 개발
- @Id
    - 최소한 JPA 한테 PK가 뭔지는 알려줘야함
- EntityManagerFactory는 어플리케이션 로딩시점에 딱 하나만 만든다
- 실제 트랜잭션단위마다 EntityManager를 만들어야함
- JPA 모든 데이터변경은 트랜잭션안에서 처리되어야 한다.
---

### JPQL
- 테이블이 아닌 객체를 대상으로 검색하는 객체지향쿼리
- JPQL은 엔티티 객체를 대상으로 쿼리
- 테이블을 대상으로 쿼리를 짜지 않는다.
- 엔티티를 대상으로 쿼리를 만든다.
---

### 영속성 컨텍스트
- 엔티티 팩토리와 엔티티 매니저
- 엔티티 팩토리
    - 데이터베이스를 하나만 사용하는 애플리케이션은 일반적으로 EntityManagerFactory를 하나만 생성
    - Persistence.createEntityManagerFactory(“jpabook”)을 하면 META-INF/persistence.xml에 있는 정보를 바탕으로 EntityManagerFactory를 생성
    - EntityManagerFactory는 여러 스레드가 동시에 접근해도 안전하므로 스레드 간에 공유허용
    - EntityManager는 여러 스레드가 동시에 접근하면 동시성 문제가 발생하므로 스레드간 공유불허
    - EntityManager는 데이터베이스 연결이 꼭 필요한 시점까지 커넥션을 얻지 않음
        - 예를 들어 트랜잭션을 시작할 때 커넥션을 획득
        - 하이버네이트를 포함한 JPA 구현체들은 EntityManagerFactory를 생성할 때 커넥션풀도 만드는데 이것은 J2SE환경에서 사용하는 방법
- 객체와 관계형 데이터베이스 매핑하기
- 어플리케이션과 데이터베이스 사이에 한 계층이 존재하는것
- 영속성 컨텍스트
    - 엔티티매니저를 생성할 때 하나 만들어짐
    - 여러 엔티티 매니저가 같은 영속성 컨테
- Entity 생명주기
    - 비영속(new/transient)
        - 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
    - 영속(managed)
        - 영속성 컨텍스트에 관리되는 상태
        - 영속상태가 되었다고 해서 바로 디비에 쿼리가 날아가는게 아님(em.persist())
        - 트랜잭션을 커밋하는 시점에 디비에 쿼리가 날아감(tx.commit())
    - 준영속(detached)
        - 영속성 컨텍스트에 저장되었다가 분리된 상태
    - 삭제(removed)
        - 삭제된 상태
- 영속성 컨텍스트의 이점
    - 1차 캐시
    - 동일성(identity) 보장
    - 트랜잭션을 지원하는 쓰기 지연(쓰기 지연 SQL 저장소)
        - 트랜잭션을 커밋하는 순간 insert 쿼리가 날아감
    - 변경 감지(dirty checking)
        1. flush()할때 발생
        1. 엔티티와 스냅샷비교
            - 최초로 영속성 컨텍스트에 들어온 시점에 스냅샷을 떠둠
        1. Update SQL 생성
            - 쓰기 지연 SQL 저장소에 저장
        1. flush
        1. commit
    - 지연 로딩
    - 플러시
        - 영속성 컨텍스트를 비우지 않음
        - 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
        - 플러시 발생
            - 변경감지
            - 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
            - 쿼리를 데이터베이스에 전송
        - 플러시방법
            - em.flush() - 직접 호출
            - 트랜잭션 커밋 - 플러시 자동 호출
            - JPQL 쿼리 실행 - 플러시 자동 호출
        - 플러시 모드 옵션
            - javax.persistence.FlushModeType
                - FlushModeType.AUTO : 커밋이나 쿼리를 실행할 때 플러시(디폴트)
                - FlushModeType.COMMIT : 커밋할 때만 플러시
        - 준영속으로 만드는 방법
            - em.detach(entity)
                - 특정 엔티티만 준영속 상태로 전환
            - em.clear()
                - 영속성 컨텍스트를 완전히 초기화
            - em.close()
                - 영속성 컨텍스트를 종료
---
### 엔티티 매핑
- @Entity
    - 기본 생성자 필수(파라미터가 없는 public 또는 protected 생성자)
    - final 클래스, enum, interface, inner 클래스 사용불가
    - 저장할 필드에 final 사용불가
    - 속성 : name
        - 기본값 : 클래스 이름을 그대로 사용
- @Table
    - 엔티티와 매핑할 테이블 지정
    - name : 매핑할 테이블 이름 / 기본값 : 엔티티 이름을 사용
---

### 데이터베이스 스키마 자동 생성 
- 운영 장비에는 절대 create, create-drop, update 사용하면 안된다.
- 개발 초기 단계는 create 또는 update
- 테스트 서버는 update 또는 validate
- 스테이징과 운영 서버는 validate 또는 none
---

### 필드와 컬럼 매핑
- 매핑 어노테이션
    - @Column
        - name : 필드와 매핑할 테이블의 컬럼 이름
        - insertable, updatable : 등록 변경 가능 여부
        - nullable : null 값의 허용 여부. false로 하면 DDL 생성시에 not null 제약조건이 붙는다
        - unique : 컬럼에 unique를 붙이면 이름이 이상하게 만들어지기 때문에 이름을 지정할 수 있는 방법인 @Table(uniqueConstraints를 선호
        - columnDefinition : 데이터 베이스 컬럼 정보를 직접 줄 수 있음
        - length : 문자 길이 제약조건, String 타입에만 사용
        - precision, scale : BigDecimal 타입에서 사용
    - @Temporal : 날짜
        - LocalDate, LocalDateTime을 쓰면 안써도 된다
    - @Enumerated : enum
        - EnumType.ORDINAL : 디폴트, enum순서(정수)를 DB에 저장
        - EnumType.STRING : enum이름(String)을 DB에 저장
    - @Lob : BLOB, CLOB
        - 지정할 속성이 없다
        - 매핑할 타입이 문자면 CLOB, 나머지는 BLOB
    - @Transient : 매핑안함

---
### 기본 키 매핑
- @Id
- @GeneratedValue
    - IDENTITY
        - 기본 키 생성을 데이터베이스에 위임
        - 주로 MySQl, PostgreSQL, SQL Server, DB2에서 사용
            - ex> MySQL - AUTO_INCREAMENT
        - DB에 쿼리를 날려야 PK값을 알 수 있는데 그렇다면 em.persist()를 호출한 시점에는 영속성 컨텍스트 입장에서 pk를 알 방법이 없다. DB를 가야 pk값을 계산할 수 있기 때문
        - 따라서 IDENTITY전략은 예외적으로 persist할 때 쿼리가 날아감(원래는 트랜잭션 커밋시점에 날아감)
    - SEQUENCE
    - TABLE
---
### 연관관계 매핑 기초
- 객체를 테이블에 맞추어 데이터 중심으로 모델링하면 협력 관계를 만들 수 없다
    - 테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾는다
    - 객체는 참조를 사용해서 연관된 객체를 찾는다
    - 테이블과 객체 사이에는 이런 큰 간격이 있다
---
### 양방향 연관관계와 연관관계의 주인
- 연관관계의 주인과 mappedBy
- 객체의 양방향 관계
    - 객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개다
- 테이블의 양방향 관계
    - 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리
- 연관관계의 주인
    - 양방향 매핑 규칙
        - 객체의 두 관계중 하나를 연관괸계의 주인으로 지정
        - 연관관계의 주인만이 외래 키를 관리(등록, 수정)
        - 주인이 아닌쪽은 읽기만 가능
        - 주인은 mappedBy 속성 사용 X
        - 주인이 아니면 mappedBy 속성으로 주인 지정
- 누구를 주인으로?
    - 외래 키가 있는 곳을 주인으로 지정
    - 연관관계의 주인은 외래 키의 위치를 기준으로 정해야함
- 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자
- 연관관계 편의 메소드를 생성하자
- 양방향 매핑시에 무한 루프를 조심하자
- 양방향 매핑 정리
    - 단방향 매핑만으로도 이미 연관관계 매핑은 완료
    - 양방향 매핑은 반대 방향으로 조회기능이 추가된 것 뿐
    - JPQL에서 역방향으로 탐색할 일이 많음
    - 단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 됨
---