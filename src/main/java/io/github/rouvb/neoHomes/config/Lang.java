package io.github.rouvb.neoHomes.config;

import lombok.Getter;

@Getter
public enum Lang {
    HOME_NOT_FOUND("home.not_found"),
    HOME_DELETED("home.deleted"),
    HOME_SET("home.set"),
    TELEPORT_STARTING("teleport.starting"),
    TELEPORT_CANCELLED_MOVED("teleport.cancelled.moved"),
    TELEPORT_CANCELLED_DAMAGED("teleport.cancelled.damaged"),
    TELEPORT_SUCCESS("teleport.success"),
    COMMAND_DELHOME_USAGE("command.delhome.usage"),
    COMMAND_SETHOME_USAGE("command.sethome.usage"),
    COMMAND_HOME_USAGE("command.home.usage"),
    ERROR_MAX_LIMIT("error.max_limit"),
    ERROR_BLACKLISTED_WORLD("errors.blacklisted_world"),
    ERROR_BLACKLISTED_WORD("errors.blacklisted_word");

    private final String path;

    Lang(String path) {
        this.path = path;
    }
}
