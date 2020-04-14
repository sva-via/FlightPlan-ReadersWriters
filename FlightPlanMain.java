import airport.Airport;
import airport.Customer;
import airport.FlightPlanAccess;
import airport.ThreadSafeFlightPlan;
import model.FlightPlan;

public class FlightPlanMain
{
   public static void main(String[] args)
   {
      FlightPlan flightPlan = new FlightPlan();
      FlightPlanAccess access = new ThreadSafeFlightPlan(flightPlan);
      
      Thread[] readers = new Thread[30]; 
      for (int i=0; i<readers.length; i++)
      {
         Customer reader = new Customer(access);
         readers[i] = new Thread(reader, "Reader"+ i);
         readers[i].start();
      }
      
      Thread[] writers = new Thread[5]; 
      for (int i=0; i<writers.length; i++)
      {
         Airport writer = new Airport(access);
         writers[i] = new Thread(writer, "Writer" + i);
         writers[i].start();
      }
   }
}