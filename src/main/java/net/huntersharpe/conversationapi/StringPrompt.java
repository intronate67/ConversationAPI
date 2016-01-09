package net.huntersharpe.conversationapi;

/**
 * Created by intronate67 on 1/4/2016.
 */
public abstract class StringPrompt implements Prompt{

    /**
     * Ensures that the prompt waits for the user to provide input.
     *
     * @param context Context information about the conversation.
     * @return True.
     */
    public boolean blocksForInput(ConversationContext context) {
        return true;
    }
}