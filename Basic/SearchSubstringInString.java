public class SearchSubstringInString
{
  public static void main(String [] args)
  {
    String str = "AaaabbXX yyyyXX zzzzXzzX";
    String findStr = "XX";
    int count = 0, startIndex = 0;

    while ((startIndex = str.indexOf(findStr, startIndex)) != -1)
    {
      System.out.println("Found at index: " + startIndex);
      count++;
      startIndex++;
    }

    System.out.println("Total Occurences: " + count);
  }
}
