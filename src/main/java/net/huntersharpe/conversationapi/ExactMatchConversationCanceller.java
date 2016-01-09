package net.huntersharpe.conversationapi;

/**
 * Created by intronate67 on 1/4/2016.
 */
public class ExactMatchConversationCanceller implements ConversationCanceller {
    private String escapeSequence;

    /**
     * Builds an ExactMatchConversationCanceller.
     *
     * @param escapeSequence The string that, if entered by the user, will
     *     cancel the conversation.
     */
    public ExactMatchConversationCanceller(String escapeSequence) {
        this.escapeSequence = escapeSequence;
    }

    public void setConversation(Conversation conversation) {}

    public boolean cancelBasedOnInput(ConversationContext context, String input) {
        return input.equals(escapeSequence);
    }

    public ConversationCanceller clone() {
        return new ExactMatchConversationCanceller(escapeSequence);
    }
}
