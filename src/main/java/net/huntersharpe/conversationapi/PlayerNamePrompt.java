package net.huntersharpe.conversationapi;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Created by intronate67 on 1/4/2016.
 */
public abstract class PlayerNamePrompt extends ValidatingPrompt{
    private Game game;

    public PlayerNamePrompt(Game game) {
        super();
        this.game = game;
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        return game.getServer().getPlayer(input) != null;

    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, String input) {
        return acceptValidatedInput(context, game.getServer().getPlayer(input).get());
    }

    /**
     * Override this method to perform some action with the user's player name
     * response.
     *
     * @param context Context information about the conversation.
     * @param input The user's player name response.
     * @return The next {@link Prompt} in the prompt graph.
     */
    protected abstract Prompt acceptValidatedInput(ConversationContext context, Player input);
}