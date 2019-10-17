public class ParseDateFormatString
{
    public static void main(String [] args)
    {
        String dateTest = parseStringToFormatString("2019-10-17");

        System.out.println();
    }

    public String parseStringToFormatString(String dateExisting)
    {
        String realDate = "";

        try
        {
            if (dateExisting.equalsIgnoreCase("-"))
            {
                realDate = null;
            }
            else
            {
                //parseString to Date
                SimpleDateFormat existingFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = existingFormat.parse(dateExisting);

                //parseDate to String with New Format
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                realDate = formatter.format(currentDate);
            }
        }
        catch (ParseException e)
        {
            logger.error("Error when parsing Date format: ", e);
        }

        return realDate;
    }
}