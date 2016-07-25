package com.minorityhobbies.mop.web;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class MopToolApplication extends Application {
    private final Set<Class<?>> resources = new HashSet<>();

    public MopToolApplication() {
        resources.add(MopResource.class);
        resources.add(MopActionResource.class);
        resources.add(MopActionVoteResource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return resources;
    }
}
