package net.huntersharpe.conversationapi;

/**
 * Created by intronate67 on 1/4/2016.
 */
public class ManuallyAbandonedConversationCanceller implements ConversationCanceller{
    public void setConversation(Conversation conversation) {
        throw new UnsupportedOperationException();
    }

    public boolean cancelBasedOnInput(ConversationContext context, String input) {
        throw new UnsupportedOperationException();
    }

    public ConversationCanceller clone() {
        throw new UnsupportedOperationException();
    }
}
