package net.huntersharpe.conversationapi;

/**
 * Created by intronate67 on 1/4/2016.
 */
public class NullConversationPrefix implements ConversationPrefix{
    /**
     * Prepends each conversation message with an empty string.
     *
     * @param context Context information about the conversation.
     * @return An empty string.
     */
    public String getPrefix(ConversationContext context) {
        return "";
    }
}
