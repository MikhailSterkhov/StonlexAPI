package ru.stonlex.bukkit.depend.vault;

public interface IVaultPlayer {

    String getPlayerName();

    String getDisplayName();


    String getPrefix();

    String getGroupPrefix();


    String getSuffix();

    String getGroupSuffix();


    String getPrimaryGroup();

    String[] getGroups();


    double getBalance();


    boolean hasPermission(String permission);

    boolean hasGroup(String groupName);

    boolean hasMoney(double moneyCount);


    void setMoney(double moneyCount);

    void giveMoney(double moneyToGive);

    void takeMoney(double moneyToTake);


    void addPermission(String permission);

    void removePermission(String permission);


    void addGroup(String groupName);

    void removeGroup(String groupName);

}
