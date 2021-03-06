package net.huntersharpe.conversationapi;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by intronate67 on 1/4/2016.
 */
public class ConversationFactory {

    protected Game game;
    protected boolean isModal;
    protected boolean localEchoEnabled;
    protected ConversationPrefix prefix;
    protected Prompt firstPrompt;
    protected Map<Object, Object> initialSessionData;
    protected String playerOnlyMessage;
    protected List<ConversationCanceller> cancellers;
    protected List<ConversationAbandonedListener> abandonedListeners;

    /**
     * Constructs a ConversationFactory.
     *
     * @param game The game that owns the factory.
     */
    public ConversationFactory(Game game)
    {
        this.game = game;
        isModal = true;
        localEchoEnabled = true;
        prefix = new NullConversationPrefix();
        firstPrompt = Prompt.END_OF_CONVERSATION;
        initialSessionData = new HashMap<Object, Object>();
        playerOnlyMessage = null;
        cancellers = new ArrayList<ConversationCanceller>();
        abandonedListeners = new ArrayList<ConversationAbandonedListener>();
    }

    /**
     * Sets the modality of all {@link Conversation}s created by this factory.
     * If a conversation is modal, all messages directed to the player are
     * suppressed for the duration of the conversation.
     * <p>
     * The default is True.
     *
     * @param modal The modality of all conversations to be created.
     * @return This object.
     */
    public ConversationFactory withModality(boolean modal)
    {
        isModal = modal;
        return this;
    }

    /**
     * Sets the local echo status for all {@link Conversation}s created by
     * this factory. If local echo is enabled, any text submitted to a
     * conversation gets echoed back into the submitter's chat window.
     *
     * @param localEchoEnabled The status of local echo.
     * @return This object.
     */
    public ConversationFactory withLocalEcho(boolean localEchoEnabled) {
        this.localEchoEnabled = localEchoEnabled;
        return this;
    }

    /**
     * Sets the {@link ConversationPrefix} that prepends all output from all
     * generated conversations.
     * <p>
     * The default is a {@link NullConversationPrefix};
     *
     * @param prefix The ConversationPrefix to use.
     * @return This object.
     */
    public ConversationFactory withPrefix(ConversationPrefix prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Sets the number of inactive seconds to wait before automatically
     * abandoning all generated conversations.
     * <p>
     * The default is 600 seconds (5 minutes).
     *
     * @param timeoutSeconds The number of seconds to wait.
     * @return This object.
     */
    public ConversationFactory withTimeout(int timeoutSeconds) {
        return withConversationCanceller(new InactivityConversationCanceller(game, timeoutSeconds));
    }

    /**
     * Sets the first prompt to use in all generated conversations.
     * <p>
     * The default is Prompt.END_OF_CONVERSATION.
     *
     * @param firstPrompt The first prompt.
     * @return This object.
     */
    public ConversationFactory withFirstPrompt(Prompt firstPrompt) {
        this.firstPrompt = firstPrompt;
        return this;
    }

    /**
     * Sets any initial data with which to populate the conversation context
     * sessionData map.
     *
     * @param initialSessionData The conversation context's initial
     *     sessionData.
     * @return This object.
     */
    public ConversationFactory withInitialSessionData(Map<Object, Object> initialSessionData) {
        this.initialSessionData = initialSessionData;
        return this;
    }

    /**
     * Sets the player input that, when received, will immediately terminate
     * the conversation.
     *
     * @param escapeSequence Input to terminate the conversation.
     * @return This object.
     */
    public ConversationFactory withEscapeSequence(String escapeSequence) {
        return withConversationCanceller(new ExactMatchConversationCanceller(escapeSequence));
    }


    /**
     * Adds a {@link ConversationCanceller} to constructed conversations.
     *
     * @param canceller The {@link ConversationCanceller} to add.
     * @return This object.
     */
    public ConversationFactory withConversationCanceller(ConversationCanceller canceller) {
        cancellers.add(canceller);
        return this;
    }

    /**
     * Prevents this factory from creating a conversation for non-player
     * {@link Conversable} objects.
     *
     * @param playerOnlyMessage The message to return to a non-play in lieu of
     *     starting a conversation.
     * @return This object.
     */
    public ConversationFactory thatExcludesNonPlayersWithMessage(String playerOnlyMessage) {
        this.playerOnlyMessage = playerOnlyMessage;
        return this;
    }

    /**
     * Adds a {@link ConversationAbandonedListener} to all conversations
     * constructed by this factory.
     *
     * @param listener The listener to add.
     * @return This object.
     */
    public ConversationFactory addConversationAbandonedListener(ConversationAbandonedListener listener) {
        abandonedListeners.add(listener);
        return this;
    }

    /**
     * Constructs a {@link Conversation} in accordance with the defaults set
     * for this factory.
     *
     * @param forWhom The entity for whom the new conversation is mediating.
     * @return A new conversation.
     */
    public Conversation buildConversation(Conversable forWhom) {
        //Abort conversation construction if we aren't supposed to talk to non-players
        if (playerOnlyMessage != null && !(forWhom instanceof Player)) {
            return new Conversation(game, forWhom, new NotPlayerMessagePrompt());
        }

        //Clone any initial session data
        Map<Object, Object> copiedInitialSessionData = new HashMap<Object, Object>();
        copiedInitialSessionData.putAll(initialSessionData);

        //Build and return a conversation
        Conversation conversation = new Conversation(game, forWhom, firstPrompt, copiedInitialSessionData);
        conversation.setModal(isModal);
        conversation.setLocalEchoEnabled(localEchoEnabled);
        conversation.setPrefix(prefix);

        //Clone the conversation cancellers
        for (ConversationCanceller canceller : cancellers) {
            conversation.addConversationCanceller(canceller.clone());
        }

        //Add the ConversationAbandonedListeners
        for (ConversationAbandonedListener listener : abandonedListeners) {
            conversation.addConversationAbandonedListener(listener);
        }

        return conversation;
    }

    private class NotPlayerMessagePrompt extends MessagePrompt {

        public Text getPromptText(ConversationContext context) {
            return Text.of(playerOnlyMessage);
        }

        @Override
        protected Prompt getNextPrompt(ConversationContext context) {
            return Prompt.END_OF_CONVERSATION;
        }
    }

}
