import java.util.Scanner;

public class TwoTrains
{
  public static void main(String args[])
  {
    float b_leave_time;
    float speed_a;
    float speed_b; 
    float distance;
    float a_time;
    float b_time;
    float meet_time;
    float meet_loc;
        
    Scanner input = new Scanner(System.in);
    
    System.out.print("Time(in hours) for Train B after Train A leaves: ");
    b_leave_time = input.nextFloat();
    System.out.print("Speed of Train A(in mph): ");
    speed_a = input.nextFloat();
    System.out.print("Speed of Train B(in mph): ");
    speed_b = input.nextFloat();
    System.out.print("Distance of destination(in miles): ");
    distance = input.nextFloat();
    
    a_time = distance/speed_a;
    b_time = distance/speed_b + b_leave_time;
    meet_time = (b_leave_time * speed_b)/(speed_b - speed_a);
    meet_loc = speed_a*meet_time;
    
    System.out.println("Train A will arrive "+ a_time +" hours after Train A leaves.");
    System.out.println("Train B will arrive "+ b_time +" hours after Train A leaves.");
    System.out.println("The two trains will meet after "+ meet_time + " hours and "+ meet_loc + " miles.");
  }
}
    