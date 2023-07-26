import java.util.ArrayList;
import java.util.Scanner;

/**
uses ant colony optimization to solve the CVRP
@author YOUR NAME HERE
@version YOUR DATE HERE
*/
public class CVRP_ACO
{
   /** depot has not been visited yet */
   public static final int NOT_VISITED = 0;
   
   /** depot has already been visited */
   public static final int VISITED = 1;

   /** probability that truck chooses next depot randomly */
   public static final double RANDOM_DEPOT_FACTOR = 0.1;
   
   /** maximum number of random tries before a truck just comes home */
   public static final int MAX_TRIES = 10;
   
   /** alpha value for the probabilistic selection algorithm */
   public static final int ALPHA = 1;
   
   /** beta value for the probabilistic selection algorithm */
   public static final int BETA = 2;
   
   /** number of trucks to use as ants as a %age of the number of depots */
   public static final double NUMBER_OF_TRUCKS_FACTOR = 0.5;
   
   /** number of times to repeat algorithm */
   public static final int TOTAL_ITERATIONS = 1000;
   
   /** %age of pheromones to preserve after each iteration */
   public static final double EVAPORATION_RATE = 0.4;
   
   /** max amount the truck can carry at any one time */
   public static final int MAX_LOAD = 15;

   /** distances between each depot (symmetrical) */
   public static final int[][] distanceMatrix = {
      {0, 548, 776, 696, 582, 274, 502, 194, 308, 194, 536, 502, 388, 354, 468, 776, 662},
      {548, 0, 684, 308, 194, 502, 730, 354, 696, 742, 1084, 594, 480, 674, 1016, 868, 1210},
      {776, 684, 0, 992, 878, 502, 274, 810, 468, 742, 400, 1278, 1164, 1130, 788, 1552, 754},
      {696, 308, 992, 0, 114, 650, 878, 502, 844, 890, 1232, 514, 628, 822, 1164, 560, 1358},
      {582, 194, 878, 114, 0, 536, 764, 388, 730, 776, 1118, 400, 514, 708, 1050, 674, 1244},
      {274, 502, 502, 650, 536, 0, 228, 308, 194, 240, 582, 776, 662, 628, 514, 1050, 708},
      {502, 730, 274, 878, 764, 228, 0, 536, 194, 468, 354, 1004, 890, 856, 514, 1278, 480},
      {194, 354, 810, 502, 388, 308, 536, 0, 342, 388, 730, 468, 354, 320, 662, 742, 856},
      {308, 696, 468, 844, 730, 194, 194, 342, 0, 274, 388, 810, 696, 662, 320, 1084, 514},
      {194, 742, 742, 890, 776, 240, 468, 388, 274, 0, 342, 536, 422, 388, 274, 810, 468},
      {536, 1084, 400, 1232, 1118, 582, 354, 730, 388, 342, 0, 878, 764, 730, 388, 1152, 354},
      {502, 594, 1278, 514, 400, 776, 1004, 468, 810, 536, 878, 0, 114, 308, 650, 274, 844},
      {388, 480, 1164, 628, 514, 662, 890, 354, 696, 422, 764, 114, 0, 194, 536, 388, 730},
      {354, 674, 1130, 822, 708, 628, 856, 320, 662, 388, 730, 308, 194, 0, 342, 422, 536},
      {468, 1016, 788, 1164, 1050, 514, 514, 662, 320, 274, 388, 650, 536, 342, 0, 764, 194},
      {776, 868, 1552, 560, 674, 1050, 1278, 742, 1084, 810, 1152, 274, 388, 422, 764, 0, 798},
      {662, 1210, 754, 1358, 1244, 708, 480, 856, 514, 468, 354, 844, 730, 536, 194, 798, 0},
    };
    
   /** loads present at each depot */
   public static final int[] loads = {0, 1, 1, 2, 4, 2, 4, 8, 8, 1, 2, 1, 2, 4, 4, 8, 8};
   
   // ADD YOUR CLASS VARIABLES HERE
   
   /**
   default constructor
   */
   public CVRP_ACO()
   {
      Truck t = new Truck();
      System.out.println(t);
      
      t.add_depot(1);
      System.out.println(t);
      
      t.add_depot(4);
      t.add_depot(9);
      System.out.println(t);
      
      t.add_depot(0);
      System.out.println(t);
   }
   
   
    
