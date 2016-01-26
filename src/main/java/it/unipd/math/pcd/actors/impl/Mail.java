package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;

/**
 * Created by Andrea Perini on 19/01/2016.
 */
public interface Mail<T extends Message> {
    T getMsg();

    ActorRef<T> getSender();
}
