package com.hendra.rest.swagger.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@ApiModel(description="All details about the Employee. ")
public class Employee
{
    @ApiModelProperty(notes = "The database generated employee ID")
    private long id;

    @ApiModelProperty(notes = "The employee first name")
    private String firstName;

    @ApiModelProperty(notes = "The employee last name")
    private String lastName;

    @ApiModelProperty(notes = "The employee email id")
    private String emailId;

    public Employee()
    {
        //Empty Constructor
    }

    public Employee(long id, String firstName, String lastName, String emailId)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Column(name = "email_address", nullable = false)
    public String getEmailId()
    {
        return emailId;
    }

    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }

    @Override
    public String toString()
    {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
