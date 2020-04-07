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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member = new Member();
            member.setUsername("doik1");
            member.changeTeam(team);
            em.persist(member);

            Member member1 = new Member();
            member1.setUsername("doik2");
            member1.changeTeam(team2);
            em.persist(member1);

            em.flush();
            em.clear();

            String query = "select m from Member m join fetch m.team";
            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "doik2")
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
