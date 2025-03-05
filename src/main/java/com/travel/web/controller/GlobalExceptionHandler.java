package com.travel.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.travel.web.custom.exception.ResourceNotFoundException;

/**
 * Global Exception Handler using a single error page
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles Resource Not Found Exception (404)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleResourceNotFound(ResourceNotFoundException ex, Model model) {
        return buildErrorPage(model, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Handles Illegal Argument Exception (400)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleIllegalArgument(IllegalArgumentException ex, Model model) {
        return buildErrorPage(model, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Handles Generic Runtime Exceptions (500)
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleRuntimeException(RuntimeException ex, Model model) {
        return buildErrorPage(model, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    /**
     * Handles All Other Exceptions (500)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleGlobalException(Exception ex, Model model) {
        return buildErrorPage(model, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }

    /**
     * Handles Authentication Exception (401)
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView handleAuthenticationException(AuthenticationException ex, Model model) {
        return buildErrorPage(model, HttpStatus.UNAUTHORIZED, "Authentication failed. Please login again.");
    }

    /**
     * Handles Access Denied Exception (403)
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex, Model model) {
        return buildErrorPage(model, HttpStatus.FORBIDDEN, "You do not have permission to access this resource.");
    }

    /**
     * Handles Method Not Allowed Exception (405)
     */
    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ModelAndView handleMethodNotAllowedException(org.springframework.web.HttpRequestMethodNotSupportedException ex, Model model) {
        return buildErrorPage(model, HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed. The requested method is not supported for this resource.");
    }

    /**
     * Handles Not Acceptable Exception (406)
     */
    @ExceptionHandler(org.springframework.web.HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ModelAndView handleNotAcceptableException(org.springframework.web.HttpMediaTypeNotAcceptableException ex, Model model) {
        return buildErrorPage(model, HttpStatus.NOT_ACCEPTABLE, "The requested resource is not available in the acceptable format.");
    }

    /**
     * Handles Bad Gateway Exception (502)
     */
    @ExceptionHandler(org.springframework.web.client.HttpServerErrorException.BadGateway.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ModelAndView handleBadGatewayException(org.springframework.web.client.HttpServerErrorException.BadGateway ex, Model model) {
        return buildErrorPage(model, HttpStatus.BAD_GATEWAY, "Bad Gateway. The server encountered an error while processing your request.");
    }

    /**
     * Handles Service Unavailable Exception (503)
     */
    @ExceptionHandler(org.springframework.web.client.HttpServerErrorException.ServiceUnavailable.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ModelAndView handleServiceUnavailableException(org.springframework.web.client.HttpServerErrorException.ServiceUnavailable ex, Model model) {
        return buildErrorPage(model, HttpStatus.SERVICE_UNAVAILABLE, "Service Unavailable. Please try again later.");
    }

    /**
     * Handles Gateway Timeout Exception (504)
     */
    @ExceptionHandler(org.springframework.web.client.HttpServerErrorException.GatewayTimeout.class)
    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    public ModelAndView handleGatewayTimeoutException(org.springframework.web.client.HttpServerErrorException.GatewayTimeout ex, Model model) {
        return buildErrorPage(model, HttpStatus.GATEWAY_TIMEOUT, "Gateway Timeout. The server did not receive a timely response.");
    }

    /**
     * Builds a single error page with dynamic status and message
     */
    private ModelAndView buildErrorPage(Model model, HttpStatus status, String message) {
        model.addAttribute("status", status.value());
        model.addAttribute("error", status.getReasonPhrase());
        model.addAttribute("message", message);
        return new ModelAndView("error");
    }
}
