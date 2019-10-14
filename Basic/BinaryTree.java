import java.util.ArrayList;
import java.util.Collections;


public class BinaryTree
{
  public static void main(String [] args)
  {
    //Create ArrayList
    ArrayList<Employee> listEmployee = new ArrayList<Employee>();
    listEmployee.add(new Employee(25, "Hendra"));
    listEmployee.add(new Employee(19, "Fauzi"));
    listEmployee.add(new Employee(12, "Cool"));
    listEmployee.add(new Employee(25, "Ace"));
    listEmployee.add(new Employee(19, "Macbook"));
    listEmployee.add(new Employee(12, "Huawei"));

    System.out.println("Original ArrayList: " + listEmployee);

    //Sort ArrayList having amployee objects.
    Collections.sort(listEmployee);

    System.out.println("Sorted ArrayList" + listEmployee);

    //Binary search for employee having age of 35 and name Alex
    int index = Collections.binarySearch(listEmployee, new Employee(22, "Wina"));

    if (index == 0) {  }
    else
    {
      System.out.println("Employee not found in ArrayList");
    }
  }
}

//Create Employee DTO
class Employee
{
  private int empAge;
  private String empName;

  public Employee()
  {
    //Do Nothing, Empty Constructor
  }

  public Employee(int empAge, String empName)
  {
    this.empAge = empAge;
    this.empName = empName;
  }

  public int getEmpAge(int empAge)
  {
    return empAge;
  }

  public void setEmpAge()
  {
    this.empAge = empAge;
  }

  public String getEmpName(String empName)
  {
    return empName;
  }

  public void setEmpName()
  {
    this.empName = empName;
  }

  /* Create compareTo method to sorting the employee in ascending on age */
  public int compareTo(Employee employee)
  {
    if (this.empAge > Employee.empAge) { return 1; }
    else if (this.empAge < Employee.empAge) { return -1; }
    else { return 0; }
  }

  public String toString()
  {
    return empAge + " " + empName;
  }
}
