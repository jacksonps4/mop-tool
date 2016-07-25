package com.minorityhobbies.mop.web;

import com.minorityhobbies.mop.api.MopAction;
import com.minorityhobbies.mop.api.MopActionVote;
import com.minorityhobbies.mop.service.MopActionVotes;
import com.minorityhobbies.mop.service.MopRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("vote")
public class MopActionVoteResource extends AbstractMopActionResource {
    @Inject
    MopActionVotes votes;

    @Inject
    MopRepository mopRepository;

    @GET
    public List<MopActionVote> votes() {
        return votes.getVotes(getUsername(), getMopId());
    }

    @GET
    @Path("add")
    public Response vote(@QueryParam("mopActionId") long mopActionId) {
        MopAction action = mopRepository.getMopAction(mopActionId);
        if (action == null) {
            throw new NotFoundException();
        }

        MopActionVote vote = votes.vote(action, getUsername());
        if (vote != null) {
            return Response.ok()
                    .entity(vote)
                    .build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("undo")
    public Response undoVote(@QueryParam("mopActionId") long mopActionId) {
        MopAction action = mopRepository.getMopAction(mopActionId);
        if (action == null) {
            throw new NotFoundException();
        }

        if (votes.removeVote(getUsername(), action, getMopId())) {
            return Response.ok().build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
