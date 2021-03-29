package com.example.universalstudios.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = -1552618210040732073L;
  private final HttpStatus httpStatus;

  public ServiceException(final String errorMessage) {
    super(errorMessage);
    this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
  }

  public ServiceException(final String errorMessage, final HttpStatus httpStatus) {
    super(errorMessage);
    this.httpStatus = httpStatus;
  }

  public ServiceException(final Throwable throwable, final HttpStatus httpStatus) {
    super(throwable);
    this.httpStatus = httpStatus;
  }

  public ServiceException(final String errorMessage, final Throwable throwable,
      final HttpStatus httpStatus) {
    super(errorMessage, throwable);
    this.httpStatus = httpStatus;
  }
}
