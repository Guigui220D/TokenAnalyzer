package bot;

import sx.blah.discord.api.*;
import sx.blah.discord.util.DiscordException;

public class ChatBot 
{
	Boolean done = false;
	
	public static void main(String[] args) {	
		if (args.length == 0)
		{
			System.out.println("No token specified");
			return;
		}
		String defaultToken = args[0];
		IDiscordClient dc = createClient(defaultToken);
		if (dc  ==  null)
			System.exit(0);
		Module module = new Module();
		module.enable(dc);
	}

	public static IDiscordClient createClient(String token) 
	{ 
        ClientBuilder clientBuilder = new ClientBuilder(); 
        clientBuilder.withToken(token); 
        try 
        {
        	return clientBuilder.login(); // Creates the client instance and logs the client in
        } 
        catch (DiscordException e) 
        { 
            e.printStackTrace();
            System.out.println("Could not connect with token");
            return null;
        }
    }
}