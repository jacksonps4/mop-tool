package com.minorityhobbies.mop.web;

import com.minorityhobbies.mop.api.Mop;
import com.minorityhobbies.mop.service.MopRepository;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("mop")
public class MopResource extends AbstractMopActionResource {
    @Inject
    MopRepository mopRepository;

    @Context
    HttpServletRequest request;

    @GET
    @Path("create")
    public Mop create(@QueryParam("description") String description, @QueryParam("username") String username) {
        if (username != null) {
            setUsername(username);
        }

        Mop mop = mopRepository.createMop(description, username);
        setMopId(mop.getId());
        return mop;
    }

    @GET
    @Path("join")
    public Mop join(@QueryParam("mopId") Long mopId, @QueryParam("username") String username) {
        if (mopId == null) {
            throw new BadRequestException("Must set mop id");
        }
        if (username != null) {
            setUsername(username);
        }

        Mop mop = findMop(mopId);
        setMopId(mop.getId());
        return mop;
    }

    @GET
    @Path("list")
    public List<Mop> list() {
        return mopRepository.getMops();
    }

    @GET
    public Mop get() {
        return findMop(getMopId());
    }

    Mop findMop(long mopId) {
        Mop mop = mopRepository.getMop(mopId);
        if (mop == null) {
            throw new NotFoundException();
        }
        return mop;
    }
}
