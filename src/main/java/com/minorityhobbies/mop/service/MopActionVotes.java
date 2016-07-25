package com.minorityhobbies.mop.service;

import com.minorityhobbies.mop.api.MopAction;
import com.minorityhobbies.mop.api.MopActionVote;
import com.minorityhobbies.util.ee.AbstractJPARepository;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.Query;
import java.util.List;

@Singleton
@Startup
public class MopActionVotes extends AbstractJPARepository {
    public MopActionVote vote(MopAction action, String username) {
        // check vote count
        Long count = getUnique("select count(v) from MopActionVote v where v.username = ?1 " +
                        "and v.mopAction.mop.id = ?2",
                Long.class, username, action.getMop().getId());
        if (count >= 5) {
            return null;
        }

        MopActionVote vote = new MopActionVote();
        vote.setUsername(username);
        vote.setMopAction(action);
        persist(vote);
        return vote;
    }

    public List<MopActionVote> getVotes(String username, long mopId) {
        return get("select v from MopActionVote v where v.username = ?1 and v.mopAction.mop.id = ?2",
                MopActionVote.class, username, mopId);
    }

    public boolean removeVote(String username, MopAction action, long mopId) {
        Query q = getEntityManager().createQuery("delete from MopActionVote v where v.username = ?1 and " +
                "v.mopAction.id = ?2 and v.mopAction.mop.id = ?3");
        q.setParameter(1, username);
        q.setParameter(2, action.getId());
        q.setParameter(3, mopId);
        return q.executeUpdate() > 0;
    }
}
