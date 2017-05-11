package com.skyisland.d20.network.message;

import com.skyisland.d20.D20Mod;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Admin has requested a D20 roll. Handled on SERVER
 * Params:
 *   - Sides: The number of sides the die has (or range of random)
 * @author Skyler
 *
 */
public class RollResultMessage implements IMessage {

	public static class Handler implements IMessageHandler<RollResultMessage, IMessage> {

		@Override
		public IMessage onMessage(RollResultMessage message, MessageContext ctx) {
			//Read sides from tag
			int result = message.getResult();
			D20Mod.logger.info("Received roll result (" + result + ")");
			
			D20Mod.proxy.displayRoll(result);
			
			return null;
		}
		
	}
	
	protected NBTTagCompound tag;
	private static final String KEY_RESULT = "result";
	
	public RollResultMessage(int sides) {
		tag = new NBTTagCompound();
		tag.setInteger(KEY_RESULT, sides);
	}
	
	public RollResultMessage() {
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
	
	public int getResult() {
		return tag.getInteger(KEY_RESULT);
	}

}
