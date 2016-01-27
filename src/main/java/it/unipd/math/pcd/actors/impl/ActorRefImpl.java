package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;
import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;
import it.unipd.math.pcd.actors.impl.MailImpl;

/**
 * Created by Andrea Perini on 10/01/2016.
 */
public class ActorRefImpl<T extends Message> implements ActorRef<T> {

    AbsActorSystem actorSystem;

    public ActorRefImpl(ActorSystem actorSystem) {
        this.actorSystem = (AbsActorSystem) actorSystem;
    }

    @Override
    public void send(Message message, ActorRef to) throws NoSuchActorException {
        if (((AbsActor) actorSystem.findActor(to)).isActorNotActive()) {
            throw new NoSuchActorException("Qua");
        } else {
            ((AbsActor) actorSystem.findActor(to)).AddNewMail(new MailImpl(this, message));
        }
    }

    @Override
    public int compareTo(ActorRef ar) {
        if (this == ar) {
            return 0;
        } else {
            return -1;
        }
    }
}
