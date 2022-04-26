package pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing;

public class ImageServiceException extends Exception {
    public ImageServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    public ImageServiceException() {
        super();
    }
}
