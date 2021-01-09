package ru.stonlex.bukkit.protocollib.packet.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.nbt.NbtBase;
import ru.stonlex.bukkit.protocollib.packet.AbstractPacket;

public class WrapperPlayServerTileEntityData extends AbstractPacket {
	public static final PacketType TYPE =
			PacketType.Play.Server.TILE_ENTITY_DATA;

	public WrapperPlayServerTileEntityData() {
		super(new PacketContainer(TYPE), TYPE);
		handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerTileEntityData(PacketContainer packet) {
		super(packet, TYPE);
	}

	/**
	 * Retrieve Location.
	 * 
	 * @return The current Location
	 */
	public BlockPosition getLocation() {
		return handle.getBlockPositionModifier().read(0);
	}

	/**
	 * Set Location.
	 * 
	 * @param value - new value.
	 */
	public void setLocation(BlockPosition value) {
		handle.getBlockPositionModifier().write(0, value);
	}

	/**
	 * Retrieve Action.
	 * <p>
	 * Notes: the type of update to perform
	 * 
	 * @return The current Action
	 */
	public int getAction() {
		return handle.getIntegers().read(0);
	}

	/**
	 * Set Action.
	 * 
	 * @param value - new value.
	 */
	public void setAction(int value) {
		handle.getIntegers().write(0, value);
	}

	/**
	 * Retrieve NBT Data.
	 * <p>
	 * Notes: if not present then its TAG_END (0)
	 * 
	 * @return The current NBT Data
	 */
	public NbtBase<?> getNbtData() {
		return handle.getNbtModifier().read(0);
	}

	/**
	 * Set NBT Data.
	 * 
	 * @param value - new value.
	 */
	public void setNbtData(NbtBase<?> value) {
		handle.getNbtModifier().write(0, value);
	}

}