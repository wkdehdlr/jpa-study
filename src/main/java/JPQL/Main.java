package JPQL;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("doik1");
            em.persist(member);

            Member member1 = new Member();
            member1.setUsername("doik2");
            em.persist(member1);

            em.flush();
            em.clear();

            String query = "select function('group_concat', m.username) FROM Member m";
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            System.out.println(resultList);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
}
