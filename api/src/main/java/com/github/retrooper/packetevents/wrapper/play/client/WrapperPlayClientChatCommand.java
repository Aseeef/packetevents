package com.github.retrooper.packetevents.wrapper.play.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.util.crypto.MessageSignData;
import com.github.retrooper.packetevents.util.crypto.SaltSignature;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public class WrapperPlayClientChatCommand extends PacketWrapper<WrapperPlayClientChatCommand> {
    private String command;
    // 1.19+
    private @Nullable MessageSignData messageSignData;

    public WrapperPlayClientChatCommand(PacketReceiveEvent event) {
        super(event);
    }

    public WrapperPlayClientChatCommand(String command) {
        this(command, null);
    }

    public WrapperPlayClientChatCommand(String command, @Nullable MessageSignData messageSignData) {
        super(PacketType.Play.Client.CHAT_COMMAND);
        this.command = command;
        this.messageSignData = messageSignData;
    }

    @Override
    public void read() {
        int maxMessageLength = serverVersion.isNewerThanOrEquals(ServerVersion.V_1_11) ? 256 : 100;
        this.command = readString(maxMessageLength);
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            Instant timestamp = readTimestamp();
            SaltSignature saltSignature = readSaltSignature();
            boolean signedPreview = readBoolean();
            this.messageSignData = new MessageSignData(saltSignature, timestamp, signedPreview);
        }
    }

    @Override
    public void write() {
        writeString(command);
        if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
            writeTimestamp(messageSignData.getTimestamp());
            writeSaltSignature(messageSignData.getSaltSignature());
            writeBoolean(messageSignData.isSignedPreview());
        }
    }

    @Override
    public void copy(WrapperPlayClientChatCommand wrapper) {
        this.command = wrapper.command;
        this.messageSignData = wrapper.messageSignData;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public @Nullable MessageSignData getMessageSignData() {
        return messageSignData;
    }

    public void setMessageSignData(@Nullable MessageSignData messageSignData) {
        this.messageSignData = messageSignData;
    }
}