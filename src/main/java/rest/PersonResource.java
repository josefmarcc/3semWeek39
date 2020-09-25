package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPerson(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO p = FACADE.getPerson(id);
        return GSON.toJson(p);
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll() {
        PersonsDTO personsDTO = FACADE.getAllPersons();
        return GSON.toJson(personsDTO);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPerson(String person) throws MissingInputException {
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        PersonDTO pNew = FACADE.addPerson(p.getfName(), p.getlName(), p.getPhone(), p.getStreet(), p.getZip(), p.getCity());
        return GSON.toJson(pNew);
    }
    
    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updatePerson(@PathParam("id") int id,  String person) throws PersonNotFoundException, MissingInputException {
        PersonDTO pDTO = GSON.fromJson(person, PersonDTO.class);
        Person p = new Person(pDTO.getfName(), pDTO.getlName(), pDTO.getPhone());
        p.setId(id);
        PersonDTO pNew = new PersonDTO(p); 
        FACADE.editPerson(pNew);
        return GSON.toJson(pNew);
    }
    
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deletePerson(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO pDTO = FACADE.deletePerson(id);
        return "{\"status\":\"deleted\"}" + GSON.toJson(pDTO);
    }
    
    
    @GET
    @Path("/populate")
    @Produces({MediaType.APPLICATION_JSON})
    public String populate() {
        FACADE.populateDB();
        return "{\"msg\":\"People Added!\"}";
    }
    
    

}
