package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import entities.Address;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2, p3;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Josef", "Marc", "112");
        p1.setAddress(new Address("vej", "2600", "Glostrup"));
        p2 = new Person("Frederik", "Dahl", "30303030");
        p2.setAddress(new Address("vej", "2200", "Nørrebro"));
        p3 = new Person("Thor", "Christensen", "45454545");
        p3.setAddress(new Address("vej", "4200", "Slagelse"));
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNativeQuery("ALTER TABLE startcode_test.ADDRESS AUTO_INCREMENT = 1");
            em.createNativeQuery("ALTER TABLE startcode_test.PERSON AUTO_INCREMENT = 1");
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetAllPersons() {
        System.out.println("getAllPersons");
        int expResult = 3;
        PersonsDTO result = facade.getAllPersons();
        assertEquals(expResult, result.getAll().size());
    }

    
    // FIX SENERE 
    @Disabled 
    @Test
    public void testGetPerson() throws Exception {
        System.out.println("getPerson");
        int id = p3.getId();
        PersonDTO expResult = new PersonDTO(p3);
        PersonDTO result = facade.getPerson(id);
        assertEquals(expResult, result);
    }

    @Test
    public void testAddPerson() throws Exception {
        System.out.println("addPerson");
        String fName = "Person";
        String lName = "Test";
        String phone = "234234";
        String street = "vej";
        String zip = "2332";
        String city = "By";
        PersonDTO result = facade.addPerson(fName, lName, phone, street, zip, city);
        PersonDTO expResult = new PersonDTO(fName, lName, phone, street, zip, city);
        expResult.setId(expResult.getId());
        assertEquals(expResult.getfName(), result.getfName());
        assertEquals(expResult.getlName(), result.getlName());
        assertEquals(expResult.getPhone(), result.getPhone());
    }

    @Test
    public void testEditPerson() throws Exception {
        System.out.println("editPerson");
        PersonDTO p = new PersonDTO(p1);
        PersonDTO expResult = new PersonDTO(p1);
        expResult.setfName("Birger");
        p.setfName("Birger");
        PersonDTO result = facade.editPerson(p);
        assertEquals(expResult.getfName(), result.getfName());
    }
}
