package movies;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Iterable;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessCommandServlet extends HttpServlet 
{
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
    	
        Key movieKey = KeyFactory.createKey("Movies", "Purdue");
        
        
      /*you don't need to worry about the variable below, this gets the value of the 
       * string entered in the text area as defined in the movies.jsp file
       */
        String content = req.getParameter("command");
        
        
        /*This string array contains the individual elements of the 
        command entered in the text area, e.g. if commandEls[0] gives "add_actor", 
        commandEls[1] gives the actor name, commandEls[2] gives the gender
        and commandEls[3] gives the date of birth*/ 
        String [] commandEls = content.split(":");
        
        /*This string contains the results to display to the user once a command is entered.
         * For a query, it should list the results of the query. 
         * For an insertion or deletion, it should either contain an error message or 
         * the message "Command executed successfully!"*/
        String results = null;
        
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        /*your implementation starts here*/
        
        if ( commandEls[0].equals( "add_actor" ) ) 
        {
        	String name = commandEls[1];
        	String gender = commandEls[2];
        	String date_of_birth = commandEls[3];
        	
        	boolean duplicate = false;
        	
        	//check if actor exists in datastore, update value of duplicate
        	Query query = new Query("Actor");
        	query.addFilter("actorName", Query.FilterOperator.EQUAL, name);
        	
        	PreparedQuery pquery = datastore.prepare(query);
        	for(Entity e : pquery.asIterable())
        	{
        		duplicate = true;
        	}
        	
        	//if no duplicates, add an actor record with the given fields to the datastore
        	if (!duplicate) {
        		
        		//set the value of the "results" string
        		Entity newActor = new Entity("Actor");
        		newActor.setProperty("actorName", name);
        		newActor.setProperty("actorGender", gender);
        		newActor.setProperty("actorDOB", date_of_birth);
        		
        		try
        		{
        			datastore.put(newActor);
        			results = "Command executed successfully!";
        		}
        		catch(Exception e)
        		{
        			results = "Error : " + e.getMessage();
        		}
        		
        		
        	}
        	else 
        	{
        		results = "Error: Actor already exists!";
        	}
        	
        }
        else if ( commandEls[0].equals( "add_director" ) ) 
        {
        	String name = commandEls[1];
        	String gender = commandEls[2];
        	String date_of_birth = commandEls[3];
        	
        	boolean duplicate = false;
        	
        	//check if director exists in datastore, update value of duplicate
        	Query query = new Query("Director");
        	query.addFilter("directorName", Query.FilterOperator.EQUAL, name);
        	
        	PreparedQuery pquery = datastore.prepare(query);
        	for(Entity e : pquery.asIterable())
        	{
        		duplicate = true;
        	}
        	
        	//if no duplicates, add an actor record with the given fields to the datastore
        	if (!duplicate) {
        		
        		//set the value of the "results" string
        		Entity newDirector = new Entity("Director");
        		newDirector.setProperty("directorName", name);
        		newDirector.setProperty("directorGender", gender);
        		newDirector.setProperty("directorDOB", date_of_birth);
        		
        		try
        		{
        			datastore.put(newDirector);
        			results = "Command executed successfully!";
        		}
        		catch(Exception e)
        		{
        			results = "Error : " + e.getMessage();
        		}
        		
        		
        	}
        	else 
        	{
        		results = "Error: Director already exists!";
        	}
        }
        else if(commandEls[0].equals("add_company"))
        {
        	String name = commandEls[1];
        	String address = commandEls[2];
        	
        	boolean duplicate = false;
        	
        	//check if company exists in datastore, update value of duplicate
        	Query query = new Query("Company");
        	query.addFilter("companyName", Query.FilterOperator.EQUAL, name);
        	
        	PreparedQuery pquery = datastore.prepare(query);
        	for(Entity e : pquery.asIterable())
        	{
        		duplicate = true;
        	}
        	
        	//if no duplicates, add an actor record with the given fields to the datastore
        	if (!duplicate) {
        		
        		//set the value of the "results" string
        		Entity newCompany = new Entity("Company");
        		newCompany.setProperty("companyName", name);
        		newCompany.setProperty("companyAddress", address);
        		
        		try
        		{
        			datastore.put(newCompany);
        			results = "Command executed successfully!";
        		}
        		catch(Exception e)
        		{
        			results = "Error : " + e.getMessage();
        		}
        	}
        	else 
        	{
        		results = "Error: Company already exists!";
        	}
        }
        else if(commandEls[0].equals("add_movie"))
        {
        	String name = commandEls[1];
        	String releaseYear = commandEls[2];
        	String length = commandEls[3];
        	String genre = commandEls[4];
        	String plot = commandEls[5];
        	String director = commandEls[6];
        	String company = commandEls[7];
        	
        	boolean movieExists = false;
        	boolean directorExists = false;
        	boolean companyExists = false;
        	
        	Query directorQuery = new Query("Director");
        	directorQuery.addFilter("directorName", Query.FilterOperator.EQUAL, director);
        	PreparedQuery preparedDirectorQuery = datastore.prepare(directorQuery);
        	
        	for(Entity e : preparedDirectorQuery.asIterable())
        	{
        		directorExists = true;
        	}
        	
        	Query companyQuery = new Query("Company");
        	companyQuery.addFilter("companyName", Query.FilterOperator.EQUAL, company);
        	PreparedQuery preparedCompanyQuery = datastore.prepare(companyQuery);
        	
        	for(Entity e : preparedCompanyQuery.asIterable())
        	{
        		companyExists = true;
        	}
        	
        	Query movieQuery = new Query("Movie");
        	movieQuery.addFilter("movieName", Query.FilterOperator.EQUAL, name);
        	movieQuery.addFilter("movieReleaseYear", Query.FilterOperator.EQUAL, releaseYear);
        	PreparedQuery preparedMovieQuery = datastore.prepare(movieQuery);
        	
        	for(Entity e : preparedMovieQuery.asIterable())
        	{
        		movieExists = true;
        	}
        	
        	if(directorExists && companyExists && !movieExists)
        	{
        		Entity newMovie = new Entity("Movie");
        		newMovie.setProperty("movieName", name);
        		newMovie.setProperty("movieReleaseYear", releaseYear);
        		newMovie.setProperty("movieLength", length);
        		newMovie.setProperty("movieGenre", genre);
        		newMovie.setProperty("moviePlot", plot);
        		newMovie.setProperty("movieDirector", director);
        		newMovie.setProperty("movieCompany", company);
        		
        		try
        		{
        			datastore.put(newMovie);
        			results = "Command executed successfully!";
        		}
        		catch(Exception e)
        		{
        			results = "Error: "  + e.getMessage();
        		}
        	}
        	else if(movieExists)
        	{
        		results = "Error: Movie already exists!";
        	}
        	else if(!directorExists || !companyExists)
        	{
        		results = "Error: Foreign Key constraints violated!";
        	}
        }
        else if(commandEls[0].equals("add_awards_event"))
        {
        	String name = commandEls[1];
        	String year = commandEls[2];
        	String venue = commandEls[3];
        	
        	boolean eventExists = false;
        	
        	Query eventQuery = new Query("Event");
        	eventQuery.addFilter("eventName", Query.FilterOperator.EQUAL, name);
        	eventQuery.addFilter("eventYear", Query.FilterOperator.EQUAL, year);
        	PreparedQuery preparedEventQuery = datastore.prepare(eventQuery);
        	
        	for(Entity e : preparedEventQuery.asIterable())
        	{
        		eventExists = true;
        	}
        	
        	if(!eventExists)
        	{
        		Entity newEvent = new Entity("Event");
        		newEvent.setProperty("eventName", name);
        		newEvent.setProperty("eventYear", year);
        		newEvent.setProperty("eventVenue", venue);
        		
        		try
        		{
        			datastore.put(newEvent);
        			results = "Command executed successfully!";
        		}
        		catch(Exception e)
        		{
        			results = "Error: " + e.getMessage();
        		}
        	}
        	else
        	{
        		results = "Error: Event already exists!";
        	}
        }
        else if(commandEls[0].equals("add_user"))
        {
        	String userId = commandEls[1];
        	
        	boolean userExists = false;
        	
        	Query userQuery = new Query("User");
        	userQuery.addFilter("userId", Query.FilterOperator.EQUAL, userId);
        	PreparedQuery preparedUserQuery = datastore.prepare(userQuery);
        	
        	for(Entity e : preparedUserQuery.asIterable())
        	{
        		userExists = true;
        	}
        	
        	if(!userExists)
        	{
        		Entity newUser = new Entity("User");
        		newUser.setProperty("userId", userId);
        		
        		try
        		{
        			datastore.put(newUser);
        			results = "Command executed successfully!";
        		}
        		catch(Exception e)
        		{
        			results = "Error: " + e.getMessage();
        		}
        	}
        	else
        	{
        		results = "Error: User already exists!"; 
        	}
        }
        else if(commandEls[0].equals("add_movie_rating"))
        {
        	String userId = commandEls[1];
        	String movieTitle = commandEls[2];
        	String releaseYear = commandEls[3];
        	String rating = commandEls[4];
        	
        	boolean userExists = false;
        	boolean ratingExists = false;
        	boolean movieExists = false;
        	
        	Query ratingQuery = new Query("Rating");
        	ratingQuery.addFilter("movieRatingUserId", Query.FilterOperator.EQUAL,userId);
        	ratingQuery.addFilter("movieRatingMovieName", Query.FilterOperator.EQUAL, movieTitle);
        	ratingQuery.addFilter("movieRatingMovieReleaseYear", Query.FilterOperator.EQUAL, releaseYear);
        	PreparedQuery preparedRatingQuery = datastore.prepare(ratingQuery);
        	
        	Query movieQuery = new Query("Movie");
        	movieQuery.addFilter("movieName", Query.FilterOperator.EQUAL, movieTitle);
        	movieQuery.addFilter("movieReleaseYear", Query.FilterOperator.EQUAL, releaseYear);
        	PreparedQuery preparedMovieQuery = datastore.prepare(movieQuery);
        	
        	Query userQuery = new Query("User");
        	userQuery.addFilter("userId", Query.FilterOperator.EQUAL, userId);
        	PreparedQuery preparedUserQuery = datastore.prepare(userQuery);
        	
        	for(Entity e : preparedUserQuery.asIterable())
        	{
        		userExists = true;
        	}
        	
        	for(Entity e : preparedMovieQuery.asIterable())
        	{
        		movieExists = true;
        	}
        	
        	for(Entity e : preparedRatingQuery.asIterable())
        	{
        		ratingExists = true;
        	}
        	
        	if(userExists && movieExists && !ratingExists)
        	{
        		Entity newRating = new Entity("Rating");
        		newRating.setProperty("movieRatingUserId", userId);
        		newRating.setProperty("movieRatingMovieName", movieTitle);
        		newRating.setProperty("movieRatingMovieReleaseYear", releaseYear);
        		newRating.setProperty("movieRatingMovieRating", rating);
        		
        		try
        		{
        			datastore.put(newRating);
        			results = "Command executed successfully!";
        		}
        		catch(Exception e)
        		{
        			results = "Error: " + e.getMessage();
        		}
        	}
        	else if(ratingExists)
        	{
        		results = "Error: Rating already Exists!";
        	}
        	else if(!userExists || !movieExists)
        	{
        		results = "Error: Foreign key constraints violated!";
        	}
        }
        else if(commandEls[0].equals("add_cast"))
        {
        	String movieName = commandEls[1];
        	String releaseYear = commandEls[2];
        	String actorName = commandEls[3];
        	String role = commandEls[4];
        	
        	boolean castExists = false;
        	boolean movieExists = false;
        	boolean actorExists = false;
        	
        	Query castQuery = new Query("Cast");
        	castQuery.addFilter("castMovieName", Query.FilterOperator.EQUAL, movieName);
        	castQuery.addFilter("castMovieReleaseYear", Query.FilterOperator.EQUAL, releaseYear);
        	castQuery.addFilter("castActorName", Query.FilterOperator.EQUAL, actorName);
        	PreparedQuery preparedCastQuery = datastore.prepare(castQuery);
        	
        	Query movieQuery = new Query("Movie");
        	movieQuery.addFilter("movieName", Query.FilterOperator.EQUAL, movieName);
        	movieQuery.addFilter("movieReleaseYear", Query.FilterOperator.EQUAL, releaseYear);
        	PreparedQuery preparedMovieQuery = datastore.prepare(movieQuery);
        	
        	Query actorQuery = new Query("Actor");
        	actorQuery.addFilter("actorName", Query.FilterOperator.EQUAL, actorName);
        	PreparedQuery preparedActorQuery = datastore.prepare(actorQuery);
        	
        	for(Entity e : preparedCastQuery.asIterable())
        	{
        		castExists = true;
        	}
        	
        	for(Entity e : preparedMovieQuery.asIterable())
        	{
        		movieExists = true;
        	}
        	
        	for(Entity e : preparedActorQuery.asIterable())
        	{
        		actorExists = true;
        	}
        	
        	if(movieExists && actorExists && !castExists)
        	{
        		Entity newCast = new Entity("Cast");
        		newCast.setProperty("castMovieName", movieName);
        		newCast.setProperty("castMovieReleaseYear", releaseYear);
        		newCast.setProperty("castActorName", actorName);
        		newCast.setProperty("castRole", role);
        		
        		try
        		{
        			datastore.put(newCast);
        			results = "Command executed successfully!";
        		}
        		catch(Exception e)
        		{
        			results = "Error: " + e.getMessage();
        		}
        	}
        	else if(castExists)
        	{
        		results = "Error: Cast already exists!";
        	}
        	else if(!movieExists || !actorExists)
        	{
        		results = "Error: Foreign key constraints violated!";
        	}
        }
        else if(commandEls[0].equals("add_nomination_category"))
        {
        	String nominationCategoryName = commandEls[1];
        	
        	boolean nominationCategoryExists = false;
        	
        	Query categoryQuery = new Query("Category");
        	categoryQuery.addFilter("nominationCategoryName", Query.FilterOperator.EQUAL, nominationCategoryName);
        	PreparedQuery preparedCategoryQuery = datastore.prepare(categoryQuery);
        	
        	for(Entity e : preparedCategoryQuery.asIterable())
        	{
        		nominationCategoryExists = true;
        	}
        	
        	if(!nominationCategoryExists)
        	{
        		Entity newCategory = new Entity("Category");
        		newCategory.setProperty("nominationCategoryName", nominationCategoryName);
        		
        		try
        		{
        			datastore.put(newCategory);
        			results = "Command executed successfully!";
        		}
        		catch(Exception e)
        		{
        			results = "Error: " + e.getMessage();
        		}
        	}
        	else
        	{
        		results = "Error: Category already exists!";
        	}
       }
       else if(commandEls[0].equals("add_nomination"))
       {
    	   String movieName = commandEls[1];
    	   String releaseYear = commandEls[2];
    	   String eventName = commandEls[3];
    	   String eventYear = commandEls[4];
    	   String category = commandEls[5];
    	   String won = commandEls[6];
    	   
    	   boolean nominationExists = false;
    	   boolean movieExists = false;
    	   boolean eventExists = false;
    	   boolean categoryExists = false;
    	   
    	   Query nominationQuery = new Query("Nomination");
    	   nominationQuery.addFilter("nominationMovieName", Query.FilterOperator.EQUAL, movieName);
    	   nominationQuery.addFilter("nominationMovieReleaseYear", Query.FilterOperator.EQUAL, releaseYear);
    	   nominationQuery.addFilter("nominationEventName", Query.FilterOperator.EQUAL, eventName);
    	   nominationQuery.addFilter("nominationEventYear", Query.FilterOperator.EQUAL, eventYear);
    	   nominationQuery.addFilter("nominationCategory", Query.FilterOperator.EQUAL, category);
    	   PreparedQuery preparedNominationQuery = datastore.prepare(nominationQuery);
    	   
    	   Query movieQuery = new Query("Movie");
       	   movieQuery.addFilter("movieName", Query.FilterOperator.EQUAL, movieName);
       	   movieQuery.addFilter("movieReleaseYear", Query.FilterOperator.EQUAL, releaseYear);
       	   PreparedQuery preparedMovieQuery = datastore.prepare(movieQuery);
    	   
       	   Query eventQuery = new Query("Event");
       	   eventQuery.addFilter("eventName", Query.FilterOperator.EQUAL, eventName);
       	   eventQuery.addFilter("eventYear", Query.FilterOperator.EQUAL, eventYear);
       	   PreparedQuery preparedEventQuery = datastore.prepare(eventQuery);
    	   
       	   Query categoryQuery = new Query("Category");
       	   categoryQuery.addFilter("nominationCategoryName", Query.FilterOperator.EQUAL, category);
       	   PreparedQuery preparedCategoryQuery = datastore.prepare(categoryQuery);
       	   
       	   for(Entity e : preparedNominationQuery.asIterable())
       	   {
       		   nominationExists = true;
       	   }
       	   
       	   for(Entity e : preparedMovieQuery.asIterable())
       	   {
       		   movieExists = true;
       	   }
       	   
       	   for(Entity e : preparedEventQuery.asIterable())
       	   {
       		   eventExists = true;
       	   }
       	   
       	   for(Entity e : preparedCategoryQuery.asIterable())
       	   {
       		   categoryExists = true;
       	   }
       	   
       	   if(!nominationExists && movieExists && eventExists && categoryExists)
       	   {
       		   Entity newNomination = new Entity("Nomination");
       		   newNomination.setProperty("nominationMovieName", movieName);
       		   newNomination.setProperty("nominationMovieReleaseYear", releaseYear);
       		   newNomination.setProperty("nominationEventName", eventName);
       		   newNomination.setProperty("nominationEventYear", eventYear);
       		   newNomination.setProperty("nominationCategory", category);
       		   newNomination.setProperty("nominationWon", won);
       		   
       		   try
       		   {
       			   datastore.put(newNomination);
       			   results = "Command executed successfully!";
       		   }
       		   catch(Exception e)
       		   {
       			   results = "Error: " + e.getMessage();
       		   }
       	   }
       	   else if(nominationExists)
       	   {
       		   results = "Error: Nomination already exists!";
       	   }
       	   else if(!movieExists || !eventExists || !categoryExists)
       	   {
       		   results = "Error: Foreign key constraints violated!";
       	   }
       }
       else if(commandEls[0].equals("get_movies_by_company"))
       {
    	   String company = commandEls[1];
    	   
    	   Query companyQuery = new Query("Movie");
    	   companyQuery.addFilter("movieCompany", Query.FilterOperator.EQUAL, company);
    	   PreparedQuery preparedCompanyQuery = datastore.prepare(companyQuery);
    	   
    	   Iterator<Entity> iterator = preparedCompanyQuery.asIterable().iterator();
    	   
    	   while(iterator.hasNext())
    	   {
    		   Entity e = iterator.next();
    		   
    		   if(results == null)
    		   {
    			   results = e.getProperty("movieName") + "," + e.getProperty("movieReleaseYear");
    		   }
    		   else
    		   {
    		   		results += e.getProperty("movieName") + "," + e.getProperty("movieReleaseYear");
    		   }
    		   
    		   if(iterator.hasNext())
    		   {
    			   results += "; ";
    		   }
    	   }
       }
       else if(commandEls[0].equals("get_movies_by_director"))
       {
    	   String director = commandEls[1];
    	   
    	   Query directorQuery = new Query("Movie");
    	   directorQuery.addFilter("movieDirector", Query.FilterOperator.EQUAL, director);
    	   PreparedQuery preparedDirectorQuery = datastore.prepare(directorQuery);
    	   
    	   Iterator<Entity> iterator = preparedDirectorQuery.asIterable().iterator();
    	   
    	   while(iterator.hasNext())
    	   {
    		   Entity e = iterator.next();
    		   
    		   if(results == null)
    			   results = e.getProperty("movieName") + "," + e.getProperty("movieReleaseYear");
    		   else
    			   results += e.getProperty("movieName") + "," + e.getProperty("movieReleaseYear");
    		   
    		   if(iterator.hasNext())
    			   results += "; ";
    	   }
       }
       else if(commandEls[0].equals("get_nominations_for_actor"))
       {
    	   String actor = commandEls[1];
    	   String gender = null;
    	   
    	   Query genderQuery = new Query("Actor");
    	   genderQuery.addFilter("actorName", Query.FilterOperator.EQUAL, actor);
    	   PreparedQuery preparedGenderQuery = datastore.prepare(genderQuery);
    	   
    	   Iterator<Entity> genderIterator = preparedGenderQuery.asIterable().iterator();
    	   
    	   while(genderIterator.hasNext())
    	   {
    		   Entity e = genderIterator.next();
    		   gender = e.getProperty("actorGender").toString();
    	   }
    	   
    	   if(gender.equals("Male"))
    	   {
    		   Query leadNominationQuery = new Query("Nomination");
    		   leadNominationQuery.addFilter("nominationCategory", Query.FilterOperator.EQUAL, "best lead actor");
    		   PreparedQuery preparedLeadNominationQuery = datastore.prepare(leadNominationQuery);
    		   
    		   Query supportingNominationQuery = new Query("Nomination");
    		   supportingNominationQuery.addFilter("nominationCategory", Query.FilterOperator.EQUAL, "best supporting actor");
    		   PreparedQuery preparedSupportingNominationQuery = datastore.prepare(supportingNominationQuery);
    		   
    		   Query leadCastQuery = new Query("Cast");
    		   leadCastQuery.addFilter("castActorName", Query.FilterOperator.EQUAL, actor);
    		   leadCastQuery.addFilter("castRole", Query.FilterOperator.EQUAL, "lead actor");
    		   PreparedQuery preparedLeadCastQuery = datastore.prepare(leadCastQuery);
    		   
    		   Query supportingCastQuery = new Query("Cast");
    		   supportingCastQuery.addFilter("castActorName", Query.FilterOperator.EQUAL, actor);
    		   supportingCastQuery.addFilter("castRole", Query.FilterOperator.EQUAL, "supporting actor");
    		   PreparedQuery preparedSupportingCastQuery = datastore.prepare(supportingCastQuery);
    		   
    		   Iterator<Entity> leadCastIterator = preparedLeadCastQuery.asIterable().iterator();
    		   ArrayList<Entity> entities = new ArrayList<Entity>();
    		   
    		   while(leadCastIterator.hasNext())
    		   {
    			   Entity cast = leadCastIterator.next();
        		   Iterator<Entity> leadNominationIterator = preparedLeadNominationQuery.asIterable().iterator();
    			   while(leadNominationIterator.hasNext())
    			   {
    				   Entity nomination = leadNominationIterator.next();
    				   
    				   if(cast.getProperty("castMovieName").equals(nomination.getProperty("nominationMovieName")) && cast.getProperty("castMovieReleaseYear").equals(nomination.getProperty("nominationMovieReleaseYear")))
    				   {
    					   entities.add(nomination);	
    				   }
    			   }
    		   }
    		   
    		   Iterator<Entity> supportingCastIterator = preparedSupportingCastQuery.asIterable().iterator();
    		   
    		   while(supportingCastIterator.hasNext())
    		   {
    			   Entity cast = supportingCastIterator.next();
        		   Iterator<Entity> supportingNominationIterator = preparedSupportingNominationQuery.asIterable().iterator();
    			   while(supportingNominationIterator.hasNext())
    			   {
    				   Entity nomination = supportingNominationIterator.next();
    				   
    				   if(cast.getProperty("castMovieName").equals(nomination.getProperty("nominationMovieName")) && cast.getProperty("castMovieReleaseYear").equals(nomination.getProperty("nominationMovieReleaseYear")))
    				   {
   					   		entities.add(nomination);
    				   }
    			   }
    		   }
    		   
    		   for(int i = 0; i < entities.size(); i++)
    		   {
				   if(results == null)
				   		results = entities.get(i).getProperty("nominationEventName") + "," + entities.get(i).getProperty("nominationEventYear") + "," + entities.get(i).getProperty("nominationCategory") + "," + entities.get(i).getProperty("nominationWon");
				   	else
				   		results += entities.get(i).getProperty("nominationEventName") + "," + entities.get(i).getProperty("nominationEventYear") + "," + entities.get(i).getProperty("nominationCategory") + "," + entities.get(i).getProperty("nominationWon");
				   	
				   	if(i != (entities.size()-1))
				   		results += "; ";
    		   }
    	   }
    	   else if(gender.equals("Female"))
    	   {
    		   Query leadNominationQuery = new Query("Nomination");
    		   leadNominationQuery.addFilter("nominationCategory", Query.FilterOperator.EQUAL, "best lead actress");
    		   PreparedQuery preparedLeadNominationQuery = datastore.prepare(leadNominationQuery);
    		   
    		   Query supportingNominationQuery = new Query("Nomination");
    		   supportingNominationQuery.addFilter("nominationCategory", Query.FilterOperator.EQUAL, "best supporting actress");
    		   PreparedQuery preparedSupportingNominationQuery = datastore.prepare(supportingNominationQuery);
    		   
    		   Query leadCastQuery = new Query("Cast");
    		   leadCastQuery.addFilter("castActorName", Query.FilterOperator.EQUAL, actor);
    		   leadCastQuery.addFilter("castRole", Query.FilterOperator.EQUAL, "lead actress");
    		   PreparedQuery preparedLeadCastQuery = datastore.prepare(leadCastQuery);
    		   
    		   Query supportingCastQuery = new Query("Cast");
    		   supportingCastQuery.addFilter("castActorName", Query.FilterOperator.EQUAL, actor);
    		   supportingCastQuery.addFilter("castRole", Query.FilterOperator.EQUAL, "supporting actress");
    		   PreparedQuery preparedSupportingCastQuery = datastore.prepare(supportingCastQuery);
    		   
    		   Iterator<Entity> leadCastIterator = preparedLeadCastQuery.asIterable().iterator();
    		   ArrayList<Entity> entities = new ArrayList<Entity>();
    		   
    		   while(leadCastIterator.hasNext())
    		   {
    			   Entity cast = leadCastIterator.next();
        		   Iterator<Entity> leadNominationIterator = preparedLeadNominationQuery.asIterable().iterator();
    			   while(leadNominationIterator.hasNext())
    			   {
    				   Entity nomination = leadNominationIterator.next();
    				   
    				   if(cast.getProperty("castMovieName").equals(nomination.getProperty("nominationMovieName")) && cast.getProperty("castMovieReleaseYear").equals(nomination.getProperty("nominationMovieReleaseYear")))
    				   {
    					   entities.add(nomination);
    				   }
    			   }
    		   }
    		   
    		   Iterator<Entity> supportingCastIterator = preparedSupportingCastQuery.asIterable().iterator();
    		   
    		   while(supportingCastIterator.hasNext())
    		   {
    			   Entity cast = supportingCastIterator.next();
        		   Iterator<Entity> supportingNominationIterator = preparedSupportingNominationQuery.asIterable().iterator();
    			   while(supportingNominationIterator.hasNext())
    			   {
    				   Entity nomination = supportingNominationIterator.next();
    				   
    				   if(cast.getProperty("castMovieName").equals(nomination.getProperty("nominationMovieName")) && cast.getProperty("castMovieReleaseYear").equals(nomination.getProperty("nominationMovieReleaseYear")))
    				   {
    					   entities.add(nomination);
    				   }
    			   }
    		   }
    		   
    		   for(int i = 0; i < entities.size(); i++)
    		   {
				   if(results == null)
				   		results = entities.get(i).getProperty("nominationEventName") + "," + entities.get(i).getProperty("nominationEventYear") + "," + entities.get(i).getProperty("nominationCategory") + "," + entities.get(i).getProperty("nominationWon");
				   	else
				   		results += entities.get(i).getProperty("nominationEventName") + "," + entities.get(i).getProperty("nominationEventYear") + "," + entities.get(i).getProperty("nominationCategory") + "," + entities.get(i).getProperty("nominationWon");
				   	
				   	if(i != (entities.size()-1))
				   		results += "; ";
    		   }
    	   }
    	}
       	else if(commandEls[0].equals("get_movies_of_genre_for_actor"))
       	{
       		String actor = commandEls[1];
       		String genre = commandEls[2];
       		
       		Query genreQuery = new Query("Movie");
       		genreQuery.addFilter("movieGenre", Query.FilterOperator.EQUAL, genre);
       		PreparedQuery preparedGenreQuery = datastore.prepare(genreQuery);
       		
       		Query castQuery = new Query("Cast");
       		castQuery.addFilter("castActorName", Query.FilterOperator.EQUAL, actor);
       		PreparedQuery preparedCastQuery = datastore.prepare(castQuery);
       		
       		Iterator<Entity> castIterator = preparedCastQuery.asIterable().iterator();
       		ArrayList<Entity> entities = new ArrayList<Entity>();
       		
       		while(castIterator.hasNext())
       		{
       			Entity cast = castIterator.next();
       			Iterator<Entity> genreIterator = preparedGenreQuery.asIterable().iterator();
       			
       			while(genreIterator.hasNext())
       			{
       				Entity movie = genreIterator.next();
       				
       				if(cast.getProperty("castMovieName").equals(movie.getProperty("movieName")) && cast.getProperty("castMovieReleaseYear").equals(movie.getProperty("movieReleaseYear")))
       				{
       					entities.add(movie);
     				}
       			}
       		}
       		
       		for(int i = 0; i < entities.size(); i++)
       		{
				if(results == null)
   					results = entities.get(i).getProperty("movieName") + "," + entities.get(i).getProperty("movieReleaseYear");
   				else
   					results += entities.get(i).getProperty("movieName") + "," + entities.get(i).getProperty("movieReleaseYear");
				
				if(i != (entities.size()-1))
					results += "; ";
					
       		}
       	}
       	else if(commandEls[0].equals("get_number_of_nominations_for_movie"))
       	{
       		String movieName = commandEls[1];
       		String releaseYear = commandEls[2];
       		
       		Query nominationQuery = new Query("Nomination");
       		nominationQuery.addFilter("nominationMovieName", Query.FilterOperator.EQUAL, movieName);
       		nominationQuery.addFilter("nominationMovieReleaseYear", Query.FilterOperator.EQUAL, releaseYear);
       		PreparedQuery preparedNominationQuery = datastore.prepare(nominationQuery);
       		
       		int nominations = 0;
       		for(Entity e: preparedNominationQuery.asIterable())
       		{
       			nominations++;
       		}
       		
       		results = "" + nominations;
       	}
       	else if(commandEls[0].equals("get_average_rating_for_movie"))
       	{
       		String movieName = commandEls[1];
       		String releaseYear = commandEls[2];
       		
       		Query ratingQuery = new Query("Rating");
       		ratingQuery.addFilter("movieRatingMovieName", Query.FilterOperator.EQUAL, movieName);
       		ratingQuery.addFilter("movieRatingMovieReleaseYear", Query.FilterOperator.EQUAL, releaseYear);
       		PreparedQuery preparedRatingQuery = datastore.prepare(ratingQuery);
       		
       		int movies = 0;
       		double avg = 0;
       		
       		for(Entity e : preparedRatingQuery.asIterable())
       		{
       			avg += Double.parseDouble(e.getProperty("movieRatingMovieRating").toString());
       			movies++;
       		}
       		
       		avg = avg/movies;
       		
       		results = "" + avg;
       	}
       	else if(commandEls[0].equals("get_average_rating_of_user"))
       	{
       		String user = commandEls[1];
       		
       		Query ratingQuery = new Query("Rating");
       		ratingQuery.addFilter("movieRatingUserId", Query.FilterOperator.EQUAL, user);
       		PreparedQuery preparedRatingQuery = datastore.prepare(ratingQuery);
       		
       		int movies = 0;
       		double avg = 0;
       		
       		for(Entity e : preparedRatingQuery.asIterable())
       		{
       			avg += Double.parseDouble(e.getProperty("movieRatingMovieRating").toString());
       			movies++;
       		}
       		
       		avg = avg/movies;
       		if(movies != 0)
       			results = "" + avg;
       	}
       	else if(commandEls[0].equals("delete_company"))
       	{
       		String company = commandEls[1];
       		
       		Query companyQuery = new Query("Movie");
       		companyQuery.addFilter("movieCompany", Query.FilterOperator.EQUAL, company);
       		PreparedQuery preparedCompanyQuery = datastore.prepare(companyQuery);
       		
       		boolean hasProduced = false;
       		
       		for(Entity e : preparedCompanyQuery.asIterable())	
       			hasProduced = true;
       		
       		if(!hasProduced)
       		{
       			Query deleteQuery = new Query("Company");
       			deleteQuery.addFilter("companyName", Query.FilterOperator.EQUAL, company);
       			PreparedQuery preparedDeleteQuery = datastore.prepare(deleteQuery);
       			
       			for(Entity e : preparedDeleteQuery.asIterable())
       			{
       				Key key = e.getKey();
       				try
       				{
       					datastore.delete(key);
       					results = "Command executed successfully!";
       				}
       				catch(Exception error)
       				{
       					results = "Error: " + error.getMessage();
       				}
       			}
       			
       		}
       		else
       		{
       			results = "Error: Referential integrity violation!";
       		}
       	}
       	else if(commandEls[0].equals("delete_user"))
       	{
       		String user = commandEls[1];
       		
       		Query userQuery = new Query("Rating");
       		userQuery.addFilter("movieRatingUserId", Query.FilterOperator.EQUAL, user);
       		PreparedQuery preparedUserQuery = datastore.prepare(userQuery);
       		
       		boolean hasRated = false;
       		
       		for(Entity e : preparedUserQuery.asIterable())
       			hasRated = true;
       		
       		if(!hasRated)
       		{
       			Query deleteQuery = new Query("User");
       			deleteQuery.addFilter("userId", Query.FilterOperator.EQUAL, user);
       			PreparedQuery preparedDeleteQuery = datastore.prepare(deleteQuery);
       			
       			for(Entity e : preparedDeleteQuery.asIterable())
       			{
       				Key key = e.getKey();
       				
       				try
       				{
       					datastore.delete(key);
       					results = "Command executed successfully!";
       				}
       				catch(Exception error)
       				{
       					results = "Error: " + error.getMessage();
       				}
       			}
       		}
       		else
       		{
       			results = "Error: Referential integrity violation!";
       		}
       	}
        
        //Include else-if statements for processing all the other commands 
        
        /*your implementation ends here */
        
        
        resp.sendRedirect( "/movies.jsp?moviedbName=Purdue&display=" + results );
    }  

}
