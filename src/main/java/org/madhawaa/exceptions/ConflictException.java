package org.madhawaa.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ConflictException extends WebApplicationException {
    public ConflictException(String message) {
        super(Response.status(Response.Status.CONFLICT)
                .entity(new ErrorResponse(409, message))
                .build());
    }
}
