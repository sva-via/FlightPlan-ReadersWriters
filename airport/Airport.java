package airport;

import airport.FlightPlanAccess;
import model.Flight;
import model.FlightPlanWrite;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Airport implements Runnable
{
  private FlightPlanAccess access;

  public Airport(FlightPlanAccess access)
  {
    this.access = access;
  }

  public void run()
  {
    while (true)
    {
      doingSomething("Get updates from pilots", 9000, 12000);

      // get write access
      FlightPlanWrite flightPlan = access.acquireWrite();

      for (int i = 0; i < 10; i++)
      {
        String id = "" + (i + 100) * 10;
        Flight flight = flightPlan.getFlight(id);
        if (flight != null)
        {
          LocalDateTime time = flightPlan.getFlight(id).getDepartureTime();
          time = time.plus(30, ChronoUnit.MINUTES); // Delayed 30 minutes
          flightPlan.updateFlightDepartureDate(id, time);
        }
      }
      doingSomething("Update", 1000, 2000);
      flightPlan.removeFlightsBefore(LocalDateTime.now());

      access.releaseWrite();
      // end of write access

      doingSomething("Remove old flights", 1000, 2000);
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