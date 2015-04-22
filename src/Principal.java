import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.Scanner;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;


public class Principal {

	/**
	 * @param args
	 * @throws JSONException 
	 */
	public static int getGoals(JSONArray events, String p) throws JSONException
	{
		int countOfGoalOnThisMatch = 0;
    	for (int j = 0; j< events.length() ; j++)
    	{
    		JSONObject ob = events.getJSONObject(j);
    		if (ob.getString("player").equalsIgnoreCase(p) && ob.getString("type_of_event").equalsIgnoreCase("goal"))
    		{
    			countOfGoalOnThisMatch++;
    		}
    	}
    	return countOfGoalOnThisMatch;
	}
	public static int getMatchesByCountry(String fifaCode, String player)
	{
		int acum = 0;
		JSONArray events;
		try {
			Resty resty = new Resty();
	        JSONResource jsResource = null;
	        //Getting HTTP REQUEST
	        jsResource = resty.json("http://worldcup.sfg.io/matches/country?fifa_code=" + fifaCode);
	        //Convert JSON RESOURCE to Array
	        JSONArray matches = jsResource.array();
		    //Looking for the team in all matches
	        for(int i=0; i < matches.length(); i++){
                JSONObject obj = matches.getJSONObject(i);
                JSONObject country = (JSONObject) obj.get("home_team");
                //If a home or away team
                if (country.getString("code").equalsIgnoreCase(fifaCode))
                {
                	events = obj.getJSONArray("home_team_events");
                }else
                {
                	events = obj.getJSONArray("away_team_events");
                }
                
                acum += getGoals(events, player);
		    }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return acum;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		System.out.println("Escriba el codigo FIFA del pais");
		String n = s.nextLine();
		System.out.println("Escriba el nombre del jugador");
		String player = s.nextLine(); 
		System.out.println(player + " Hizo un total de " + getMatchesByCountry(n,player) + " Goles");
		
	}

}
