package net.huntersharpe.conversationapi;

import org.spongepowered.api.Game;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by intronate67 on 1/4/2016.
 */
public class Conversation {

    private Prompt firstPrompt;
    private boolean abandoned;
    protected Prompt currentPrompt;
    protected ConversationContext context;
    protected boolean modal;
    protected boolean localEchoEnabled;
    protected ConversationPrefix prefix;
    protected List<ConversationCanceller> cancellers;
    protected List<ConversationAbandonedListener> abandonedListeners;

    /**
     * Initializes a new Conversation.
     *
     * @param game The game that owns this conversation.
     * @param forWhom The entity for whom this conversation is mediating.
     * @param firstPrompt The first prompt in the conversation graph.
     */
    public Conversation(Game game, Conversable forWhom, Prompt firstPrompt) {
        this(game, forWhom, firstPrompt, new HashMap<Object, Object>());
    }

    /**
     * Initializes a new Conversation.
     *
     * @param game The plugin that owns this conversation.
     * @param forWhom The entity for whom this conversation is mediating.
     * @param firstPrompt The first prompt in the conversation graph.
     * @param initialSessionData Any initial values to put in the conversation
     *     context sessionData map.
     */
    public Conversation(Game game, Conversable forWhom, Prompt firstPrompt, Map<Object, Object> initialSessionData) {
        this.firstPrompt = firstPrompt;
        this.context = new ConversationContext(game, forWhom, initialSessionData);
        this.modal = true;
        this.localEchoEnabled = true;
        this.prefix = new NullConversationPrefix();
        this.cancellers = new ArrayList<ConversationCanceller>();
        this.abandonedListeners = new ArrayList<ConversationAbandonedListener>();
    }

    /**
     * Gets the entity for whom this conversation is mediating.
     *
     * @return The entity.
     */
    public Conversable getForWhom() {
        return context.getForWhom();
    }

    /**
     * Gets the modality of this conversation. If a conversation is modal, all
     * messages directed to the player are suppressed for the duration of the
     * conversation.
     *
     * @return The conversation modality.
     */
    public boolean isModal() {
        return modal;
    }

    /**
     * Sets the modality of this conversation.  If a conversation is modal,
     * all messages directed to the player are suppressed for the duration of
     * the conversation.
     *
     * @param modal The new conversation modality.
     */
    void setModal(boolean modal) {
        this.modal = modal;
    }

    /**
     * Gets the status of local echo for this conversation. If local echo is
     * enabled, any text submitted to a conversation gets echoed back into the
     * submitter's chat window.
     *
     * @return The status of local echo.
     */
    public boolean isLocalEchoEnabled() {
        return localEchoEnabled;
    }

    /**
     * Sets the status of local echo for this conversation. If local echo is
     * enabled, any text submitted to a conversation gets echoed back into the
     * submitter's chat window.
     *
     * @param localEchoEnabled The status of local echo.
     */
    public void setLocalEchoEnabled(boolean localEchoEnabled) {
        this.localEchoEnabled = localEchoEnabled;
    }

    /**
     * Gets the {@link ConversationPrefix} that prepends all output from this
     * conversation.
     *
     * @return The ConversationPrefix in use.
     */
    public ConversationPrefix getPrefix() {
        return prefix;
    }

    /**
     * Sets the {@link ConversationPrefix} that prepends all output from this
     * conversation.
     *
     * @param prefix The ConversationPrefix to use.
     */
    void setPrefix(ConversationPrefix prefix) {
        this.prefix = prefix;
    }

    /**
     * Adds a {@link ConversationCanceller} to the cancellers collection.
     *
     * @param canceller The {@link ConversationCanceller} to add.
     */
    void addConversationCanceller(ConversationCanceller canceller) {
        canceller.setConversation(this);
        this.cancellers.add(canceller);
    }

    /**
     * Gets the list of {@link ConversationCanceller}s
     *
     * @return The list.
     */
    public List<ConversationCanceller> getCancellers() {
        return cancellers;
    }

    /**
     * Returns the Conversation's {@link ConversationContext}.
     *
     * @return The ConversationContext.
     */
    public ConversationContext getContext() {
        return context;
    }

    /**
     * Displays the first prompt of this conversation and begins redirecting
     * the user's chat responses.
     */
    public void begin() {
        if (currentPrompt == null) {
            abandoned = false;
            currentPrompt = firstPrompt;
            context.getForWhom().beginConversation(this);
        }
    }

    /**
     * Returns Returns the current state of the conversation.
     *
     * @return The current state of the conversation.
     */
    public ConversationState getState() {
        if (currentPrompt != null) {
            return ConversationState.STARTED;
        } else if (abandoned) {
            return ConversationState.ABANDONED;
        } else {
            return ConversationState.UNSTARTED;
        }
    }

    /**
     * Passes player input into the current prompt. The next prompt (as
     * determined by the current prompt) is then displayed to the user.
     *
     * @param input The user's chat text.
     */
    public void acceptInput(String input) {
        try { // Spigot
            if (currentPrompt != null) {

                // Echo the user's input
                if (localEchoEnabled) {
                    context.getForWhom().sendRawMessage(Text.of(prefix.getPrefix(context) + input));
                }

                // Test for conversation abandonment based on input
                for(ConversationCanceller canceller : cancellers) {
                    if (canceller.cancelBasedOnInput(context, input)) {
                        abandon(new ConversationAbandonedEvent(this, canceller));
                        return;
                    }
                }

                // Not abandoned, output the next prompt
                currentPrompt = currentPrompt.acceptInput(context, input);
                outputNextPrompt();
            }
            // Spigot Start
        } catch ( Throwable t )
        {
            java.util.logging.Logger.getLogger("Error handling conversation prompt");
        }
        // Spigot End
    }

    /**
     * Adds a {@link ConversationAbandonedListener}.
     *
     * @param listener The listener to add.
     */
    public synchronized void addConversationAbandonedListener(ConversationAbandonedListener listener) {
        abandonedListeners.add(listener);
    }

    /**
     * Removes a {@link ConversationAbandonedListener}.
     *
     * @param listener The listener to remove.
     */
    public synchronized void removeConversationAbandonedListener(ConversationAbandonedListener listener) {
        abandonedListeners.remove(listener);
    }

    /**
     * Abandons and resets the current conversation. Restores the user's
     * normal chat behavior.
     */
    public void abandon() {
        abandon(new ConversationAbandonedEvent(this, new ManuallyAbandonedConversationCanceller()));
    }

    /**
     * Abandons and resets the current conversation. Restores the user's
     * normal chat behavior.
     *
     * @param details Details about why the conversation was abandoned
     */
    public synchronized void abandon(ConversationAbandonedEvent details) {
        if (!abandoned) {
            abandoned = true;
            currentPrompt = null;
            context.getForWhom().abandonConversation(this);
            for (ConversationAbandonedListener listener : abandonedListeners) {
                listener.conversationAbandoned(details);
            }
        }
    }

    /**
     * Displays the next user prompt and abandons the conversation if the next
     * prompt is null.
     */
    public void outputNextPrompt() {
        if (currentPrompt == null) {
            abandon(new ConversationAbandonedEvent(this));
        } else {
            context.getForWhom().sendRawMessage(Text.of(prefix.getPrefix(context) + currentPrompt.getPromptText(context)));
            if (!currentPrompt.blocksForInput(context)) {
                currentPrompt = currentPrompt.acceptInput(context, null);
                outputNextPrompt();
            }
        }
    }

    public enum ConversationState {
        UNSTARTED,
        STARTED,
        ABANDONED
    }

}
