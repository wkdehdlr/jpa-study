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
            - 기본값 : true
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
    - Auto
        - 데이터베이스 방언에 따라 IDENTITY, SEQUENCE, TABLE 전략 중 하나를 자동으로 선택
        - 오라클 : SEQUENCE, MySQL : IDENTITY 등등
        
- 영속성 컨텍스트는 엔티티를 식별자 값으로 구분하므로 엔티티를 영속 상태로 만들려면 식별자 값이 반드시 있어야함
---
### 연관관계 매핑 기초
- 객체를 테이블에 맞추어 데이터 중심으로 모델링하면 협력 관계를 만들 수 없다
    - 테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾는다
    - 객체는 참조를 사용해서 연관된 객체를 찾는다
    - 테이블과 객체 사이에는 이런 큰 간격이 있다
---
### 양방향 연관관계와 연관관계의 주인
- 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리
- 엔티티를 단방향으로 매핑하면 참조를 하나만 사용
- 엔티티를 양방향 연관관계로 설정하면 객체의 참조는 둘인데 외래 키는 하나다
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
    - 연관관계의 주인은 외래 키의 위치와 관련해서 정해야지 비즈니스 중요도로 접근하면 안된
---
### 다양한 연관관계 매핑
- 단방향, 양방향
    - 테이블
        - 외래 키 하나로 양쪽 조인 가능
        - 사실 방향이라는 개념이 없음
    - 객체
        - 참조용 필드가 있는 쪽으로만 참조 가능
        - 한쪽만 참조하면 단방향
        - 양쪽이 서로 참조하면 양방향
            - 사실 단방향이 2개가 있는것
- 연관관계의 주인
    - 테이블은 외래 키 하나로 두 테이블이 연관관계를 맺음
    - 객체 양방향 관계는 A->B, B->A 처럼 참조가 2군데
    - 객체 양방향 관계는 참조가 2군데 있음. 둘중 테이블의 외래 키를 관리할 곳을 지정해야함
    - 연관관계의 주인 : 외래 키를 관리하는 참조
    - 주인의 반대편 : 외래 키에 영향을 주지 않음, 단순 조회만 가능
- 일대다 단방향 정리
    - 일대다 단방향은 일대다(1:N)에서 일(1)이 연관관계의 주인
    - 태이블 일대다 관계는 항상 다(N)쪽에 외래키가 있음
    - 객체와 테이블의 차이 때문에 반대편 테이블의 외래 키를 관리하는 특이한 구조
    - @JoinColumn을 꼭 사용해야 함. 그렇지 않으면 조인 테이블 방식을 사용함
        - 중간에 테이블을 하나 추가함
    - 일대다 단방향 단점
        - 엔티티가 관리하는 외래키가 다른 테이블에 있음
        - 연관관계 관리를 위해 추가로 Update Sql 실행
        - 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자
    - 일대다 양방향 정리
        - 이런 매핑은 공식적으로 존재 X
        - @JoinColumn(insertable = false, updatable = false)
        - 읽기 전용 필드를 사용해서 양방향 처럼 사용하는 방법
        - 다대일 양방향을 사용하자
- 일대일 관계
    - 일대일 관계는 그 반대도 일대일
    - 주 테이블이나 대상 테이블 중에 외래 키 선택 가능
        - 주 테이블에 외래 키
        - 대상 테이블에 외래 키
    - 외래 키에 데이터베이스 유니크 제약조건 추가
    - 일대일 : 주 테이블에 외래 키 단방향 
        - 다대일 단방향 관계와 유사
        - 외래키를 객체 참조와 비슷하게 사용
        - 주 테이블이 외래 키를 가지고 있으므로 주 테이블만 확인해도 대상 테이블과 연관관계가 있는지 알 수 있음
    - 일대일 : 대상 테이블에 외래 키 단방향
        - 단방향 관계는 JPA 지원 X
        - 일대일에서 일대다로 변경할 때 테이블구조를 그대로 유지할 수 있
- 다대다 관계
    - 실무에서 X
    - 관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없음
    - 연결 테이블을 추가해서 일대다, 다대일 관계로 풀어내야 함   
---
### 상속관계 매핑
- 슈퍼타입 서브타입 논리 모델을 실제 물리 모델로 구현하는 방법
    - 각각 테이블로 변환 -> 조인 전략
    - 통합 테이블로 변환 -> 단일 테이블 전략
    - 서브타입 테이블로 변환 -> 구현 클래스마다 테이블 전략
