package com.example.universalstudios.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class WaitTimeDto {

  private Long rideId;
  private Long queueId;
  private String rideName;
  private Integer positionInQueue;
  private Integer waitTimeInSec;
}
