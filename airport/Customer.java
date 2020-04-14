package airport;

import model.Flight;
import model.FlightPlanRead;

import java.util.ArrayList;

public class Customer implements Runnable
{
  private FlightPlanAccess access;

  public Customer(FlightPlanAccess access)
  {
    this.access = access;
  }

  public void run()
  {
    for (int i = 0; i < 10; i++)
    {
      doingSomething("Checking my money", 1000, 2000);

      // get read access
      FlightPlanRead flightPlan = access.acquireRead();

      ArrayList<Flight> flights = flightPlan.getFlightsTo("London");
      String flightId = "?";
      if (flights.size() > 0)
      {
        flightId = flights.get(0).getId();
      }
      Flight flight = flightPlan.getFlight(flightId);
      doingSomething("Reading", 1000, 2000);

      access.releaseRead();
    }
  }

  private void doingSomething(String what, int minTime, int maxTime)
  {
    int time = (int) (Math.random() * (maxTime - minTime) + minTime);
    try
    {
      Thread.sleep(time);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }

}