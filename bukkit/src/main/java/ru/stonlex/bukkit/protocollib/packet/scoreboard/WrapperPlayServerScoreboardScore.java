package ru.stonlex.bukkit.protocollib.packet.scoreboard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ScoreboardAction;
import ru.stonlex.bukkit.protocollib.packet.AbstractPacket;

public class WrapperPlayServerScoreboardScore extends AbstractPacket {
	public static final PacketType TYPE =
			PacketType.Play.Server.SCOREBOARD_SCORE;

	public WrapperPlayServerScoreboardScore() {
		super(new PacketContainer(TYPE), TYPE);
		handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerScoreboardScore(PacketContainer packet) {
		super(packet, TYPE);
	}

	/**
	 * Retrieve Score name.
	 * <p>
	 * Notes: the name of the score to be updated or removed.
	 *
	 * @return The current Score name
	 */
	public String getScoreName() {
		return handle.getStrings().read(0);
	}

	/**
	 * Set Score name.
	 *
	 * @param value - new value.
	 */
	public void setScoreName(String value) {
		handle.getStrings().write(0, value);
	}

	/**
	 * Retrieve Objective Name.
	 * <p>
	 * Notes: the name of the objective the score belongs to.
	 *
	 * @return The current Objective Name
	 */
	public String getObjectiveName() {
		return handle.getStrings().read(1);
	}

	/**
	 * Set Objective Name.
	 *
	 * @param value - new value.
	 */
	public void setObjectiveName(String value) {
		handle.getStrings().write(1, value);
	}

	/**
	 * Retrieve Value.
	 * <p>
	 * Notes: the score to be displayed next to the entry. Only sent when
	 * Update/Remove does not equal 1.
	 *
	 * @return The current Value
	 */
	public int getValue() {
		return handle.getIntegers().read(0);
	}

	/**
	 * Set Value.
	 *
	 * @param value - new value.
	 */
	public void setValue(int value) {
		handle.getIntegers().write(0, value);
	}

	public ScoreboardAction getAction() {
		return handle.getScoreboardActions().read(0);
	}

	public void setScoreboardAction(ScoreboardAction value) {
		handle.getScoreboardActions().write(0, value);
	}

}