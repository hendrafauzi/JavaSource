package com.hendra.Demojms.jms;

public class Confirmation
{
    private int ackNumber;

    private String verificationComment;

    public Confirmation()
    {
        //Do Nothing
    }

    public Confirmation(int ackNumber, String verificationComment)
    {
        this.ackNumber = ackNumber;
        this.verificationComment = verificationComment;
    }

    public int getAckNumber()
    {
        return ackNumber;
    }

    public void setAckNumber(int ackNumber)
    {
        this.ackNumber = ackNumber;
    }

    public String getVerificationComment()
    {
        return verificationComment;
    }

    public void setVerificationComment(String verificationComment)
    {
        this.verificationComment = verificationComment;
    }

    @Override
    public String toString()
    {
        return "Confirmation{" +
                "ackNumber='" + ackNumber + '\'' +
                ", verificationComment=" + verificationComment +
                '}';
    }
}