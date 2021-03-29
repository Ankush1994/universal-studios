package com.example.universalstudios.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ride")
public class Ride implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "capacity")
  private Integer capacity;

  @Column(name = "round_duration_in_sec")
  private Integer roundDurationInSec;

  @Column(name = "active")
  private Boolean active;
}
