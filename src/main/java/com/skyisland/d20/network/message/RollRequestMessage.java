package com.skyisland.d20.network.message;

import com.skyisland.d20.D20Mod;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Server-to-client message reporting a roll has been made
 * Params:
 *   - Roll result: the result of the roll
 * @author Skyler
 *
 */
public class RollRequestMessage implements IMessage {

	public static class Handler implements IMessageHandler<RollRequestMessage, IMessage> {

		@Override
		public IMessage onMessage(RollRequestMessage message, MessageContext ctx) {
			//Read sides from tag
			D20Mod.logger.info("Received request to roll. Checking credentials...");
			
			if (!D20Mod.adminRegistry.isAdmin(ctx.getServerHandler().playerEntity)) {
				D20Mod.logger.info("Bad authentication on roll request from "
						+ ctx.getServerHandler().playerEntity.getName());
				return null;
			}
			
			int sides = message.getSides();
			D20Mod.proxy.executeRoll(ctx.getServerHandler().playerEntity.getServer().getPlayerList().getPlayerList(), sides);
			
			return null;
		}
		
	}
	
	protected NBTTagCompound tag;
	private static final String KEY_SIDES = "sides";
	
	public RollRequestMessage(int sides) {
		tag = new NBTTagCompound();
		tag.setInteger(KEY_SIDES, sides);
	}
	
	public RollRequestMessage() {
		tag = new NBTTagCompound();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, tag);
	}
	
	public int getSides() {
		return tag.getInteger(KEY_SIDES);
	}

}
