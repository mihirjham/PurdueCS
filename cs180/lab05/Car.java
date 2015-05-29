class Car
{
  private int price;            //Car's price
  private String make, model;   //Car's make, Car's model
  
  public Car(String carMake, String carModel, int carPrice)
  {
    make = carMake;
    model = carModel;
    price = carPrice;
  }
  
  private int getProfitablePrice()
  {
    return price+1000;
  }
  
  public void displayCarInfo()
  {
    System.out.println(make + "\t" + model + "\tCost: $" +getProfitablePrice() + ".");
  }
  
}

  
  