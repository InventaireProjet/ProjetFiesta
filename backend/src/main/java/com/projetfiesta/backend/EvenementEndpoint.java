package com.projetfiesta.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "evenementApi",
        version = "v1",
        resource = "evenement",
        namespace = @ApiNamespace(
                ownerDomain = "backend.projetfiesta.com",
                ownerName = "backend.projetfiesta.com",
                packagePath = ""
        )
)
public class EvenementEndpoint {

    private static final Logger logger = Logger.getLogger(EvenementEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Evenement.class);
    }

    /**
     * Returns the {@link Evenement} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Evenement} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "evenement/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Evenement get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Evenement with ID: " + id);
        Evenement evenement = ofy().load().type(Evenement.class).id(id).now();
        if (evenement == null) {
            throw new NotFoundException("Could not find Evenement with ID: " + id);
        }
        return evenement;
    }

    /**
     * Inserts a new {@code Evenement}.
     */
    @ApiMethod(
            name = "insert",
            path = "evenement",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Evenement insert(Evenement evenement) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that evenement.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(evenement).now();
        logger.info("Created Evenement with ID: " + evenement.getId());

        return ofy().load().entity(evenement).now();
    }

    /**
     * Updates an existing {@code Evenement}.
     *
     * @param id        the ID of the entity to be updated
     * @param evenement the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Evenement}
     */
    @ApiMethod(
            name = "update",
            path = "evenement/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Evenement update(@Named("id") Long id, Evenement evenement) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(evenement).now();
        logger.info("Updated Evenement: " + evenement);
        return ofy().load().entity(evenement).now();
    }

    /**
     * Deletes the specified {@code Evenement}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Evenement}
     */
    @ApiMethod(
            name = "remove",
            path = "evenement/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Evenement.class).id(id).now();
        logger.info("Deleted Evenement with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "evenement",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Evenement> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Evenement> query = ofy().load().type(Evenement.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Evenement> queryIterator = query.iterator();
        List<Evenement> evenementList = new ArrayList<Evenement>(limit);
        while (queryIterator.hasNext()) {
            evenementList.add(queryIterator.next());
        }
        return CollectionResponse.<Evenement>builder().setItems(evenementList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Evenement.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Evenement with ID: " + id);
        }
    }
}