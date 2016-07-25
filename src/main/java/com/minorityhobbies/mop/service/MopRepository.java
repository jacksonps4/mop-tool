package com.minorityhobbies.mop.service;


import com.minorityhobbies.mop.api.Mop;
import com.minorityhobbies.mop.api.MopAction;
import com.minorityhobbies.util.EventBus;
import com.minorityhobbies.util.ee.AbstractJPARepository;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.List;

@Singleton
@Startup
public class MopRepository extends AbstractJPARepository {
    @Inject
    EventBus<MopAction> mopActionMessageBus;

    public Mop createMop(String description, String createdBy) {
        Mop mop = new Mop(description, createdBy);
        persist(mop);
        return mop;
    }

    public Mop getMop(long mopId) {
        List<Mop> mops = get("select m from Mop m where m.id = ?1", Mop.class, mopId);
        return mops.size() > 0 ? mops.get(0) : null;
    }

    public List<MopAction> getMopActions(long mopId) {
        return get("select a from MopAction a where a.mop.id = ?1", MopAction.class, mopId);
    }

    public MopAction getMopAction(long mopActionId) {
        List<MopAction> actions = get("select a from MopAction a where a.id = ?1", MopAction.class, mopActionId);
        return actions.size() > 0 ? actions.get(0) : null;
    }

    public void saveMopAction(long mopId, MopAction mopAction) {
        Mop mop = getMop(mopId);
        mopAction.setMop(mop);
        persist(mopAction);
        mopActionMessageBus.publish(mopAction);
    }

    public List<Mop> getMops() {
        return get("select m from Mop m", Mop.class);
    }
}
