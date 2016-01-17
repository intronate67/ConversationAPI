package net.huntersharpe.conversationapi;

import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

/**
 * Created by intronate67 on 1/4/2016.
 */
public class PluginNameConversationPrefix implements ConversationPrefix {

    protected String separator;
    protected TextColor prefixColor;
    protected Plugin plugin;

    private String cachedPrefix;

    public PluginNameConversationPrefix(Plugin plugin) {
        this(plugin, " > ", TextColors.LIGHT_PURPLE);
    }

    public PluginNameConversationPrefix(Plugin plugin, String separator, TextColor prefixColor) {
        this.separator = separator;
        this.prefixColor = prefixColor;
        this.plugin = plugin;

        cachedPrefix = prefixColor + plugin.name() + separator + TextColors.WHITE;
    }

    /**
     * Prepends each conversation message with the plugin name.
     *
     * @param context Context information about the conversation.
     * @return An empty string.
     */
    public String getPrefix(ConversationContext context) {
        return cachedPrefix;
    }
}