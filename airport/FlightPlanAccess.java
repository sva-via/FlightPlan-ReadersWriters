package airport;

import model.FlightPlanWrite;
import model.FlightPlanRead;

public interface FlightPlanAccess
{
  public FlightPlanRead acquireRead();
  public void releaseRead();
  public FlightPlanWrite acquireWrite();
  public void releaseWrite();
}