package net.huntersharpe.conversationapi;

import org.spongepowered.api.text.Text;

/**
 * Created by intronate67 on 1/4/2016.
 */
public interface Conversable {

    /**
     * Tests to see of a Conversable object is actively engaged in a
     * conversation.
     *
     * @return True if a conversation is in progress
     */
    public boolean isConversing();

    /**
     * Accepts input into the active conversation. If no conversation is in
     * progress, this method does nothing.
     *
     * @param input The input message into the conversation
     */
    public void acceptConversationInput(Text input);

    /**
     * Enters into a dialog with a Conversation object.
     *
     * @param conversation The conversation to begin
     * @return True if the conversation should proceed, false if it has been
     *     enqueued
     */
    public boolean beginConversation(Conversation conversation);

    /**
     * Abandons an active conversation.
     *
     * @param conversation The conversation to abandon
     */
    public void abandonConversation(Conversation conversation);

    /**
     * Abandons an active conversation.
     *
     * @param conversation The conversation to abandon
     * @param details Details about why the conversation was abandoned
     */
    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details);

    /**
     * Sends this sender a message raw
     *
     * @param message Message to be displayed
     */
    public void sendRawMessage(Text message);

}
