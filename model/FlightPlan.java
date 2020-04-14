package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class FlightPlan implements FlightPlanWrite
{
  private ArrayList<Flight> flights;

  public FlightPlan()
  {
    flights = new ArrayList<>();
    createDummyData();
  }

  // READ METHODS

  @Override
  public Flight getFlight(String id)
  {
    for (int i = 0; i < flights.size(); i++)
    {
      if (flights.get(i).getId().equals(id))
      {
        return flights.get(i).copy();
      }
    }
    return null;
  }

  @Override
  public ArrayList<Flight> getFlightsTo(String to)
  {
    ArrayList<Flight> flightsTo = new ArrayList<>();
    for (int i = 0; i < flights.size(); i++)
    {
      if (flights.get(i).getTo().equalsIgnoreCase(to))
      {
        flightsTo.add(flights.get(i).copy());
      }
    }
    return flightsTo;
  }

  // WRITE METHODS

  @Override
  public void updateFlightDepartureDate(String flightId,
      LocalDateTime time)
  {
    for (int i = flights.size() - 1; i >= 0; i--)
    {
      if (flights.get(i).getId().equals(flightId))
      {
        flights.get(i).setDepartureTime(time);
      }
    }
  }

  @Override
  public void removeFlightsBefore(LocalDateTime time)
  {
    for (int i = flights.size() - 1; i >= 0; i--)
    {
      if (flights.get(i).getDepartureTime().isBefore(time))
      {
        flights.remove(i);
      }
    }
  }

  // PRIVATE METHOD

  private void createDummyData()
  {
    String[] cities = { "Paris", "Amsterdam", "London", "New York", "Rome",
        "Reykjavik", "Sofia", "Berlin" };

    LocalDateTime time = LocalDateTime.of(2019, 4, 26, 6, 0);
    for (int i = 100; i < 10100; i++)
    {
      String to = cities[(int) (Math.random() * cities.length)];
      time = time.plus(2, ChronoUnit.HOURS);
      flights.add(new Flight("" + i, "Copenhagen", to, time));
    }
  }
}
