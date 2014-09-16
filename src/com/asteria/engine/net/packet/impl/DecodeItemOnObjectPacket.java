package com.asteria.engine.net.packet.impl;

import com.asteria.engine.net.ProtocolBuffer;
import com.asteria.engine.net.ProtocolBuffer.ByteOrder;
import com.asteria.engine.net.ProtocolBuffer.ValueType;
import com.asteria.engine.net.packet.PacketDecoder;
import com.asteria.engine.net.packet.PacketOpcodeHeader;
import com.asteria.world.entity.player.Player;
import com.asteria.world.item.Item;
import com.asteria.world.map.Position;

/**
 * Sent when the player uses an item on an object.
 * 
 * @author lare96
 */
@PacketOpcodeHeader({ 192 })
public class DecodeItemOnObjectPacket extends PacketDecoder {

    // TODO: When cache reading is done, check position of objects to
    // see if you're actually near them or not.

    @Override
    public void decode(Player player, ProtocolBuffer buf) {
        int container = buf.readShort(false);
        int objectId = buf.readShort(true, ByteOrder.LITTLE);
        int objectY = buf.readShort(true, ValueType.A, ByteOrder.LITTLE);
        int slot = buf.readShort(true, ByteOrder.LITTLE);
        int objectX = buf.readShort(true, ValueType.A, ByteOrder.LITTLE);
        int itemId = buf.readShort(false);
        int size = 1;

        if (container < 0 || objectId < 0 || objectY < 0 || slot < 0 || objectX < 0 || itemId < 0) {
            return;
        }
        // if (!def.valid(new Position(objectX, objectY, player.getPosition()
        // .getZ())))
        // return;

        Item item = player.getInventory().get(slot);

        if (item == null || container != 3214) {
            return;
        }

        player.facePosition(new Position(objectX, objectY));
        player.getMovementQueueListener().append(new Runnable() {
            @Override
            public void run() {
                if (player.getPosition().withinDistance(
                    new Position(objectX, objectY, player.getPosition().getZ()),
                    size)) {
                    switch (objectId) {

                    }

                    switch (itemId) {

                    }
                }
            }
        });
    }
}