- 주요 어노테이션
    - @Inheritance(strategy = InheritanceType.XXX)
        - JOINED : 조인전략
        - SINGLE_TABLE : 단일 테이블 전략
        - TABLE_PER_CLASS : 구현 클래스마다 테이블 전략
    - @DiscriminatorColumn(name = "DTYPE")
    - @Discriminator("XXX")
- 조인 전략(기본적으로 정석)
    - 장점
        - 테이블 정규화
        - 저장공간 효율화
        - 외래 키 참조 무결성 제약조건 활용가능
    - 단점
        - 조회시 조인을 많이 사용, 성능저하
        - 조회쿼리가 복잡
        - 데이터 저장시 Insert SQL 2번 호출
- 단일 테이블 전략
    - 장점
        - 조인이 필요 없으므로 일반적으로 조회 성능이 빠름
        - 조회 쿼리가 단순함
    - 단점
        - 자식 엔티티가 매핑한 컬럼은 모두 null 허용
        - 테이블이 커질수 있으므로 상황에 따라 조회성능이 저하
- 구현 클래스마다 테이블 전략(쓰면 안된다)
    - 장점
        - 서브 타입을 명확하게 구분해서 처리할 때 효과적
        - not null 제약조건 사용 가능
    - 단점
        - 여러 자식 테이블을 함께 조회할 때 성능이 느림(UNION SQL)
        - 자식 테이블을 통합해서 쿼리하기 어려움
---
### @MappedSuperclass
- 공통 매핑정보가 필요할 때 사용
- 상속관계 매핑 X
- 엔티티 X, 테이블과 매핑 X
- 부모 클래스를 상속받는 자식 클래스에 매핑 정보만 제공
- 조회, 검색 불가
- 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장
- @Entity 클래스는 엔티티나 @MappedSuperclass로 지정한 클래스만 상속 가능
---
### 프록시
- 프록시 기초
    - em.find() vs em.getReference()
    - em.find() : 데이터베이스를 통해서 실제 엔티티 객체 조회
    - em.getReference() : 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회
- 프록시 특징
    - 실제 엔티티를 상속 받아서 만들어짐
    - 실제 클래스와 겉 모양이 같다
    - 사용하는 입장에서는 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면 됨
    - 프록시 객체는 실제 객체의 참조(target)를 보관
    - 프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드 호출
- 프록시 객체 초기화
    - 1.getName()
    - 2.텅텅빈 Proxy를 초기화 요청
    - 3.JPA가 영속성 컨텍스트에 요청
    - 4.영속성 컨텍스트가 DB를 조회해서 실제 객체 가져오고 연결해줌
- 프록시 특징
    - 처음 사용할 때 한번만 초기화
    - 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것이 아니라 초기화되면
      프록시 객체를 통해서 실제 엔티티에 접근 가능
    - 프록시 객체는 원본 엔티티를 상속받음, 따라서 타입 체크시 주의해야함(==비교 대신 instance of 사용)
    - em.getReference()후에 em.find()를 하면 실제 엔티티가 아니라 Proxy가 반환
    - 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
        - 이미 영속성 컨텍스트에 있는데 굳이 프록시로 만들어서 줄 이유가 없음(성능상)
        - JPA 한 트랙잭션안에서 같은거에 대해 보장을 해줌
    - 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화 하면 문제 발생
        - 하이버네이트는 org.hibernate.LazyInitializationException 예외를 발생
- 프록시 확인
    - 프록시 인스턴스 초기화 여부 확인
        - PersistenceUnitUtil.isLoaded(Object entity)
    - 프록시 클래스 확인 방법
        - entity.getClass().getName() 출력
    - 프록시 강제 초기화
        - org.hibernate.Hibernate.initialize(entity);
    - JPA 표준은 강제 초기화 없음
        - 강제호출 : member.getName()
---
### 즉시 로딩과 지연 로딩
- 지연로딩
    - FetchType.Lazy
    - 프록시로 가져옴
    - 실제 사용하는 시점에 초기화
- 즉시로딩
    - FetchType.EAGER
