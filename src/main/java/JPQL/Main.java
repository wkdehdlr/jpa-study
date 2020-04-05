package JPQL;

import javax.persistence.*;

public class Main {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("doik");
            em.persist(member);

//            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//            Query query1 = em.createQuery("select m.username, m.age from Member m");

            Member singleResult = em.createQuery("select m from Member m where m.username like :username", Member.class)
                    .setParameter("username", "doik")
                    .getSingleResult();

            System.out.println(singleResult.getUsername());


            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
}
