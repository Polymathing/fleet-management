package com.example.fleet_management.web.dto.error;

public record ErrorResponseBody(String message) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorResponseBody that = (ErrorResponseBody) o;

        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }

    @Override
    public String toString() {
        return "ErrorResponseBody{" +
                "message='" + message + '\'' +
                '}';
    }
}