- 프록시와 즉시로딩 주의
    - 가급적 지연 로딩만 사용(특히 실무에서)
        - 일단 다 Lazy로 깔고 필요하면 설정
    - 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
    - 즉시로딩은 JPQL에서 N+1 문제를 일으킨다
        - 예를 들어 Lazy일 때 find는 내부적으로 최적화가 되어있어서 Member를 find할 때 Team도 join해서 한번에 가져오는 반면 em.createQuery로 select m from Member m 하면
          createQuery가 스트링 그대로 번역되서 일단 당장 Member만 select하는 쿼리가 나가고 현재 Team이 EAGER니까 team을 채우기 위한 쿼리가 또 나간다
          member마다 다 다른 team이면 member만큼 team 쿼리가 발생(이게 N+1)
    - @ManyToOne, @OneToMany는 기본값이 즉시로딩
    - @OneToMany, @ManyToMany는 기본이 지연로딩
- 실무 지연 로딩 활용
    - 모든 연관관계에 지연 로딩을 사용해라
    - 실무에서 즉시 로딩을 사용하지 마라
    - JPQL fetch 조인이나, 엔티티 그래프 기능을 사용해라
    - 즉시 로딩은 상상하지 못한 쿼리가 나간다
---
### 영속성 전이 : CASCADE
- 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티 함께 영속 상태로 만들고 싶을 때
---
### 고아객체
- 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
- 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제하는 기능
- **참조하는 곳이 하나일 때 사용해야함**
- **특정 엔티티가 개인 소유할 때 사용**
- CasecadeType.ALL + orphanRemoval=true
    - 스스로 생명주기를 관리하는 엔티티는 em.persist()로 영속화, em.remove()로 제거
    - 두 옵션을 모두 활성화 하면 부모 엔티티를 통해서 자식의 생명주기를 관리할 수 있음
---
### 기본값 타입
- JPA의 데이터 타입분류
    - 엔티티 타입
        - @Entity로 정의하는 객체
        - 데이터가 변해도 식별자로 지속해서 추적 가능
        - ex) 회원 엔티티의 키나 나이 값을 변경해도 식별자로 인식 가능
    - 값 타입
        - int, Integer, String처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체
        - 식별자가 없고 값만 있으므로 변경시 추적 불가
        - 기본값 타입
            - 자바 기본 타입(int, double)
            - 래퍼 클래스(Integer, Long)
            - String
        - 임베디드 타입(embedded type, 복합 값 타입)
        - 컬렉션 값 타입(collection value type)
        - 생명주기를 엔티티에 의존
            - ex) 회원을 삭제하면 이름, 나이 필드도 함께 삭제
        - 값 타입은 공유하면 X
            - ex) 회원 이름 변경시 다른 회원의 이름도 함께 변경되면 안됨
            - 자바의 기본 타입은 절대 공유 안됨
---
### 임베디드 타입
- 새로운 값 타입을 직접 정의할 수 있음
- JPA는 임베디드 타입이라함
- 주로 기본 값 타입을 모아서 만들어서 복합 값 타입이라고도 함
- int, String과 같은 값 타입
- 임베디드 타입의 장점
    - 재사용
    - 높은 응집도
    - Period.isWork()처럼 해당 값 타입만 사용하는 의미 있는 메소드를 만들 수 있음
    - 임베디드 타입을 포함한 모든 값 타입은 값 타입을 소유한 엔티티의 생명주기에 의존함
- 임베디드 타입은 엔티티의 값일 뿐이다
- **임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.**
- 객체와 테이블을 아주 세밀하게 매핑하는 것이 가능
- 잘 설계한 ORM 어플리케이션은 매핑한 테이블의 수보다 클래스의 수가 더 많음
- @AttributeOverride
    - 한 엔티티에서 같은 값 타입을 사용하면?
    - 컬럼 명이 중복됨
    - @AttributeOverrides, @AttributeOverride를 사용해서 컬럼명 속성을 재정의함
---
### 값 타입과 불변객체
- 값 타입 공유 참조
    - 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험함
    - 부작용 발생
- 값 타입 복사
    - 값 타입의 실제 인스턴스인 값을 공유하는 것은 위험
    - 대신 값(인스턴스)를 복사해서 사용
- 객체 타입의 한계
    - 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있음
    - 문제는 임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본타입이 아니라 객체 타입이다
    - 자바 기본 타입에 값을 대입하면 값을 복사
    - 객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없음
    - 객체의 공유 참조는 피할 수 없다
- 불변객체
    - 객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
    - 값 타입은 불변객체(immutable object)로 설계해야함
    - 불변객체 : 생성 시점 이후 절대 값을 변경할 수 없는 객체
    - 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 됨
    - 참고 : Integer, String은 자바가 제공하는 대표적인 불변 객체
