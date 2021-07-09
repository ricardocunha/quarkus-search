package org.acme.search;

import org.acme.search.model.Employee;
import org.acme.search.model.VwEmployee;
import org.hibernate.search.mapper.orm.session.SearchSession;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/employee")
public class EmployeeResource {

    @Inject
    SearchSession searchSession;

    @GET
    @Path("sync")
    @Produces(MediaType.APPLICATION_JSON)
    public void sync() {
        System.out.println("start sync");
        try {
            searchSession.massIndexer(Employee.class).transactionTimeout(10000000).startAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end sync");
    }

    @PUT
    @Path("/")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addEmployee(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName) {
        Employee e = new Employee();
        e.firstName = firstName;
        e.lastName = lastName;
        e.persist();
    }

    @POST
    @Path("{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateEmployee(@PathParam("id") Long id, @FormParam("firstName")  String firstName, @FormParam("lastName") String lastName) {
        Employee e = Employee.findById(id);
        if (e == null) {
            return;
        }
        e.firstName = firstName;
        e.lastName = lastName;
        e.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deleteEmployee(@PathParam("id") Long id) {
        Employee e = Employee.findById(id);
        if (e != null) {
            e.delete();
        }
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> searchEmployee(@QueryParam("name") String name) {
        return searchSession.search(Employee.class)
                .where(f -> name == null || name.trim().isEmpty() ? f.matchAll()
                        : f.simpleQueryString()
                        .fields("firstName", "lastName").matching(name))
                .sort(f -> f.field("lastName_sort").then().field("firstName_sort"))
                .fetchHits(20);
    }

    @GET
    @Path("view")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VwEmployee> searchViewEmployee(@QueryParam("name") String name) {
        return VwEmployee.getEmployees(name);
    }

}