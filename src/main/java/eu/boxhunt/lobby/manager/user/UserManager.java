package eu.boxhunt.lobby.manager.user;

import eu.boxhunt.lobby.object.user.User;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class UserManager {

    private final ConcurrentHashMap<UUID, User> userMap = new ConcurrentHashMap<>();

    public CompletableFuture<User> compute(UUID uuid){
        return CompletableFuture.completedFuture(userMap.computeIfAbsent(uuid, User::new));
    }

    public Optional<User> get(UUID uuid){
        return Optional.ofNullable(userMap.get(uuid));
    }
}
