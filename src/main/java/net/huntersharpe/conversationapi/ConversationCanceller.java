package net.huntersharpe.conversationapi;

/**
 * Created by intronate67 on 1/4/2016.
 */
public interface ConversationCanceller extends Cloneable{
    /**
     * Sets the conversation this ConversationCanceller can optionally cancel.
     *
     * @param conversation A conversation.
     */
    public void setConversation(Conversation conversation);

    /**
     * Cancels a conversation based on user input.
     *
     * @param context Context information about the conversation.
     * @param input The input text from the user.
     * @return True to cancel the conversation, False otherwise.
     */
    public boolean cancelBasedOnInput(ConversationContext context, String input);

    /**
     * Allows the {@link ConversationFactory} to duplicate this
     * ConversationCanceller when creating a new {@link Conversation}.
     * <p>
     * Implementing this method should reset any internal object state.
     *
     * @return A clone.
     */
    public ConversationCanceller clone();



}
