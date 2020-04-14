package airport;

import model.FlightPlanRead;
import model.FlightPlanWrite;

public interface FlightPlanAccess
{
  public FlightPlanRead acquireRead();
  public void releaseRead();
  public FlightPlanWrite acquireWrite();
  public void releaseWrite();
}