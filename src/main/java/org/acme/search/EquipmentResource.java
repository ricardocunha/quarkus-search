package org.acme.search;

import org.acme.search.model.*;
import org.hibernate.search.mapper.orm.session.SearchSession;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/equipment")
public class EquipmentResource {

    @Inject
    SearchSession searchSession;
    @GET
    @Path("sync")
    @Produces(MediaType.APPLICATION_JSON)
    public void sync() {
        System.out.println("start sync");
        try {
            searchSession.massIndexer(Equipment.class).startAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end sync");
    }
    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Equipment> searchEmployee(@QueryParam("name") String name) {
        return searchSession.search(Equipment.class)
                .where(f -> name == null || name.trim().isEmpty() ? f.matchAll()
                        : f.simpleQueryString()
                        .fields("make", "model", "fuel").matching(name))
                .fetchHits(20);
    }



}