   public static void main(String[] args)
   {
      CVRP_ACO test = new CVRP_ACO();
      
      //test.solve();
   }
   
   
   /**
   models the truck (ant)
   */
   private class Truck
   {
      /** route traveled thus far */
      public ArrayList<Integer> route;
      
      /** true for each depot if visited, else false */
      public int[] visited_depots;
      
      /** load currently being carried */
      public int load;
      
      /**
      constructor
      */
      public Truck()
      {
         load = 0;
         
         visited_depots = new int[loads.length];
         visited_depots[0] = VISITED;
      
         route = new ArrayList<Integer>();
         //int starting_depot = (int) (Math.random() * demands.length);
         int starting_depot = 0;
         route.add(starting_depot);
      }
      
      /**
      return string implementation
      @return class variables in a string
      */
      public String toString()
      {
         String out = "Truck (load = " + load + ", distance = " + get_distance_traveled() + "): "
            + route;
            
         return out;
      }
      
      /**
      checks if all depots are visited
      @return true if each element in visited_depots == VISITED, else false
      */
      public boolean done_route()
      {
         for(int depot : visited_depots)
         {
            if(depot == NOT_VISITED)
            {
               return false;
            }
         }
         
         return true;
      }
      
      /**
      calculates length of this route
      @return sum of distances between each pair of elements in route
      */
      public int get_distance_traveled()
      {
         // ADD YOUR CODE HERE
         
         // dummy value
         return -1;
      }
      
      /**
      tries to visit a specific depot
      @param num the number of the depot to visit
      @return true if it is possible to visit this depot, else false
      */
      public boolean add_depot(int num)
      {
         int newLoad = loads[num];
         
         if(num == route.get(route.size() - 1))
         {
            return false;
         }
         else if(num == 0)
         {
            route.add(0);
            visited_depots[0] = 1;
            load = 0;
            return true;
         }
         else
         {
            if(load + newLoad > MAX_LOAD)
            {
               return false;
            }
            else if(route.contains(num))
            {
               return false;
            }
            else
            {
               load += newLoad;
               visited_depots[num] = VISITED;
               route.add(num);
               return true;
            }
         }
      }
      
      /**
      tells truck to visit a depot (either randomly or probabilistically
      */
      public void visit_depot()
      {
         if(!this.done_route())
         {
            if(Math.random() < RANDOM_DEPOT_FACTOR)
            {
               visit_random_depot();
            }
            else
            {
               visit_probabilistic_depot();
            }
         }
      
      
      }
      
      /**
      tells truck to visit a depot randomly
      */
      public void visit_random_depot()
      {
         boolean done = false;
         int numTries = 0;
         
         while(!done && numTries < MAX_TRIES)
         {
            numTries++;
         
            int depot = (int) (Math.random() * loads.length);
            done = this.add_depot(depot);
         }
         
         if(!done)
         {
            this.add_depot(0);
         }
      
      }
      
      /**
      tells truck to visit a depot probabilistically
      */
      public void visit_probabilistic_depot()
      {
         int depot = this.roulette_wheel_selection();
         this.add_depot(depot);
      }
      
      /**
      selects a depot probabilistically using roulette wheel
      @return number of depot to visit
      */
      public int roulette_wheel_selection()
      {
         // YOUR CODE HERE
         
         // dummy value
         return -1;
      }
      
      /**
      generates list of probabilities for roulette wheel
      @return list of <depot, prob> pairs (scaled to 1.0)
      */
      public ArrayList<DepotProb> get_depot_probs()
      {
         // YOUR CODE HERE
         
         // dummy value
         return null;
      }
   }
   
   /**
   helper class for roulette wheel
   */
   private class DepotProb
   {
      /** index of the depot */
      public int idx;
      
      /** probability to visit the depot */
      public double prob;
      
      /**
      constructor
      @param i index value
      @param p probability
      */
      public DepotProb(int i, double p)
      {
         idx = i;
         prob = p;
      }
      
      /**
      converts object to a string
      @return string containing class variables
      */
      public String toString()
      {
         return "(" + idx + "," + prob + ")";
      }
   }

}