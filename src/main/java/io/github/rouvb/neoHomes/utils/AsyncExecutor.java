package io.github.rouvb.neoHomes.utils;

import org.bukkit.Bukkit;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public final class AsyncExecutor {

    private final ExecutorService executor;

    public AsyncExecutor() {
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    public <T> CompletableFuture<T> supplyAsync(Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, executor)
                .exceptionally(ex -> {
                    Bukkit.getLogger().severe("Profile async error");
                    ex.printStackTrace();
                    return null;
                });
    }

    public CompletableFuture<Void> runAsync(Runnable task) {
        return CompletableFuture.runAsync(task, executor)
                .exceptionally(ex -> {
                    Bukkit.getLogger().severe("Profile async error");
                    ex.printStackTrace();
                    return null;
                });
    }

    public void shutdown() {
        executor.shutdown();
    }
}
