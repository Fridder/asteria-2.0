package com.asteria.engine.net.packet.impl;

import java.util.Optional;

import com.asteria.engine.net.ProtocolBuffer;
import com.asteria.engine.net.ProtocolBuffer.ByteOrder;
import com.asteria.engine.net.packet.PacketDecoder;
import com.asteria.engine.net.packet.PacketOpcodeHeader;
import com.asteria.world.World;
import com.asteria.world.entity.player.Player;
import com.asteria.world.entity.player.minigame.Minigame;
import com.asteria.world.entity.player.minigame.Minigames;
import com.asteria.world.entity.player.skill.Skills;

/**
 * Sent when the player sends another player some sort of request using the
 * <code>sendMessage</code> method.
 * 
 * @author lare96
 */
@PacketOpcodeHeader({ 139 })
public class DecodeRequestPacket extends PacketDecoder {

    @Override
    public void decode(Player player, ProtocolBuffer buf) {
        int requestId = buf.readShort(true, ByteOrder.LITTLE);

        if (requestId < 0) {
            return;
        }

        Player request = World.getPlayers().get(requestId);

        // Make sure the player to is real and close to us.
        if (request == null || !request.getPosition().isViewableFrom(
            player.getPosition()) || request.equals(player)) {
            return;
        }

        Skills.fireSkillEvents(player);

        switch (player.getSession().getPacketOpcode()) {
        case 139:

            // Check if we can trade based on the minigame we're in.
            Optional<Minigame> optional = Minigames.get(player);
            if (optional.isPresent()) {
                if (!optional.get().canTrade(player, request)) {
                    return;
                }
            }

            player.getTradeSession().request(request);
            break;

        }
    }
}
