package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.AbsActorSystem;
import it.unipd.math.pcd.actors.ActorRef;

/**
 * Created by Andrea Perini on 10/01/2016.
 */
public class ActorSystemImpl extends AbsActorSystem {

    @Override
    protected ActorRef createActorReference(ActorMode mode) throws IllegalArgumentException {
        if (mode == ActorMode.REMOTE){
            throw new IllegalArgumentException();
        } else {
            return new ActorRefImpl(this);
        }
    }
}
