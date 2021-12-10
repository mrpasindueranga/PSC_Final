package com.psc.psc_management.Models;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "vehicle_number")
    private String vehicleNumber;
    @Column(name = "capacity")
    private float capacity;
    @Column(name = "type")
    private String type;
    @Column(name = "colour")
    private String colour;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<IssuePaddy> issuePaddy;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicleNumber() {
        return this.vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public float getCapacity() {
        return this.capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColour() {
        return this.colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Set<IssuePaddy> getIssuePaddy() {
        return issuePaddy;
    }

    public void setIssuePaddy(Set<IssuePaddy> issuePaddy) {
        this.issuePaddy = issuePaddy;
    }
}
