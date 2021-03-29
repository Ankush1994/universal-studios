package com.example.universalstudios.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "app_user")
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "access_token")
  private String accessToken;

  @Column(name = "active")
  private Boolean active;

  @Column(name = "role")
  private String role;

  @PrePersist
  public void prePersist() {
    if ( Objects.isNull(this.active)) {
      this.active = Boolean.TRUE;
    }
  }
}
