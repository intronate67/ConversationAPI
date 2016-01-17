package net.huntersharpe.conversationapi;

import org.spongepowered.api.text.Text;

/**
 * Created by intronate67 on 1/4/2016.
 */
public interface Prompt extends Cloneable {

    /**
     * A convenience constant for indicating the end of a conversation.
     */
    Prompt END_OF_CONVERSATION = null;

    /**
     * Gets the text to display to the user when this prompt is first
     * presented.
     *
     * @param context Context information about the conversation.
     * @return The text to display.
     */
    Text getPromptText(ConversationContext context);

    /**
     * Checks to see if this prompt implementation should wait for user input
     * or immediately display the next prompt.
     *
     * @param context Context information about the conversation.
     * @return If true, the {@link Conversation} will wait for input before
     *     continuing.
     */
    boolean blocksForInput(ConversationContext context);

    /**
     * Accepts and processes input from the user. Using the input, the next
     * Prompt in the prompt graph is returned.
     *
     * @param context Context information about the conversation.
     * @param input The input text from the user.
     * @return The next Prompt in the prompt graph.
     */
    Prompt acceptInput(ConversationContext context, String input);
}
