package com.minorityhobbies.mop.web;

import com.minorityhobbies.mop.api.MopAction;
import com.minorityhobbies.mop.service.MopRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("mop-action")
public class MopActionResource extends AbstractMopActionResource {
    @Inject
    MopRepository mopRepository;

    @GET
    public List<MopAction> getMopActions() {
        return mopRepository.getMopActions(getMopId());
    }

    @GET
    @Path("create")
    public MopAction addMopAction(@QueryParam("action") String action) {
        MopAction mopAction = new MopAction();
        mopAction.setAction(action);
        mopRepository.saveMopAction(getMopId(), mopAction);
        return mopAction;
    }
}
