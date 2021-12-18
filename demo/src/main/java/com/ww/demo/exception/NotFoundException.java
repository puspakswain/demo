package com.ww.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = 277260490767325622L;

  public NotFoundException() {
    super(HttpStatus.NOT_FOUND.getReasonPhrase());
  }

  public NotFoundException(String message) {
    super(message);
  }

}
