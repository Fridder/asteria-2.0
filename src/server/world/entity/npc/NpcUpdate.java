package server.world.entity.npc;

import java.util.Iterator;

import server.core.net.packet.PacketBuffer;
import server.core.net.packet.PacketBuffer.ByteOrder;
import server.core.net.packet.PacketBuffer.ValueType;
import server.core.worker.TaskFactory;
import server.util.Misc;
import server.world.World;
import server.world.entity.UpdateFlags.Flag;
import server.world.entity.player.Player;
import server.world.map.Position;

/**
 * Provides static utility methods for updating NPCs.
 * 
 * @author blakeman8192
 * @author lare96
 */
public class NpcUpdate {

    /**
     * Updates all NPCs for the argued Player.
     * 
     * @param player
     *            the argued player.
     */
    public static void update(Player player) throws Exception {
        // XXX: The buffer sizes may need to be tuned.
        PacketBuffer.WriteBuffer out = PacketBuffer.newWriteBuffer(2048);
        PacketBuffer.WriteBuffer block = PacketBuffer.newWriteBuffer(1024);

        /** Initialize the update packet. */
        out.writeVariableShortPacketHeader(65);
        out.setAccessType(PacketBuffer.AccessType.BIT_ACCESS);

        /** Update the NPCs in the local list. */
        out.writeBits(8, player.getNpcs().size());
        for (Iterator<Npc> i = player.getNpcs().iterator(); i.hasNext();) {
            Npc npc = i.next();
            if (npc.getPosition().isViewableFrom(player.getPosition())
                    && npc.isVisible()) {
                NpcUpdate.updateNpcMovement(out, npc);
                if (npc.getFlags().isUpdateRequired()) {
                    NpcUpdate.updateState(block, npc);
                }
            } else {
                /** Remove the NPC from the local list. */
                out.writeBit(true);
                out.writeBits(2, 3);
                i.remove();
            }
        }

        /** Update the local NPC list itself. */
        int added = 0;
        for (Npc npc : World.getNpcs()) {
            if (npc == null || added == 15 || player.getNpcs().size() >= 255
                    || player.getNpcs().contains(npc) || !npc.isVisible()) {
                continue;
            }

            if (npc.getPosition().isViewableFrom(player.getPosition())) {
                npc.getFlags().flag(Flag.APPEARANCE);
                player.getNpcs().add(npc);
                addNpc(out, player, npc);

                if (npc.getFlags().isUpdateRequired()) {
                    NpcUpdate.updateState(block, npc);
                }
                added++;
            }
        }

        /** Append the update block to the packet if need be. */
        if (block.getBuffer().position() > 0) {
            out.writeBits(14, 16383);
            out.setAccessType(PacketBuffer.AccessType.BYTE_ACCESS);
            out.writeBytes(block.getBuffer());
        } else {
            out.setAccessType(PacketBuffer.AccessType.BYTE_ACCESS);
        }

        /** Ship the packet out to the client. */
        out.finishVariableShortPacketHeader();
        player.getSession().encode(out);
    }

    /**
     * Adds the NPC to the client side local list.
     * 
     * @param out
     *            The buffer to write to.
     * @param player
     *            The player.
     * @param npc
     *            The NPC being added.
     */
    private static void addNpc(PacketBuffer.WriteBuffer out, Player player,
            Npc npc) {
        out.writeBits(14, npc.getSlot());
        Position delta = Misc.delta(player.getPosition(), npc.getPosition());
        out.writeBits(5, delta.getY());
        out.writeBits(5, delta.getX());
        out.writeBit(npc.getFlags().isUpdateRequired());
        out.writeBits(12, npc.getNpcId());
        out.writeBit(true);
    }

    /**
     * Updates the movement of a NPC for this cycle.
     * 
     * @param out
     *            The buffer to write to.
     * @param npc
     *            The NPC to update.
     */
    private static void updateNpcMovement(PacketBuffer.WriteBuffer out, Npc npc) {
        if (npc.getPrimaryDirection() == -1) {
            if (npc.getFlags().isUpdateRequired()) {
                out.writeBit(true);
                out.writeBits(2, 0);
            } else {
                out.writeBit(false);
            }
        } else {
            out.writeBit(true);
            out.writeBits(2, 1);
            out.writeBits(3, npc.getPrimaryDirection());

            if (npc.getFlags().isUpdateRequired()) {
                out.writeBit(true);
            } else {
                out.writeBit(false);
            }
        }
    }

