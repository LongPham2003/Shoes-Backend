package com.example.ProjectShoes.entity;

import com.example.ProjectShoes.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class Address extends PrimaryEntity implements Serializable {

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "specific_address")
    private String specificAddress;

    @Column(name = "default_address")
    private boolean defaultAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
