package com.github.openwebnet.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import static com.google.common.base.Preconditions.checkNotNull;

public class IpcamModel extends RealmObject implements RealmModel, DomoticModel {

    public static final String FIELD_URL = "url";
    public static final String FIELD_STREAM_TYPE = "type";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";

    // TODO validation
    public enum StreamType {
        MJPEG
    }

    @PrimaryKey
    private String uuid;

    @Required
    private Integer environmentId;

    @Required
    private String name;

    @Required
    private String url;

    @Required
    private String type;

    private String username;

    private String password;

    private boolean favourite;

    // NOT USED: realm error otherwise
    @Ignore
    private StreamType streamType;
    @Ignore
    private String gatewayUuid;

    public IpcamModel() {}

    private IpcamModel(Builder builder) {
        this.uuid = builder.uuid;
        this.environmentId = builder.environmentId;
        this.name = builder.name;
        this.url = builder.url;
        this.type = builder.type;
        this.username = builder.username;
        this.password = builder.password;
        this.favourite = builder.favourite;
    }

    public static class Builder {

        private final String uuid;
        private Integer environmentId;
        private String name;
        private String url;
        private String type;
        private String username;
        private String password;
        private boolean favourite;

        public Builder(String uuid) {
            this.uuid = uuid;
        }

        public Builder environment(Integer environmentId) {
            this.environmentId = environmentId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        // TODO validation toUpperCase
        public Builder String(StreamType type) {
            this.type = type.name();
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder favourite(boolean favourite) {
            this.favourite = favourite;
            return this;
        }

        public IpcamModel build() {
            checkNotNull(environmentId, "environmentId is null");
            checkNotNull(name, "name is null");
            checkNotNull(url, "url is null");
            checkNotNull(type, "type is null");

            return new IpcamModel(this);
        }
    }

    public static Builder addBuilder() {
        return new Builder(UUID.randomUUID().toString());
    }

    public static Builder updateBuilder(String uuid) {
        return new Builder(uuid);
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public Integer getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Integer environmentId) {
        this.environmentId = environmentId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @see IpcamModel#getStreamType()
     */
    public String getType() {
        return type;
    }

    /**
     * @see IpcamModel#setStreamType(StreamType)
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    // TODO validation toUpperCase
    public StreamType getStreamType() {
        return StreamType.valueOf(getType());
    }

    // TODO validation toUpperCase
    public void setStreamType(StreamType streamType) {
        this.type = streamType.name();
    }

    @Override
    public String getGatewayUuid() {
        throw new UnsupportedOperationException("not implemented");
    }

    public void setGatewayUuid(String gatewayUuid) {
        throw new UnsupportedOperationException("not implemented");
    }

}