    /**
     * Updates the state of the NPC to the given update block.
     * 
     * @param block
     *            The update block to append to.
     * @param npc
     *            The NPC to update.
     */
    private static void updateState(PacketBuffer.WriteBuffer block, Npc npc)
            throws Exception {
        int mask = 0x0;

        /** NPC update masks. */
        if (npc.getFlags().get(Flag.ANIMATION)) {
            mask |= 0x10;
        }
        if (npc.getFlags().get(Flag.HIT_2)) {
            mask |= 8;
        }
        if (npc.getFlags().get(Flag.GRAPHICS)) {
            mask |= 0x80;
        }
        if (npc.getFlags().get(Flag.FACE_ENTITY)) {
            mask |= 0x20;
        }
        if (npc.getFlags().get(Flag.FORCED_CHAT)) {
            mask |= 1;
        }
        if (npc.getFlags().get(Flag.HIT)) {
            mask |= 0x40;
        }
        if (npc.getFlags().get(Flag.FACE_COORDINATE)) {
            mask |= 4;
        }

        /** Write the update masks. */
        if (mask >= 0x100) {
            mask |= 0x40;
            block.writeShort(mask, PacketBuffer.ByteOrder.LITTLE);
        } else {
            block.writeByte(mask);
        }

        /** Append the NPC update blocks. */
        if (npc.getFlags().get(Flag.ANIMATION)) {
            appendAnimation(block, npc);
        }
        if (npc.getFlags().get(Flag.HIT_2)) {
            appendSecondaryHit(block, npc);
        }
        if (npc.getFlags().get(Flag.GRAPHICS)) {
            appendGfxUpdate(block, npc);
        }
        if (npc.getFlags().get(Flag.FACE_ENTITY)) {
            appendFaceEntity(block, npc);
        }
        if (npc.getFlags().get(Flag.FORCED_CHAT)) {
            appendForcedChat(block, npc);
        }
        if (npc.getFlags().get(Flag.HIT)) {
            appendPrimaryHit(block, npc);
        }
        if (npc.getFlags().get(Flag.FACE_COORDINATE)) {
            appendFaceCoordinate(block, npc);
        }
    }

    /**
     * Update the GFX block.
     * 
     * @param out
     *            the packet to write to.
     * @param npc
     *            the npc to append this update for.
     */
    private static void appendGfxUpdate(PacketBuffer.WriteBuffer out, Npc npc) {
        out.writeShort(npc.getGfx().getId());
        out.writeInt(npc.getGfx().getDelay());
    }

    /**
     * Update the secondary hit block.
     * 
     * @param out
     *            the packet to write to.
     * @param npc
     *            the npc to append this update for.
     */
    private static void appendSecondaryHit(PacketBuffer.WriteBuffer out, Npc npc)
            throws Exception {
        if (!npc.isHasDied()) {
            if (npc.getCurrentHP() <= 0) {
                npc.setCurrentHealth(0);
                npc.setHasDied(true);
                TaskFactory.getFactory().submit(npc.death());
            }
        }

        out.writeByte(npc.getSecondaryHit().getDamage(), ValueType.A);
        out.writeByte(npc.getSecondaryHit().getType().getId(), ValueType.C);
        out.writeByte(npc.getCurrentHP(), ValueType.A);
        out.writeByte(npc.getMaxHealth());
    }

    /**
     * Update the face entity block.
     * 
     * @param out
     *            the packet to write to.
     * @param npc
     *            the npc to append this update for.
     */
    private static void appendFaceEntity(PacketBuffer.WriteBuffer out, Npc npc) {
        out.writeShort(npc.getFaceIndex());
    }

    /**
     * Update the forced chat block.
     * 
     * @param out
     *            the packet to write to.
     * @param npc
     *            the npc to append this update for.
     */
    private static void appendForcedChat(PacketBuffer.WriteBuffer out, Npc npc) {
        out.writeString(npc.getForcedText());
    }

    /**
     * Update the primary hit block.
     * 
     * @param out
     *            the packet to write to.
     * @param npc
     *            the npc to append this update for.
     */
    private static void appendPrimaryHit(PacketBuffer.WriteBuffer out, Npc npc)
            throws Exception {
        if (!npc.isHasDied()) {
            if (npc.getCurrentHP() <= 0) {
                npc.setCurrentHealth(0);
                npc.setHasDied(true);
                TaskFactory.getFactory().submit(npc.death());
            }
        }

        out.writeByte(npc.getPrimaryHit().getDamage(), ValueType.C);
        out.writeByte(npc.getPrimaryHit().getType().getId(), ValueType.S);
        out.writeByte(npc.getCurrentHP(), ValueType.S);
        out.writeByte(npc.getMaxHealth(), ValueType.C);
    }

    /**
     * Update the face coordinate block.
     * 
     * @param out
     *            the packet to write to.
     * @param npc
     *            the npc to append this update for.
     */
    private static void appendFaceCoordinate(PacketBuffer.WriteBuffer out,
            Npc npc) {
        out.writeShort(npc.getFaceCoordinates().getX(), ByteOrder.LITTLE);
        out.writeShort(npc.getFaceCoordinates().getY(), ByteOrder.LITTLE);
    }

    /**
     * Update the animation block.
     * 
     * @param out
     *            the packet to write to.
     * @param npc
     *            the npc to append this update for.
     */
    private static void appendAnimation(PacketBuffer.WriteBuffer out, Npc npc) {
        out.writeShort(npc.getAnimation().getId(), ByteOrder.LITTLE);
        out.writeByte(npc.getAnimation().getDelay());
    }
}
