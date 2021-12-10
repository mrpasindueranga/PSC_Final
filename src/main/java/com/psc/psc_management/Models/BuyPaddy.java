package com.psc.psc_management.Models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "buy_paddy")
public class BuyPaddy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Farmers farmer;

    @Column(name = "date")
    private Date date;

    @Column(name = "quantity")
    private float quantity;

    @Column(name = "purchase_price")
    private float cost;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Branches branch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Farmers getFarmer() {
        return farmer;
    }

    public void setFarmer(Farmers farmer) {
        this.farmer = farmer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Branches getBranch() {
        return branch;
    }

    public void setBranch(Branches branch) {
        this.branch = branch;
    }

    public boolean isStatus() {
        return this.status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
