package realcool.ocr.exception;

public class OCRException extends RuntimeException{
    public OCRException() {
        super();
    }

    public OCRException(String message) {
        super(message);
    }

    public OCRException(String message, Throwable cause) {
        super(message, cause);
    }

    public OCRException(Throwable cause) {
        super(cause);
    }
}