---
### 값 타입 비교
- 인스턴스가 달라도 그 안에 값이 같으면 같은 것으로 봄
- 동일성 비교
- 동등성 비교
- 값 타입의 equals를 재정의
---
### 값 타입 컬렉션
- 값 타입을 하나 이상 저장할 때 사용
- @ElementCollection, @CollectionTable 사용
- 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다
- 컬렉션을 저장하기 위한 별도의 테이블이 필요
- 값 타입 컬렉션도 지연 로딩 전략 사용
- 값 타입 컬렉션은 영속성 전이 + 고아 객체 제거 기능을 필수로 가진다고 할 수 있음
- 값 타입 컬렉션 제약사항
    - 값 타입은 엔티티와 다르게 식별자 개념이 없다
    - 값은 변경하면 추적이 어렵다
    - 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장
    - 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야함 : **null 입력 X, 중복 저장 X**
- 값 타입 컬렉션 대안
    - 실무에서는 **값 타입 컬렉션 대신에 일대다 관계를 고려**
    - 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용
---
### JPQL
- JPQL은 테이블이 아닌 엔티티를 대상으로 쿼리
- JPQL 문법
    - 엔티티와 속성은 대소문자 구분 O
    - JPQL 키워드는 대소문자 구분 X
    - 엔티티 이름 사용, 테이블 이름 아님
    - **별칭 필수**
    - TypeQuery, Query
        - TypeQuery : 반환 타입이 명확할 때
        - Query : 반환 타입이 명확하지 않을 때
    - 결과 조회 API
        - query.getResultList(): 결과가 하나 이상일 때
            - 결과가 없으면 빈 리스트 반환
        - query.getSingleResult(): 결과가 정확히 하나, 단일 객체 반환
            - 결과가 없으면 : javax.persistence.NoResultException
            - 둘 이상이면 : javax.persistence.NonUniqueResultException
    - 파라미터 바인딩 - 이름 기준, 위치 기준
        - **이름기준으로 사용할것을 추천**
---
### 프로젝션
- select 절에 조회할 대상을 지정하는 것
- 프로젝션 대상 : 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자등 기본 데이터 타입)
- SELECT m FROM Member m : 엔티티 프로젝션
- SELECT m.team FROM Member m : 엔티티 프로젝션
- SELECT m.address FROM Member m : 임베디드 타입 프로젝션
- SELECT m.username, m.age FROM Member m : 스칼라 타입 프로젝션
- DISTINCT로 중복제거
- 여러 값 조회
    - SELECT m.username, m.age FROM Member m
    - 1.Query 타입으로 조회
        - List result
    - 2.Object[] 타입으로 조회
        - List<Object[]> result
    - 3.new 명령어로 조회
        - 단순 값을 DTO로 바로 조회
        - 패키지 명을 포함한 전체 클래스 명 입력
        - 순서와 타입이 일치하는 생성자 필요
---
### 페이징 API
- JPA는 페이징을 다음 두 API로 추상화
- setFirstResult(int startPosition): 조회 시작 위치(0부터 시작)
- setMaxResults(int maxResult) : 조회할 데이터 개수
---
### JOIN
- 내부 조인
    - SELECT m FROM Member m [INNER] JOIN m.team t
- 외부 조인
    - SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
- 세타 조인
    - SELECT count(m) FROM Member m, Team t where m.username = t.name 
- ON 절
    - ON절을 활용한 조인(JPA 2.1부터 지원)
        - 조인 대상 필터링
            - SELECT m,t FROM Member m LEFT JOIN m.team t ON t.name = 'A'
        - 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터)
            - SELECT m,t FROM Member m LEFT JOIN Team t ON m.username = t.name
---
### 서브쿼리
- 서브 쿼리 지원 함수
    - [NOT] EXISTS (subquery) : 서브쿼리에 결과가 존재하면 참
        - {ALL | ANY | SOME} (subquery)
        - ALL 모두 만족하면 참
        - ANY, SOME : 같은 의미, 조건을 하나라도 만족하면 참
        - SELECT m from Member m where EXISTS (SELECT t FROM m.team t WHERE t.name = '팀A')
    - [NOT] IN (subquery) : 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참
- JPA 서브 쿼리 한계
    - JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
    - SELECT 절도 가능(하이버네이트에서 지원)
    - **FROM 절의 서브 쿼리는 현재 JPQL에서 불가능**
        - **조인으로 풀 수 있으면 풀어서 해결**
