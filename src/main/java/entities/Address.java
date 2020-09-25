package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author josef
 */
@Entity
@NamedQueries({
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address"),
@NamedQuery(name = "Address.getAllRows", query = "SELECT a from Address a")})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long a_id;
    private String street;
    private String city;
    private String zip;
    
    @OneToMany(mappedBy="address")
    private List<Person> persons;

    public Address(String address, String city, String zip) {
        this.street = address;
        this.city = city;
        this.zip = zip;
        persons = new ArrayList<>();
    }
    
    public Address() {
    }

    public void addPerson(Person person) {
        if (person != null){
            persons.add(person);
        }
    }
    
    public List<Person> getPerson() {
        return persons;
    }
    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Long getId() {
        return a_id;
    }

    public void setId(Long id) {
        this.a_id = id;
    }

}

