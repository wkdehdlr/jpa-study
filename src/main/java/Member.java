import javax.persistence.*;

@Entity
public class Member extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Team team;

    @OneToOne
    @JoinColumn(name = "Locker_id")
    private Locker locker;

    public Member(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
