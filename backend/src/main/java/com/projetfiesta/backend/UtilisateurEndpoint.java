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
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "utilisateurApi",
        version = "v1",
        resource = "utilisateur",
        namespace = @ApiNamespace(
                ownerDomain = "backend.projetfiesta.com",
                ownerName = "backend.projetfiesta.com",
                packagePath = ""
        )
)
public class UtilisateurEndpoint {

    private static final Logger logger = Logger.getLogger(UtilisateurEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Utilisateur.class);
    }

    /**
     * Returns the {@link Utilisateur} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Utilisateur} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "utilisateur/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Utilisateur get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Utilisateur with ID: " + id);
        Utilisateur utilisateur = ofy().load().type(Utilisateur.class).id(id).now();
        if (utilisateur == null) {
            throw new NotFoundException("Could not find Utilisateur with ID: " + id);
        }
        return utilisateur;
    }

    /**
     * Inserts a new {@code Utilisateur}.
     */
    @ApiMethod(
            name = "insert",
            path = "utilisateur",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Utilisateur insert(Utilisateur utilisateur) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that utilisateur.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(utilisateur).now();
        logger.info("Created Utilisateur with ID: " + utilisateur.getId());

        return ofy().load().entity(utilisateur).now();
    }

    /**
     * Updates an existing {@code Utilisateur}.
     *
     * @param id          the ID of the entity to be updated
     * @param utilisateur the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Utilisateur}
     */
    @ApiMethod(
            name = "update",
            path = "utilisateur/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Utilisateur update(@Named("id") Long id, Utilisateur utilisateur) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(utilisateur).now();
        logger.info("Updated Utilisateur: " + utilisateur);
        return ofy().load().entity(utilisateur).now();
    }

    /**
     * Deletes the specified {@code Utilisateur}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Utilisateur}
     */
    @ApiMethod(
            name = "remove",
            path = "utilisateur/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Utilisateur.class).id(id).now();
        logger.info("Deleted Utilisateur with ID: " + id);
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
            path = "utilisateur",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Utilisateur> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Utilisateur> query = ofy().load().type(Utilisateur.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Utilisateur> queryIterator = query.iterator();
        List<Utilisateur> utilisateurList = new ArrayList<Utilisateur>(limit);
        while (queryIterator.hasNext()) {
            utilisateurList.add(queryIterator.next());
        }
        return CollectionResponse.<Utilisateur>builder().setItems(utilisateurList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Utilisateur.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Utilisateur with ID: " + id);
        }
    }
}