package com.skyisland.d20.network.message;

import com.skyisland.d20.D20Mod;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
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
public class RequestRollMessage implements IMessage {

	public static class Handler implements IMessageHandler<RequestRollMessage, IMessage> {

		@Override
		public IMessage onMessage(RequestRollMessage message, MessageContext ctx) {
			//Read sides from tag
			D20Mod.logger.info("Received request to roll. Checking credentials...");
			
			//TODO check credentials. If good, execute a roll
			//ctx.getServerHandler().playerEntity
			

			return null;
		}
		
	}
	
	protected NBTTagCompound tag;
	
	
	public RequestRollMessage() {
		tag = new NBTTagCompound();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		//tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		//ByteBufUtils.writeTag(buf, tag);
	}

}
