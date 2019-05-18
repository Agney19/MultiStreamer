package com.moneytransfer.model;

import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractModel {
    protected static final int BATCH_SIZE = 25;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Transient
    private UUID uuid;

    @Column(name = "uuid", nullable = false, updatable = false, unique = true, length = 36)
    private String uuidStr;

    @PrePersist
    protected void prePersist() {
        syncUuidString();
    }

    public UUID getUuid() {
        if (uuidStr == null) {
            if (uuid == null) {
                uuid = UUID.randomUUID();
            }
            uuidStr = uuid.toString();
        }
        if (uuid == null) {
            uuid = UUID.fromString(uuidStr);
        }
        return uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !this.getClass().isInstance(o)) {
            return false;
        }

        AbstractModel that = (AbstractModel) o;

        if (getUuid() != null ? !getUuid().equals(that.getUuid()) : that.getUuid() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getUuid() != null ? getUuid().hashCode() : 0;
    }

    public Long getVersion() {
        return version;
    }

    public String getUuidStr() {
        return uuidStr;
    }

    @Override
    public String toString() {
        return Optional.ofNullable(getId()).map(id -> Long.toString(id)).orElse(null);
    }

    private void syncUuidString() {
        if (uuidStr == null) {
            getUuid();
        }
    }
}
