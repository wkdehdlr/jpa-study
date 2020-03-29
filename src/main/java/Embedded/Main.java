package Embedded;

import CASCADE.Child;
import CASCADE.Parent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try{

            Address address = new Address("city","1000","street");

            Member member1 = new Member();
            member1.setUsername("doik");
            member1.setHomeAddress(address);
            em.persist(member1);

            Address address2 = new Address("city2","2000","street2");
            member1.setHomeAddress(address2);

            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
