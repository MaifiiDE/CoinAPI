package de.maifii.coinapi.api;

import java.util.UUID;

public interface ICoinAPI {
    Integer getCoins(UUID paramUUID);

    void addCoins(UUID paramUUID, int paramInt);

    void removeCoins(UUID paramUUID, int paramInt);

    void setCoins(UUID paramUUID, int paramInt);
}
