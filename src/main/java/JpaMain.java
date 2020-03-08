import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
//          비영속
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloDoik");

//          영속(실제 디비에 쿼리가 안날아감)회
            em.persist(member);
//          디비가 아닌 1차캐시에서 조
            Member member1 = em.find(Member.class, 2L);

//          실제 디비에 쿼리가 날아감
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
