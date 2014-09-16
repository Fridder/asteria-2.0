package com.asteria.engine.net.packet.impl;

import java.util.Optional;

import com.asteria.engine.net.ProtocolBuffer;
import com.asteria.engine.net.ProtocolBuffer.ByteOrder;
import com.asteria.engine.net.packet.PacketDecoder;
import com.asteria.engine.net.packet.PacketOpcodeHeader;
import com.asteria.world.entity.player.Player;
import com.asteria.world.entity.player.skill.Skills;
import com.asteria.world.item.Item;
import com.asteria.world.item.ground.GroundItem;
import com.asteria.world.item.ground.GroundItemManager;
import com.asteria.world.map.Position;

/**
 * Sent when the player attempts to pickup an item.
 * 
 * @author lare96
 */
@PacketOpcodeHeader({ 236 })
public class DecodePickupItemPacket extends PacketDecoder {

    @Override
    public void decode(Player player, ProtocolBuffer buf) {
        int itemY = buf.readShort(ByteOrder.LITTLE);
        int itemId = buf.readShort(false);
        int itemX = buf.readShort(ByteOrder.LITTLE);

        if (itemY < 0 || itemId < 0 || itemX < 0) {
            return;
        }

        Skills.fireSkillEvents(player);

        player.getMovementQueueListener().append(new Runnable() {
            @Override
            public void run() {
                if (player.getPosition().equals(
                    new Position(itemX, itemY, player.getPosition().getZ()))) {

                    Optional<GroundItem> item = GroundItemManager.getItem(
                        itemId, new Position(itemX, itemY,
                            player.getPosition().getZ()));

                    if (!item.isPresent()) {
                        return;
                    }

                    if (!player.getInventory()

                    .spaceFor(
                        new Item(itemId, item.get().getItem().getAmount()))) {
                        player.getPacketBuilder().sendMessage(
                            "You don't have enough free inventory space to pickup this item.");
                        return;
                    }

                    item.get().fireOnPickup(player);
                }
            }
        });
    }
}
