package xyz.herty.ocextras.common.interfaces;

import java.util.UUID;

public interface IOwner {
    void setOwner(UUID uuid);
    UUID getOwner();
}
