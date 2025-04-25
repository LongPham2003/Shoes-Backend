package com.example.ProjectShoes.entity;

import com.example.ProjectShoes.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role extends PrimaryEntity implements Serializable {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
}
