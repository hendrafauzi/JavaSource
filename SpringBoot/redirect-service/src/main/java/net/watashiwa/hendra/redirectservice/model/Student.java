package net.watashiwa.hendra.redirectservice.model;

public class Student
{
    private String userName;
    private String idNumber;

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getIdNumber()
    {
        return idNumber;
    }

    public void setIdNumber(String idNumber)
    {
        this.idNumber = idNumber;
    }

    @Override
    public String toString()
    {
        return "Student{" +
                "userName='" + userName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                '}';
    }
}