---
### JPQL 타입 표현
- 문자 : 'HELLO','She''s'
- 숫자 : 10L(Long), 10D(Double), 10F(Float)
- Boolean : TRUE, FALSE
- ENUM : JPQL.MemberType.Admin(패키지명 포함)
- 엔티티타입 : TYPE(m) = Member(상속관계에서 사용)
- 기타
    - EXISTS, IN
    - AND, OR, NOT
    - =, >, >=, <=, <>
    - BETWEEN, LIKE, IS NULL
---
### 조건식
- COALESCE : 하나씩 조회해서 null이 아니면 반환
    - SELECT COALESCE(m.username, '이름 없는 회원') FROM Member m
- NULLIF : 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
    - SELECT NULLIF(m.username, '관리자') FROM Member m
---
### JPQL 기본함수
- CONCAT
- SUBSTRING
- TRIM
- LOWER
- UPPER
- LENGTH
- LOCATE
- ABS, SQRT, MOD
- SIZE, INDEX(JPA 용도)
- 사용자 정의 함수
    - 하이버네이트는 사용전 방언에 추가해야 한다
    - 사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록한다
    - SELECT function('group_contat', m.username) FROM Member m
---
### 경로 표현식
- 상태필드 : 단순히 값을 저장하기 위한 필드(m.username)
    - 경로 탐색의 끝, 더이상 탐색 불
- 연관필드 : 연관관계를 위한 필드
    - **명시적 조인 사용 권장!!! 묵시적 조인은 쿼리튜닝이 힘듬**
    - 단일 값 연관 필드 : 대상이 엔티티(m.team) @ManyToOne, @OneToOne가
        - 묵시적 내부 조인(inner join)발생, 더 탐색 가능
    - 컬렉션 값 연관 필드 : 대상이 컬렉션(m.orders) @OneToMany, @ManyToMany
        - 묵시적 내부 조인 발생하지만 컬렉션에서 더 이상 탐색 불가
        - FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
            - SELECT t.members.username FROM Team t (불가)
            - SELECT m.username FROM Team t JOIN t.members m
---
### 페치조인
- **실무에서 정말 중요함**
- N + 1 쿼리 문제를 해결
- 페치 조인은 기본적으로 select * (다 가져옴)로 동작
- SQL 조인 종류 X
- JPQL에서 성능 최적화를 위해 제공하는 기능
- 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회하는 기능
- join fetch 명령어 사용
- 페치조인 [LEFT [OUTER] | INNER] JOIN FETCH 조인경로
- 지연로딩으로 걸어도 페치조인이 우선
- 페치 조인과 DISTINCT
    - SQL에 DISTINCT를 추가하지만 데이터가 다르므로 SQL 결과에서 중복제거 실패
    - 추가로 애플리케이션에서 중복 제거시도
    - 같은 식별자를 가진 엔티티 제거
- 페치 조인과 일반 조인의 차이
    - 페치 조인을 사용할 때만 연관된 엔티티도 함께 조회(즉시 로딩)
- 페치 조인의 특징과 한계
    - 페치 조인 대상에는 별칭을 줄 수 없다
    - JPA 설계원칙에 어긋남 
        - 객체 그래프를 탐색할 때 기본적으로 OneToMany 컬렉션을 모두 가져오는걸 전제로함
    - 둘 이상의 컬렉션은 페치조인 할 수 없다
    - 컬렉션을 페치 조인하면 페이징 API를 사용할 수 없다
---
### 엔티티 직접 사용
- 기본 키 사용
    - SELECT m from Member m where m = :member
- 외래 키 사용
    - SELECT m from Member m where m.team = :team
---
### Named 쿼리
- 미리 정의해서 이름을 부여해두고 사용하는 JPQL
- 정적 쿼리
- 어노테이션, XML에 정의
- 애플리케이션 로딩 시점에 초기화 후 재사용
- **애플리케이션 로딩 시점에 쿼리를 검증**
- XML이 우선권을 가짐
---
### 벌크연산
- 쿼리 한 번으로 여러 테이블 로우 변경(엔티티)
- **executeUpdate()의 결과는 영향받은 엔티티 수 반환**
- UPDATE, DELETE 지원
- INSERT(insert into ...select)
- 벌크 연산 주의
    - 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리
    - 벌크 연산을 먼저 실행
    - **벌크 연산 수행 후 영속성 컨텍스트 초기**
