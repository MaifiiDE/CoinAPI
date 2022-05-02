package de.maifii.coinapi.api;

import java.util.UUID;

public interface ICoinAPI {

    Integer getCoins(UUID uuid);

    void addCoins(UUID uuid, int count);
    void removeCoins(UUID uuid, int count);
    void setCoins(UUID uuid, int count);


}
