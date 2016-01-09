package net.huntersharpe.conversationapi;

/**
 * Created by intronate67 on 1/4/2016.
 */
public interface ConversationPrefix {

    /**
     * Gets the prefix to use before each message to the player.
     *
     * @param context Context information about the conversation.
     * @return The prefix text.
     */
    String getPrefix(ConversationContext context);

}
