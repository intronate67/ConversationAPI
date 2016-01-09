package net.huntersharpe.conversationapi;

/**
 * Created by intronate67 on 1/4/2016.
 */
public abstract class MessagePrompt implements Prompt{

    public MessagePrompt() {
        super();
    }

    /**
     * Message prompts never wait for user input before continuing.
     *
     * @param context Context information about the conversation.
     * @return Always false.
     */
    public boolean blocksForInput(ConversationContext context) {
        return false;
    }

    /**
     * Accepts and ignores any user input, returning the next prompt in the
     * prompt graph instead.
     *
     * @param context Context information about the conversation.
     * @param input Ignored.
     * @return The next prompt in the prompt graph.
     */
    public Prompt acceptInput(ConversationContext context, String input) {
        return getNextPrompt(context);
    }

    /**
     * Override this method to return the next prompt in the prompt graph.
     *
     * @param context Context information about the conversation.
     * @return The next prompt in the prompt graph.
     */
    protected abstract Prompt getNextPrompt(ConversationContext context);
}