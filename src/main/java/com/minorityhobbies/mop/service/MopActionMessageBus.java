package com.minorityhobbies.mop.service;

import com.minorityhobbies.mop.api.MopAction;
import com.minorityhobbies.util.EventBus;
import com.minorityhobbies.util.ProcessEventBus;

import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class MopActionMessageBus extends ProcessEventBus<MopAction> implements EventBus<MopAction> {
}
