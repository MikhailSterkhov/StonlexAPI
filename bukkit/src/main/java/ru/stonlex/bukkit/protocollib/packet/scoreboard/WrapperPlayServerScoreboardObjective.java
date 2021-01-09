package ru.stonlex.bukkit.protocollib.packet.scoreboard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.IntEnum;
import ru.stonlex.bukkit.protocollib.packet.AbstractPacket;

public class WrapperPlayServerScoreboardObjective extends AbstractPacket {
	public static final PacketType TYPE =
			PacketType.Play.Server.SCOREBOARD_OBJECTIVE;

	public WrapperPlayServerScoreboardObjective() {
		super(new PacketContainer(TYPE), TYPE);
		handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerScoreboardObjective(PacketContainer packet) {
		super(packet, TYPE);
	}

	/**
	 * Enum containing all known packet modes.
	 *
	 * @author dmulloy2
	 */
	public static class Mode extends IntEnum {
		public static final int ADD_OBJECTIVE = 0;
		public static final int REMOVE_OBJECTIVE = 1;
		public static final int UPDATE_VALUE = 2;

		private static final Mode INSTANCE = new Mode();

		public static Mode getInstance() {
			return INSTANCE;
		}
	}

	/**
	 * Retrieve Objective name.
	 * <p>
	 * Notes: an unique name for the objective
	 *
	 * @return The current Objective name
	 */
	public String getName() {
		return handle.getStrings().read(0);
	}

	/**
	 * Set Objective name.
	 *
	 * @param value - new value.
	 */
	public void setName(String value) {
		handle.getStrings().write(0, value);
	}

	/**
	 * Retrieve Objective DisplayName.
	 * <p>
	 * Notes: only if mode is 0 or 2. The text to be displayed for the score.
	 *
	 * @return The current Objective value
	 */
	public String getDisplayName() {
		return handle.getStrings().read(1);
	}

	/**
	 * Set Objective DisplayName.
	 *
	 * @param value - new value.
	 */
	public void setDisplayName(String value) {
		handle.getStrings().write(1, value);
	}

	/**
	 * Retrieve health display.
	 * <p>
	 * Notes: Can be either INTEGER or HEARTS
	 *
	 * @return
	 */
	public HealthDisplay getHealthDisplay() {
		return handle.getEnumModifier(HealthDisplay.class, 2).read(0);
	}

	/**
	 * Set health display.
	 *
	 * @param value - value
	 * @see #getHealthDisplay()
	 */
	public void setHealthDisplay(HealthDisplay value) {
		handle.getEnumModifier(HealthDisplay.class, 2).write(0, value);
	}

	/**
	 * Retrieve Mode.
	 * <p>
	 * Notes: 0 to create the scoreboard. 1 to remove the scoreboard. 2 to
	 * update the display text.
	 *
	 * @return The current Mode
	 */
	public int getMode() {
		return handle.getIntegers().read(0);
	}

	/**
	 * Set Mode.
	 *
	 * @param value - new value.
	 */
	public void setMode(int value) {
		handle.getIntegers().write(0, value);
	}

	public enum HealthDisplay {
		INTEGER, HEARTS;
	}
}