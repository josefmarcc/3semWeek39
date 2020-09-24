package facades;

import dto.PersonDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    private static Person p1,p2,p3;

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
        p2 = new Person("Frederik", "Dahl", "30303030");
        p3 = new Person("Thor", "Christensen", "45454545");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
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
    public void testgetAllPerson() {
        int expResult = 3;
        int result = facade.getAllPersons().getAll().size();
        assertEquals(expResult, result);
    }
    
    @Disabled
    @Test
    public void testgetPerson() throws PersonNotFoundException {
        int findID = 2;
        int expResult = p1.getId();
        int result = facade.getPerson(findID).getId();
        assertEquals(expResult, result);
    }
    
    @Disabled
    @Test
    public void testEditPerson() throws PersonNotFoundException{
        PersonDTO pDTO = new PersonDTO("Ny", "Person", "911");
        facade.editPerson(pDTO);
        String expResult = "Ny";
        
        
    }
}
