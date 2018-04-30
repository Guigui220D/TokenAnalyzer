package bot;

import java.util.List;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.EnumSet;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IExtendedInvite;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IInvite;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

public class AnnotationListener 
{	

	@EventSubscriber
    public void onReadyEvent(ReadyEvent event) 
	{	
		IDiscordClient client = event.getClient();	
		System.out.println("Discord token analyst : a tool by Guigui220D");
		System.out.println("|");
        System.out.println(client.getApplicationName() + " (" + client.getApplicationClientID() + ')');
        for (IGuild guild : client.getGuilds())
        {
        	System.out.println('|');    
        	IUser user = client.getUsers().get(0);
        	System.out.println(">-- " + guild.getName() + " (" + guild.getLongID() + ") Members : " + guild.getTotalMemberCount());
        	System.out.println("|   |");
        	System.out.println("|   Text chats:");
        	for (IChannel chan : client.getChannels())
        	{
            	System.out.println("|   >-- # " + chan.getName() + " (" + chan.getLongID() + ")");
        	}
        	System.out.println("|   |");
        	System.out.println("|   Vocal chats:");
        	for (IChannel vchan : client.getVoiceChannels())
        	{
            	System.out.println("|   >-- o) " + vchan.getName() + " (" + vchan.getLongID() + ")");
        	}
        	System.out.println("|   |");
        	System.out.println("|   Roles:");
        	List<Permissions> perms = new ArrayList<Permissions>();
        	for (IRole role : client.getRoles())
        	{
        		if (role.getPermissions().contains(Permissions.ADMINISTRATOR))
        		{
        			System.out.println("|   >-- " + role.getName() + " (" + role.getLongID() + ")");
        		}
        		else
        		{
        			System.out.println("|   >-- " + role.getName() + " (" + role.getLongID() + ")");
        		}        	
            	if (user.hasRole(role))
            	{
            		System.out.println("|   |   BOT HAS THIS ROLE");
            		System.out.println("|   |   Perms : ");
            		for (Permissions p : role.getPermissions())
            		{
            			System.out.println("|   |   > " + p.toString());
            			if (!perms.contains(p))
            				perms.add(p);
            		}
            		System.out.println("|   |   '");
            	}
        	}
        	System.out.println("|   |");
        	System.out.println("|   Total Perms :");
        	for (Permissions p : perms)
        	{
        		System.out.println("|   > " + p.toString());
        	}
        	System.out.println("|   |");
        	System.out.println("|   Invitation links :");
        	int ivcount = 0;
        	if (perms.contains(Permissions.MANAGE_SERVER))
        	{
        		try 
        		{
                	for (IExtendedInvite inv : guild.getExtendedInvites())
                	{
                		if (inv.isRevoked())
                		{
                    		System.out.println("|   >-- https://discord.gg/" + inv.getCode() + " (REVOKED)");
                		}
                		else
                		{
                    		System.out.println("|   >-- https://discord.gg/" + inv.getCode());
                    		ivcount++;
                		}
                	}
        		}
        		catch (Exception e)
        		{
        			System.out.println("|   THE BOT DOESNT HAVE THE PERMISSION TO GET INVITATION LINKS");
        		}

        	}
        	else
        	{
        		System.out.println("|   THE BOT DOESNT HAVE THE PERMISSION TO GET INVITATION LINKS");
        	}
        	if (ivcount == 0)
        	{
        		System.out.println("|   No invites were found, trying to generate some" + "");
        		if (perms.contains(Permissions.CREATE_INVITE))
        		{
        			for (IChannel chan : guild.getChannels())
        			{
        				try 
        				{
        					IInvite i = chan.createInvite(10000, 10, false, false);
        					System.out.println("|   Succesfully created https://discord.gg/" + i.getCode());
        					ivcount++;
        					break;
        				}
        				catch (Exception e)
        				{
        					
        				}
        			}
                	if (ivcount == 0)
                		System.out.println("|   Could not create invitation links");
        		}
        		else
        		{
        			System.out.println("|   The bot does not have the permission to create links");
        		}
        	}

        	System.out.println("|   |");
        	System.out.println("|   Activity :");
        	int count1h = 0;
        	int count1d = 0;
        	int count1m = 0;
        	if (perms.contains(Permissions.READ_MESSAGES))
        	{
            	for (IChannel chan : guild.getChannels())
            	{
            		System.out.println("|   (Scanning channel : " + chan.getName() + ")...");
            		try 
            		{
            			for (IMessage mess : chan.getMessageHistoryTo(Instant.now().minus(1, ChronoUnit.HOURS)))
                			count1h++;
                		for (IMessage mess : chan.getMessageHistoryTo(Instant.now().minus(1, ChronoUnit.MINUTES)))
                			count1m++;
                		for (IMessage mess : chan.getMessageHistoryTo(Instant.now().minus(1, ChronoUnit.DAYS)))
                			count1d++;
            		}
            		catch (Exception e)
            		{
            			System.out.println("|   Cannot scan this channel");
            		}
            		
            	}
            	System.out.println("|   |");
            	System.out.println("|   >-- Messages since 1m : " + count1m);
            	System.out.println("|   >-- Messages since 1h : " + count1h);
            	System.out.println("|   >-- Messages since 24h : " + count1d);
        	}
        	else
        	{
        		System.out.println("|   THE BOT DOESNT HAVE THE PERMISSION TO READ MESSAGES");
        	}
        	System.out.println("|   '");
        }
        System.out.println("|");
        System.out.println("' END");
        System.exit(0);
    }
	
	@EventSubscriber
	public void OnMesageEvent(MessageReceivedEvent event)  
    {
		
    }	
}
