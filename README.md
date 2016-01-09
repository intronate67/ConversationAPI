# ConversationAPI
Spigot/Bukkit's Conversation API ported to work with sponge.

#How to use

## While there are plenty of ways to add a github repo as a dependency, the easiest way, in my opinion, is using jitpack.

* You can either goto their website [here](https://jitpack.io/) and input the link to my repo, or add the following code to your build.gradle.

```gralde
repositories {
  maven{
    url = "https://jitpack.io"
  }
}
dependencies{
  compile 'com.github.intronate67:ConversationAPI:0.1.0'
}
```

