package com.vdshb.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "management_company")
public class ManagementCompany extends BasicEntity<ManagementCompany> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "management_company_id_seq")
    @SequenceGenerator(name = "management_company_id_seq", sequenceName = "management_company_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name="management_company_in_town",
            joinColumns = @JoinColumn(name = "management_company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "town_id", referencedColumnName = "id")
    )
    private List<Town> towns = new ArrayList<>(0);

    @Override
    public void setBeanPropertiesFromRestUpdate(ManagementCompany request) {
        setName(request.getName());
        setTowns(request.getTowns());
    }

    //===========================================================
    //             Generated getters and setters
    //===========================================================


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Town> getTowns() {
        return towns;
    }

    public void setTowns(List<Town> towns) {
        this.towns = towns;
    }
}
