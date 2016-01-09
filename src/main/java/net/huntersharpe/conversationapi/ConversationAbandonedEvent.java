package net.huntersharpe.conversationapi;

import java.util.EventObject;

/**
 * Created by intronate67 on 1/4/2016.
 */
public class ConversationAbandonedEvent extends EventObject {

    private ConversationContext context;
    private ConversationCanceller canceller;

    public ConversationAbandonedEvent(Conversation conversation) {
        this(conversation, null);
    }

    public ConversationAbandonedEvent(Conversation conversation, ConversationCanceller canceller) {
        super(conversation);
        this.context = conversation.getContext();
        this.canceller = canceller;
    }

    /**
     * Gets the object that caused the conversation to be abandoned.
     *
     * @return The object that abandoned the conversation.
     */
    public ConversationCanceller getCanceller() {
        return canceller;
    }

    /**
     * Gets the abandoned conversation's conversation context.
     *
     * @return The abandoned conversation's conversation context.
     */
    public ConversationContext getContext() {
        return context;
    }

    /**
     * Indicates how the conversation was abandoned - naturally as part of the
     * prompt chain or prematurely via a {@link ConversationCanceller}.
     *
     * @return True if the conversation is abandoned gracefully by a {@link
     *     Prompt} returning null or the next prompt. False of the
     *     conversations is abandoned prematurely by a ConversationCanceller.
     */
    public boolean gracefulExit() {
        return canceller == null;
    }
}
