package com.psc.psc_management.Models;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "branches")
public class Branches {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "branch_name")
  private String branchName;
  @Column(name = "address")
  private String address;
  @Column(name = "email")
  private String email;

  @Column(name = "capacity")
  private Float capacity;

  @Column(name = "used_capacity")
  private Float usedCapacity;

  @Column(name = "crop_limit")
  private Float cropLimit;

  @Column(name = "budget")
  private Float budget;

  @Column(name = "used_budget")
  private Float usedBudget;

  @Column(name = "generate_income")
  private Float income;

  @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Employees> employees;

  @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Farmers> farmers;

  @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<IssuePaddy> issuePaddy;

  @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<BuyPaddy> buyPaddies;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getBranchName() {
    return this.branchName;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Float getCapacity() {
    return this.capacity;
  }

  public void setCapacity(Float capacity) {
    this.capacity = capacity;
  }

  public Float getUsedCapacity() {
    return usedCapacity;
  }

  public void setUsedCapacity(Float usedCapacity) {
    this.usedCapacity = usedCapacity;
  }

  public Float getCropLimit() {
    return this.cropLimit;
  }

  public void setCropLimit(Float cropLimit) {
    this.cropLimit = cropLimit;
  }

  public Float getBudget() {
    return this.budget;
  }

  public void setBudget(Float budget) {
    this.budget = budget;
  }

  public Float getUsedBudget() {
    return usedBudget;
  }
  public void setUsedBudget(Float usedBudget) {
    this.usedBudget = usedBudget;
  }

  public Float getIncome() {
    return income;
  }
  public void setIncome(Float income) {
    this.income = income;
  }

  public Set<Employees> getEmployees() {
    return this.employees;
  }

  public void setEmployees(Set<Employees> employees) {
    this.employees = employees;
  }

  public Set<Farmers> getFarmers() {
    return this.farmers;
  }

  public void setFarmers(Set<Farmers> farmers) {
    this.farmers = farmers;
  }

  public Set<IssuePaddy> getIssuePaddy() {
    return issuePaddy;
  }

  public void setIssuePaddy(Set<IssuePaddy> issuePaddy) {
    this.issuePaddy = issuePaddy;
  }

  public Set<BuyPaddy> getBuyPaddies() {
    return buyPaddies;
  }

  public void setBuyPaddies(Set<BuyPaddy> buyPaddies) {
    this.buyPaddies = buyPaddies;
  }
}
