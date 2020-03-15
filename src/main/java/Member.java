import javax.persistence.*;

@Entity
@Table(name = "tb_member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq")
    private Long id;

    @Column(name = "mname")
    private String name;

    @Column(name = "mcity")
    private String city;

    @Column(name = "mstreet")
    private String street;

    @Column(name = "mzipcode")
    private String zipCode;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
