package model;

import java.util.ArrayList;

public interface FlightPlanRead
{
  public Flight getFlight(String id);
  public ArrayList<Flight> getFlightsTo(String to);
}
