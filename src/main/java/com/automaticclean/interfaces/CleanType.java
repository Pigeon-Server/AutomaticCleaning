package com.automaticclean.interfaces;

import java.util.Set;

public interface CleanType {
    boolean isCleanEnable();
    boolean isWhitelistMode();
    String getBeforeClean();
    String getCleanComplete();
    String getName();
    Set<String> getWhitelist();
    Set<String> getBlacklist();
    void setCleanEnable(boolean enable);
    void setWhitelistMode(boolean enable);
    void addWhitelist(String name);
    void delWhitelist(String name);
    void addBlacklist(String name);
    void delBlacklist(String name);
}
