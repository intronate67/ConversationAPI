package net.huntersharpe.conversationapi;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;

/**
 * Created by intronate67 on 1/4/2016.
 */
public class InactivityConversationCanceller implements ConversationCanceller {
    protected Game game = Sponge.getGame();
    protected int timeoutSeconds;
    protected Conversation conversation;
    private int taskId = -1;

    /**
     * Creates an InactivityConversationCanceller.
     *
     * @param game The owning game.
     * @param timeoutSeconds The number of seconds of inactivity to wait.
     */
    public InactivityConversationCanceller(Game game, int timeoutSeconds) {
        this.game = game;
        this.timeoutSeconds = timeoutSeconds;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
        //startTimer();
    }

    public boolean cancelBasedOnInput(ConversationContext context, String input) {
        // Reset the inactivity timer
        //stopTimer();
        //startTimer();
        return false;
    }

    public ConversationCanceller clone() {
        return new InactivityConversationCanceller(game, timeoutSeconds);
    }

    /**
     * Starts an inactivity timer.
     */
    /*private void startTimer() {
        //TODO: Update to use sponge scheduler
        /*taskId = game.getScheduler().createTaskBuilder().execute(new Runnable() {
        })

                .scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                if (conversation.getState() == Conversation.ConversationState.UNSTARTED) {
                    startTimer();
                } else if (conversation.getState() ==  Conversation.ConversationState.STARTED) {
                    cancelling(conversation);
                    conversation.abandon(new ConversationAbandonedEvent(conversation, InactivityConversationCanceller.this));
                }
            }
        }, timeoutSeconds * 20);

    }

    /**
     * Stops the active inactivity timer.

    private void stopTimer() {
        if (taskId != -1) {
            plugin.getServer().getScheduler().cancelTask(taskId);
            taskId = -1;
        }
    }*/

    /**
     * Subclasses of InactivityConversationCanceller can override this method
     * to take additional actions when the inactivity timer abandons the
     * conversation.
     *
     * @param conversation The conversation being abandoned.
     */
    protected void cancelling(Conversation conversation) {

    }
}
