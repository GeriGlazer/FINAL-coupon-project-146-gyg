package clients.exceptions;
/**
 * @author Yoav Hachmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */
public class CustomExceptions extends Exception{

    public CustomExceptions(EnumExceptions enumExceptions){
        super(enumExceptions.getMessage());
    }

}
