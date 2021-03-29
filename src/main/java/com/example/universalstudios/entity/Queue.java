package com.example.universalstudios.entity;

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
@Table(name = "queue")
public class Queue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "ride_id")
  private Long rideId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "status")
  private String status;

  @Column(name = "entry_ts")
  private Long entryTs;

  @PrePersist
  public void prePersist() {
    if (Objects.isNull(this.entryTs)) {
      this.entryTs = System.currentTimeMillis() / 1000;
    }
  }
}
