package ru.stonlex.bukkit.protocollib.packet.scoreboard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import ru.stonlex.bukkit.protocollib.packet.AbstractPacket;

public class WrapperPlayServerScoreboardDisplayObjective extends AbstractPacket {
	public static final PacketType TYPE =
			PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE;

	public WrapperPlayServerScoreboardDisplayObjective() {
		super(new PacketContainer(TYPE), TYPE);
		handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerScoreboardDisplayObjective(PacketContainer packet) {
		super(packet, TYPE);
	}

	/**
	 * Retrieve Position.
	 * <p>
	 * Notes: the position of the scoreboard. 0 = list, 1 = sidebar, 2 =
	 * belowName.
	 *
	 * @return The current Position
	 */
	public int getPosition() {
		return handle.getIntegers().read(0);
	}

	/**
	 * Set Position.
	 *
	 * @param value - new value.
	 */
	public void setPosition(int value) {
		handle.getIntegers().write(0, value);
	}

	/**
	 * Retrieve Score Name.
	 * <p>
	 * Notes: the unique name for the scoreboard to be displayed.
	 *
	 * @return The current Score Name
	 */
	public String getScoreName() {
		return handle.getStrings().read(0);
	}

	/**
	 * Set Score Name.
	 *
	 * @param value - new value.
	 */
	public void setScoreName(String value) {
		handle.getStrings().write(0, value);
	}

}