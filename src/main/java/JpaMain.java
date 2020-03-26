import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try{
            Member member = new Member();
            member.setName("doik");
            em.persist(member);

//            Member member2 = new Member();
//            member2.setName("doik2");
//            em.persist(member2);

            em.flush();
            em.clear();

            Member findMember1 = em.find(Member.class, member.getId());
            Member findMember2 = em.getReference(Member.class, member.getId());
//            Member findMember2 = em.getReference(Member.class, member2.getId());

//            Hibernate.initialize(findMember2);

//            System.out.println("m1 == m2" + (findMember1.getClass() == findMember2.getClass()));
//            System.out.println(findMember2.getClass());
//            System.out.println("findMember.username = " + findMember2.getName());

            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
