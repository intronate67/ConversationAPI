package net.huntersharpe.conversationapi;

import org.spongepowered.api.Game;
import org.spongepowered.api.plugin.Plugin;

import java.util.Map;

/**
 * Created by intronate67 on 1/4/2016.
 */
public class ConversationContext {

    private Conversable forWhom;
    private Map<Object, Object> sessionData;
    private Game game;

    /**
     * @param game The owning plugin.
     * @param forWhom The subject of the conversation.
     * @param initialSessionData Any initial values to put in the sessionData
     *     map.
     */
    public ConversationContext(Game game, Conversable forWhom, Map<Object, Object> initialSessionData) {
        this.game = game;
        this.forWhom = forWhom;
        this.sessionData = initialSessionData;
    }

    /**
     * Gets the plugin that owns this conversation.
     *
     * @return The owning game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Gets the subject of the conversation.
     *
     * @return The subject of the conversation.
     */
    public Conversable getForWhom() {
        return forWhom;
    }

    /**
     * Gets the entire sessionData map.
     * @return The full sessionData map.
     */
    public Map<Object, Object> getAllSessionData() {
        return sessionData;
    }

    /**
     * Gets session data shared between all {@link Prompt} invocations. Use
     * this as a way to pass data through each Prompt as the conversation
     * develops.
     *
     * @param key The session data key.
     * @return The requested session data.
     */
    public Object getSessionData(Object key) {
        return sessionData.get(key);
    }

    /**
     * Sets session data shared between all {@link Prompt} invocations. Use
     * this as a way to pass data through each prompt as the conversation
     * develops.
     *
     * @param key The session data key.
     * @param value The session data value.
     */
    public void setSessionData(Object key, Object value) {
        sessionData.put(key, value);
    }


}
