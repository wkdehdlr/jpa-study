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
            member.setUsername("doik");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List resultList = em.createQuery("select m.address from Order m")
                    .getResultList();
            Object o = resultList.get(0);
            Object[] res = (Object[])o;
            System.out.println(res[0]);
            System.out.println(res[1]);

            List<Object[]> resultList2 = em.createQuery("select m.address from Order m")
                    .getResultList();

            Object[] result = resultList2.get(0);
            System.out.println(result[0]);
            System.out.println(result[1]);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
}
