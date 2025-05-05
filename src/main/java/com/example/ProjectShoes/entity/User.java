package com.example.ProjectShoes.entity;

import com.example.ProjectShoes.common.Gender;
import com.example.ProjectShoes.common.UserStatus;
import com.example.ProjectShoes.entity.base.PrimaryEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User extends PrimaryEntity implements Serializable {
    @Column(name ="user_name",unique = true)
    private String userName;

    @Column(name ="password" )
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING )
    private Gender gender;

    @Column(name = "avatar")
    private String avatar;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "createAt", "updateAt", "createBy", "updateBy"})
    @JsonIgnore
    private List<Address> address;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnoreProperties(value = { "createAt", "updateAt", "createBy", "updateBy"})
    private Role role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING )
    private UserStatus status;
}
