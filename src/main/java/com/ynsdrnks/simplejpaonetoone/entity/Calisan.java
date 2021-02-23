package com.ynsdrnks.simplejpaonetoone.entity;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.IndexColumn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name="calisan")
@Builder
public class Calisan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="clsn_id")
    private Long clsnId;

    @Column(name="clsn_fname")
    private String clsnFirstName;

    @Column(name = "clsn_lname")
    private String clsnLastName;

    @Column(name="clsn_email")
    private String clsnEmail;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "moreInfo_id",foreignKey = @ForeignKey(name = "none"))
    private MoreInfo moreInfo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "clsn_id",referencedColumnName = "clsn_id")
    private List<Adress> adresses;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "clsn_id",referencedColumnName = "clsn_id")
    private List<AddressDropDown> addressDropDowns;

}