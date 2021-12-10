package com.psc.psc_management.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "farmers")
public class Farmers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "farmers_name")
    private String farmer;
    @Column(name = "district")
    private String district;
    @Column(name = "address")
    private String address;
    @Column(name = "nic")
    private String nic;
    @Column(name = "email")
    private String email;
    @Column(name = "contact")
    private float contact;
    @Column(name = "accno")
    private String accno;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Branches branch;

    @OneToMany(mappedBy = "farmer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BuyPaddy> buyPaddies;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFarmer() {
        return this.farmer;
    }

    public void setFarmer(String farmer) {
        this.farmer = farmer;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return this.nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getContact() {
        return this.contact;
    }

    public void setContact(float contact) {
        this.contact = contact;
    }

    public String getAccno() {
        return this.accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public Branches getBranch() {
        return this.branch;
    }

    public void setBranch(Branches branch) {
        this.branch = branch;
    }

    public Set<BuyPaddy> getBuyPaddies() {
        return buyPaddies;
    }

    public void setBuyPaddies(Set<BuyPaddy> buyPaddies) {
        this.buyPaddies = buyPaddies;
    }
}
