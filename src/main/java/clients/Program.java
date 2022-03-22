package clients;

import clients.exceptions.CustomExceptions;

import static clients.System.*;
/**
 * @author Yoav Hacmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 *Program class runs System class
 * Main class
 */

public class Program {

    public static void main(String[] args) {
        //first time run, create schema and tables necessary to run the program
        //createDataBases();
        //Optional: to re-check the program

        cleanDataBase();

        try {
            testAll();
        } catch (CustomExceptions customExceptions) {
            java.lang.System.out.println(customExceptions.getMessage());
        }
    }
}
