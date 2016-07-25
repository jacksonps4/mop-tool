package com.minorityhobbies.mop.web;

import com.minorityhobbies.mop.api.MopAction;
import com.minorityhobbies.util.EventBus;
import com.minorityhobbies.util.EventBusSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.core.Context;
import java.io.Closeable;
import java.io.IOException;
import java.util.function.Predicate;

@ServerEndpoint("/api/live/mop-actions")
public class LiveMopActions extends AbstractMopActionResource {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Closeable handle;

    @Inject
    EventBus<MopAction> mopActionMessageBus;

    @Context
    HttpServletRequest request;

    @OnOpen
    public void open(Session session) {
        handle = mopActionMessageBus.subscribe(new EventBusSubscriber<MopAction>() {
            @Override
            public Predicate<MopAction> matches() {
                return action -> getMopId().equals(action.getMop().getId());
            }

            @Override
            public void onEvent(MopAction mopAction) {
                try {
                    session.getBasicRemote().sendObject(mopAction);
                } catch (IOException | EncodeException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @OnClose
    public void close() {
        if (handle != null) {
            try {
                handle.close();
            } catch (IOException e) {
                logger.warn("Failed to close handle", e);
            }
        }
    }
}
