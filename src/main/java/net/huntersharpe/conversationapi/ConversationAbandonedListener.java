package net.huntersharpe.conversationapi;

import java.util.EventListener;

/**
 * Created by intronate67 on 1/4/2016.
 */
public interface ConversationAbandonedListener extends EventListener {
    /**
     * Called whenever a {@link Conversation} is abandoned.
     *
     * @param abandonedEvent Contains details about the abandoned
     *     conversation.
     */
    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent);
}
