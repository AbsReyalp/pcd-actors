/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015 Riccardo Cardin
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p>
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;
import it.unipd.math.pcd.actors.impl.Mail;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActor<T extends Message> implements Actor<T> {

    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;

    /**
     * Sender of the current message
     */
    protected ActorRef<T> sender;

    /**
     * Boolean to set if Actor is active or not
     */
    private volatile boolean ActorNotActive;

    /**
     * Linked queue containing Mail received from Actor
     */
    private BlockingQueue<Mail<T>> mailbox;

    public boolean isActorNotActive() {
        return ActorNotActive;
    }

    public void setActorNotActive() {
        synchronized (this) {
            if (ActorNotActive) {
                throw new NoSuchActorException();
            }
            this.ActorNotActive = true;
        }
    }

    /**
     * Sets the self-referece.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<T> setSelf(ActorRef<T> self) {
        this.self = self;
        return this;
    }

    public AbsActor() {
        mailbox = new LinkedBlockingQueue<Mail<T>>();
        self = null;
        sender = null;
        ActorNotActive = false;
        Runnable readEmail = new SimulateRead();
        Thread threadActor = new Thread(readEmail);
        threadActor.start();
    }

    public void AddNewMail(Mail<T> mail) throws NoSuchActorException {
        if (ActorNotActive){
            throw new NoSuchActorException();
        } else {
            try {
                mailbox.put(mail);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class SimulateRead implements Runnable {
        @Override
        public void run() {
            while (!ActorNotActive || !mailbox.isEmpty()) {
                try {
                    Mail<T> mail = mailbox.take();
                    sender = mail.getSender();
                    receive(mail.getMsg());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void receive(T message) {
    }
}
