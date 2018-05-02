package bot;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
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

	FileWriter fw;
	Boolean filee = true;
	
	@EventSubscriber
    public void onReadyEvent(ReadyEvent event) 
	{	
		IDiscordClient client = event.getClient();	
		File file = new File(client.getToken() + ".txt");
		if (file.exists())
			file.delete();
		try {
			fw = new FileWriter(file);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Could not create filee");
			filee = false;
		}
		COut("Discord token analyst : a tool by Guigui220D");
		COut("Date : " + new SimpleDateFormat("yyyy MM dd HH:mm:ss").format(Calendar.getInstance().getTime()));
		COut("|");
        COut(client.getApplicationName() + " (" + client.getApplicationClientID() + ')');
        for (IGuild guild : client.getGuilds())
        {
        	COut("|");    
        	IUser user = client.getUsers().get(0);
        	COut(">-- " + guild.getName() + " (" + guild.getLongID() + ") Members : " + guild.getTotalMemberCount());
        	COut("|   |");
        	COut("|   Text chats:");
        	for (IChannel chan : client.getChannels())
        	{
            	COut("|   >-- # " + chan.getName() + " (" + chan.getLongID() + ")");
        	}
        	COut("|   |");
        	COut("|   Vocal chats:");
        	for (IChannel vchan : client.getVoiceChannels())
        	{
            	COut("|   >-- o) " + vchan.getName() + " (" + vchan.getLongID() + ")");
        	}
        	COut("|   |");
        	COut("|   Roles:");
        	List<Permissions> perms = new ArrayList<Permissions>();
        	for (IRole role : client.getRoles())
        	{
        		if (role.getPermissions().contains(Permissions.ADMINISTRATOR))
        		{
        			COut("|   >-- " + role.getName() + " (" + role.getLongID() + ")");
        		}
        		else
        		{
        			COut("|   >-- " + role.getName() + " (" + role.getLongID() + ")");
        		}        	
            	if (user.hasRole(role))
            	{
            		COut("|   |   BOT HAS THIS ROLE");
            		COut("|   |   Perms : ");
            		for (Permissions p : role.getPermissions())
            		{
            			COut("|   |   > " + p.toString());
            			if (!perms.contains(p))
            				perms.add(p);
            		}
            		COut("|   |   '");
            	}
        	}
        	COut("|   |");
        	COut("|   Total Perms :");
        	for (Permissions p : perms)
        	{
        		COut("|   > " + p.toString());
        	}
        	COut("|   |");
        	COut("|   Invitation links :");
        	int ivcount = 0;
        	if (perms.contains(Permissions.MANAGE_SERVER))
        	{
        		try 
        		{
                	for (IExtendedInvite inv : guild.getExtendedInvites())
                	{
                		if (inv.isRevoked())
                		{
                    		COut("|   >-- https://discord.gg/" + inv.getCode() + " (REVOKED)");
                		}
                		else
                		{
                    		COut("|   >-- https://discord.gg/" + inv.getCode());
                    		ivcount++;
                		}
                	}
        		}
        		catch (Exception e)
        		{
        			COut("|   THE BOT DOESNT HAVE THE PERMISSION TO GET INVITATION LINKS");
        		}

        	}
        	else
        	{
        		COut("|   THE BOT DOESNT HAVE THE PERMISSION TO GET INVITATION LINKS");
        	}
        	if (ivcount == 0)
        	{
        		COut("|   No invites were found, trying to generate some" + "");
        		if (perms.contains(Permissions.CREATE_INVITE))
        		{
        			for (IChannel chan : guild.getChannels())
        			{
        				try 
        				{
        					IInvite i = chan.createInvite(10000, 10, false, false);
        					COut("|   Succesfully created https://discord.gg/" + i.getCode());
        					ivcount++;
        					break;
        				}
        				catch (Exception e)
        				{
        					
        				}
        			}
                	if (ivcount == 0)
                		COut("|   Could not create invitation links");
        		}
        		else
        		{
        			COut("|   The bot does not have the permission to create links");
        		}
        	}

        	COut("|   |");
        	COut("|   Activity :");
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
            			COut("|   Cannot scan this channel");
            		}
            		
            	}
            	COut("|   |");
            	COut("|   >-- Messages since 1m : " + count1m);
            	COut("|   >-- Messages since 1h : " + count1h);
            	COut("|   >-- Messages since 24h : " + count1d);
        	}
        	else
        	{
        		COut("|   THE BOT DOESNT HAVE THE PERMISSION TO READ MESSAGES");
        	}
        	COut("|   '");
        }
        COut("|");
        COut("' END");
        if (filee)
        {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        System.exit(0);
    }
	
	public void COut(String mess)
	{
		System.out.println(mess);
		if (filee)
		{
			try {
				fw.write(mess + '\n');
			} catch (IOException e) { }
		}
	}
}
