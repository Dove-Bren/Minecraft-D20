package com.skyisland.d20.network.message;

import com.skyisland.d20.D20Mod;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Admin token from server to client
 * @author Skyler
 *
 */
public class AdminTokenMessage implements IMessage {

	public static class Handler implements IMessageHandler<AdminTokenMessage, IMessage> {

		@Override
		public IMessage onMessage(AdminTokenMessage message, MessageContext ctx) {
			//have tag, now read it into local config
			D20Mod.logger.info("Received admin token from server");

			return null;
		}
		
	}
	
	protected NBTTagCompound tag;
	
	
	public AdminTokenMessage() {
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
