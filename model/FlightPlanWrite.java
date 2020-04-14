package model;

import java.time.LocalDateTime;

public interface FlightPlanWrite extends FlightPlanRead
{
  public void updateFlightDepartureDate(String flightId, LocalDateTime time);
  public void removeFlightsBefore(LocalDateTime time);
}
