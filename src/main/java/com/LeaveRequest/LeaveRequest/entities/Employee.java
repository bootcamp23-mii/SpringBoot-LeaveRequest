/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Panji Sadewo
 */
@Entity
@Table(name = "tb_m_employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
    , @NamedQuery(name = "Employee.findById", query = "SELECT e FROM Employee e WHERE e.id = :id")
    , @NamedQuery(name = "Employee.findByName", query = "SELECT e FROM Employee e WHERE e.name = :name")
    , @NamedQuery(name = "Employee.findByGendertype", query = "SELECT e FROM Employee e WHERE e.gendertype = :gendertype")
    , @NamedQuery(name = "Employee.findByQuota", query = "SELECT e FROM Employee e WHERE e.quota = :quota")
    , @NamedQuery(name = "Employee.findByEmail", query = "SELECT e FROM Employee e WHERE e.email = :email")
    , @NamedQuery(name = "Employee.findByPassword", query = "SELECT e FROM Employee e WHERE e.password = :password")
    , @NamedQuery(name = "Employee.findByJoindate", query = "SELECT e FROM Employee e WHERE e.joindate = :joindate")
    , @NamedQuery(name = "Employee.findByIsactive", query = "SELECT e FROM Employee e WHERE e.isactive = :isactive")
    , @NamedQuery(name = "Employee.findByIsdeleted", query = "SELECT e FROM Employee e WHERE e.isdeleted = :isdeleted")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "ID")
    private String id;
    @Size(max = 120)
    @Column(name = "NAME")
    private String name;
    @Column(name = "GENDERTYPE")
    private Boolean gendertype;
    @Column(name = "QUOTA")
    private BigInteger quota;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 100)
    @Column(name = "PASSWORD")
    private String password;
    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "JOINDATE")
    @Temporal(TemporalType.DATE)
    private Date joindate;
    @Column(name = "ISACTIVE")
    private Boolean isactive;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDELETED")
    private boolean isdeleted;
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Request> requestList;
    @JoinColumn(name = "MARRIEDSTATUS", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MarriedStatus marriedstatus;
    @OneToMany(mappedBy = "idmanager", fetch = FetchType.LAZY)
    private List<Employee> employeeList;
    @JoinColumn(name = "IDMANAGER", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee idmanager;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.LAZY)
    private List<EmployeeRole> employeeRoleList;

    public Employee() {
    }

    public Employee(String id) {
        this.id = id;
    }

    public Employee(String id, Date joindate, boolean isdeleted) {
        this.id = id;
        this.joindate = joindate;
        this.isdeleted = isdeleted;
    }

    public Employee(String id, String name, Boolean gendertype, BigInteger quota, String email, String password, byte[] photo, Date joindate, Boolean isactive, boolean isdeleted, MarriedStatus marriedstatus, Employee idmanager) {
        this.id = id;
        this.name = name;
        this.gendertype = gendertype;
        this.quota = quota;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.joindate = joindate;
        this.isactive = isactive;
        this.isdeleted = isdeleted;
        this.marriedstatus = marriedstatus;
        this.idmanager = idmanager;
    }

    public Employee(String id, String name, Boolean gendertype, BigInteger quota, String email, String password, Date joindate, MarriedStatus marriedstatus, Employee idmanager) {
        this.id = id;
        this.name = name;
        this.gendertype = gendertype;
        this.quota = quota;
        this.email = email;
        this.password = password;
        this.joindate = joindate;
        this.marriedstatus = marriedstatus;
        this.idmanager = idmanager;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGendertype() {
        return gendertype;
    }

    public void setGendertype(Boolean gendertype) {
        this.gendertype = gendertype;
    }

    public BigInteger getQuota() {
        return quota;
    }

    public void setQuota(BigInteger quota) {
        this.quota = quota;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Date getJoindate() {
        return joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    @XmlTransient
    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }

    public MarriedStatus getMarriedstatus() {
        return marriedstatus;
    }

    public void setMarriedstatus(MarriedStatus marriedstatus) {
        this.marriedstatus = marriedstatus;
    }

    @XmlTransient
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Employee getIdmanager() {
        return idmanager;
    }

    public void setIdmanager(Employee idmanager) {
        this.idmanager = idmanager;
    }

    @XmlTransient
    public List<EmployeeRole> getEmployeeRoleList() {
        return employeeRoleList;
    }

    public void setEmployeeRoleList(List<EmployeeRole> employeeRoleList) {
        this.employeeRoleList = employeeRoleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.LeaveRequest.LeaveRequest.entities.Employee[ id=" + id + " ]";
    }
    
}
