package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;

/**
 * Created by Andrea Perini on 14/01/2016.
 */
public class MailImpl<T extends Message> implements Mail<T> {

    /**
     * sender of the Mail
     */
    private final ActorRef<T> sender;

    /**
     * Message
     */
    private final T msg;

    public MailImpl(ActorRef<T> sender, T msg) {
        this.sender = sender;
        this.msg = msg;
    }

    public ActorRef<T> getSender() {
        return sender;
    }

    public T getMsg() {
        return msg;
    }
}