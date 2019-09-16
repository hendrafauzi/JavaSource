import java.util.*;

public class ReverseLinkedList
{
  public static void main(String[] args)
  {
    LinkedList<String> linkedList = new LinkedList<String>();
    linkedList.add("Apple");
    linkedList.add("Samsung");
    linkedList.add("Nokia");
    linkedList.add("Huawei");
    linkedList.add("Xiaomi");
    linkedList.add("Realme");

    System.out.println("List Before Reversing: " + linkedList);

    linkedList = reverseLinkedList(linkedList);
    System.out.println("List After Reversing: " + linkedList);
  }

  public static LinkedList<String> reverseLinkedList(LinkedList<String> lists)
  {
    LinkedList<String> revLinkedList = new LinkedList<String>();

    for (int i = lists.size() - 1; i >= 0; i--)
    {
      revLinkedList.add(lists.get(i));
    }

    return revLinkedList;
  }
}
