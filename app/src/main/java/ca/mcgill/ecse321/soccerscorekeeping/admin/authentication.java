package ca.mcgill.ecse321.soccerscorekeeping.admin;

public class authentication 
{
	final private static String managerPassword = "manager";
	final private static String scoreKeeperPassword = "keeper";
	
	public authentication()
	{
		
	}
	
	public static boolean authenticate(char[] password)
	{
		if(password.length!=managerPassword.length())
		{
			return false;
		}
		
		for(int i=0;i<password.length;i++)
		{
			if(!(managerPassword.charAt(i)==password[i]))
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean authenticateScoreKeeper(char[] password)
	{
		if(password.length!=scoreKeeperPassword.length())
		{
			return false;
		}
		
		for(int i=0;i<password.length;i++)
		{
			if(!(scoreKeeperPassword.charAt(i)==password[i]))
			{
				return false;
			}
		}
		return true;
	}
}
