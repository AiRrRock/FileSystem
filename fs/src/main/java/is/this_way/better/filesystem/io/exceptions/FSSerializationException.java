package is.this_way.better.filesystem.io.exceptions;

public class FSSerializationException extends Exception{
   private static final String message="Your file system cannot be serialized due to:  ";
   private String additionalMessage;
        public  FSSerializationException(String additionalMessage){
            super(message+additionalMessage);
            this.additionalMessage=additionalMessage;
   }
}
