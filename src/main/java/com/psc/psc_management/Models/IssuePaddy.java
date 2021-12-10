package com.psc.psc_management.Models;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "issue_paddy")
public class IssuePaddy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address")
    private String address;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "quantity")
    private float quantity;

    @Column(name = "date")
    private Date date;

    private float revenue;

    @Column(name = "status")
    private String status;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Branches branch;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Vehicles vehicle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Branches getBranch() {
        return branch;
    }

    public void setBranch(Branches branch) {
        this.branch = branch;
    }

    public Vehicles getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicles vehicle) {
        this.vehicle = vehicle;
    }
}
