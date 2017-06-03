package kr.or.connect.todo.dto;


public class ErrorDTO {
    public class Error {
        private final String message;
        public Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    private Error error;

    public ErrorDTO(String message) {
        this.error = new Error(message);
    }

    public ErrorDTO() {
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
