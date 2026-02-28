package com.employee.management.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessageKey {

    VALIDATION_FAILED("validation.failed", "Invalid request"),
    INVALID_REQUEST_BODY("invalid.request.body", "Invalid request body"),
    INVALID_PARAMETER_VALUE("invalid.parameter.value", "Invalid parameter value"),
    RESOURCE_NOT_FOUND("resource.not.found", "Resource not found"),
    RESOURCE_ALREADY_EXISTS("resource.already.exists", "Resource already exists"),
    INVALID_LEAVE_STATE("invalid.leave.state", "Invalid leave state"),
    INTERNAL_SERVER_ERROR("internal.server.error", "An unexpected error occurred");

    private final String key;
    private final String message;
}
