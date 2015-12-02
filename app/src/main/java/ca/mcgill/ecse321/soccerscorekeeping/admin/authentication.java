package ca.mcgill.ecse321.soccerscorekeeping.admin;

import java.util.HashMap;

public class authentication
{
	private static HashMap<String,String> userNames;


	public authentication()
	{
		userNames = new HashMap<String,String>();
		userNames.put("owais","git");
		userNames.put("npurdie","git");
		userNames.put("jers","git");
		userNames.put("nevmx","git");
		userNames.put("mcintosh","bluejays");
		userNames.put("walter","white");

	}

	public boolean authenticate(String username,char[] password)
	{
		if(userNames.get(username)==null)
		{
			return false;
		}

		for(int i=0;i<password.length;i++)
		{
			if(!(userNames.get(username).charAt(i)==password[i]))
			{
				return false;
			}
		}
		return true;
	}

	public boolean authenticateScoreKeeper(String username,char[] password)
	{
		if(userNames.get(username)==null)
		{
			return false;
		}

		for(int i=0;i<password.length;i++)
		{
			if(!(userNames.get(username).charAt(i)==password[i]))
			{
				return false;
			}
		}
		return true;
	}
}