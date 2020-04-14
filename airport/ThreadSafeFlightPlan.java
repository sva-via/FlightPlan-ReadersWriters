package airport;
import model.FlightPlan;
import model.FlightPlanRead;
import model.FlightPlanWrite;

public class ThreadSafeFlightPlan implements FlightPlanAccess
{
  private int readers;
  private int writers;
  private int waitingWriters;      // Writer preference
  private FlightPlan flightPlan;

  public ThreadSafeFlightPlan(FlightPlan flightPlan)
  {
    this.flightPlan = flightPlan;
    this.readers = 0;
    this.writers = 0;
    this.waitingWriters = 0;      // Writer preference
  }

  @Override public synchronized FlightPlanRead acquireRead()
  {
    while (writers > 0 || waitingWriters > 0)  // Writer preference
    {
      try
      {
        String txt = " WAIT (readers=" + readers + ", writers=" + writers + ")";
        System.out.println(Thread.currentThread().getName() + txt);
        wait();
      }
      catch (InterruptedException e) {}
    }
    readers++;
    String txt = " READING (readers=" + readers + ", writers=" + writers + ")";
    System.out.println(Thread.currentThread().getName() + txt);
    return flightPlan;
  }

  @Override public synchronized void releaseRead()
  {
    readers--;
    if (readers == 0)
    {
      notify();
    }
    String txt =
        " FINISHED READING (readers=" + readers + ", writers=" + writers + ")";
    System.out.println(Thread.currentThread().getName() + txt);
  }

  @Override public synchronized FlightPlanWrite acquireWrite()
  {
    waitingWriters++;  // Writer preference
    while (readers > 0 || writers > 0)
    {
      try
      {
        String txt = " WAIT (readers=" + readers + ", writers=" + writers + ")";
        System.out.println(Thread.currentThread().getName() + txt);
        wait();
      }
      catch (InterruptedException e){}
    }
    waitingWriters--;  // Writer preference
    writers++;
    String txt = " WRITING (readers=" + readers + ", writers=" + writers + ")";
    System.out.println(Thread.currentThread().getName() + txt);
    return flightPlan;
  }

  @Override public synchronized void releaseWrite()
  {
    writers--;
    notifyAll();
    String txt =
        " FINISHED WRITING (readers=" + readers + ", writers=" + writers + ")";
    System.out.println(Thread.currentThread().getName() + txt);
  }
}