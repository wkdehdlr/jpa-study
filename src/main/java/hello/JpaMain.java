package hello;

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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setName("doik");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

//            hello.Member findMember = em.find(hello.Member.class, member.getId());

            List<Member> members = em.createQuery(" select m from Member m ", Member.class)
                    .getResultList();


//            System.out.println("team = " + findMember.getTeam().getClass());

            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
