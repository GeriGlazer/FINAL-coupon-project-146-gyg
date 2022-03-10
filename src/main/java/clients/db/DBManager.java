package clients.db;

/**
 * @author Yoav Hachmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * SQL declaration statements
 */
public class DBManager {

    public static final String URL = "jdbc:mysql://localhost:3306";
    public static final String SQL_USER = "root";
    public static final String SQL_PASS = "12345678";

    public static final String CREATED_DB = "CREATE SCHEMA `coupons_project` ;";
    public static final String DROP_DB = "DROP SCHEMA `coupons_project`";

    public static final String DROP_CUSTOMER_VS_COUPONS_TABLE = "DROP TABLE `coupons_project`.`customers_vs_coupons`;";
    public static final String DROP_COMPANY_TABLE = "DROP TABLE `coupons_project`.`companies`;";
    public static final String DROP_CATEGORIES_TABLE = "DROP TABLE `coupons_project`.`categories`;";
    public static final String DROP_CUSTOMERS_TABLE = "DROP TABLE `coupons_project`.`customers`;";
    public static final String DROP_COUPONS_TABLE = "DROP TABLE `coupons_project`.`coupons`;";

    public static final String CREATE_COMPANY_TABLE =
            "CREATE TABLE IF NOT EXISTS `coupons_project`.`companies` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(45) NOT NULL," +
                    "`email` VARCHAR(45) NOT NULL," +
                    "`password` VARCHAR(45) NOT NULL," +
                    "PRIMARY KEY (`id`));";

    public static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE IF NOT EXISTS `coupons_project`.`customers` (" +
                    " `id` INT NOT NULL AUTO_INCREMENT," +
                    "`first_name` VARCHAR(45) NOT NULL," +
                    "`last_name` VARCHAR(45) NOT NULL," +
                    "`email` VARCHAR(45) NOT NULL," +
                    "`password` VARCHAR(45) NOT NULL," +
                    "PRIMARY KEY (`id`));";

    public static final String CREATE_CATEGORIES_TABLE =
            "CREATE TABLE IF NOT EXISTS `coupons_project`.`categories` ( " +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(45) NOT NULL," +
                    "PRIMARY KEY (`id`));";

    public static final String CREATE_COUPONS_TABLE =
            "CREATE TABLE IF NOT EXISTS `coupons_project`.`coupons` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`company_id` INT NOT NULL," +
                    "`category_id` INT NOT NULL," +
                    "`title` VARCHAR(45) NOT NULL," +
                    "`description` VARCHAR(45) NOT NULL," +
                    "`start_date` DATE NOT NULL," +
                    "`end_date` DATE NOT NULL," +
                    "`amount` INT NOT NULL," +
                    "`price` DOUBLE NOT NULL," +
                    "`image` VARCHAR(45) NOT NULL," +
                    "PRIMARY KEY (`id`)," +
                    "INDEX `company_id_idx` (`company_id` ASC) VISIBLE," +
                    "INDEX `category_id_idx` (`category_id` ASC) VISIBLE," +
                    "CONSTRAINT `company_id`" +
                    " FOREIGN KEY (`company_id`)" +
                    " REFERENCES `coupons_project`.`companies` (`id`)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE CASCADE," +
                    "CONSTRAINT `category_id`" +
                    " FOREIGN KEY (`category_id`)" +
                    " REFERENCES `coupons_project`.`categories` (`id`)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE CASCADE);";

    public static final String CREATE_CUSTOMER_VS_COUPONS_TABLE =
            "CREATE TABLE IF NOT EXISTS `coupons_project`.`customers_vs_coupons` (" +
                    "`customer_id` INT NOT NULL," +
                    "`coupon_id` INT NOT NULL," +
                    "PRIMARY KEY (`customer_id`, `coupon_id`)," +
                    "INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE," +
                    "CONSTRAINT `customer_id`" +
                    "FOREIGN KEY (`customer_id`)" +
                    " REFERENCES `coupons_project`.`customers` (`id`)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE CASCADE," +
                    "CONSTRAINT `coupon_id`" +
                    " FOREIGN KEY (`coupon_id`)" +
                    " REFERENCES `coupons_project`.`coupons` (`id`)" +
                    " ON DELETE CASCADE" +
                    " ON UPDATE CASCADE);";

    public static final String ADD_CATEGORY =
            "INSERT INTO `coupons_project`.`categories` (`name`) VALUES (?);";

    public static final String DELETE_CATEGORY =
            "DELETE FROM `coupons_project`.`categories` WHERE id=?;";

    public static final String GET_ONE_CATEGORY =
            "SELECT `id` FROM `coupons_project`.`categories` WHERE name=?;";

    public static final String CREATE_NEW_COUPON =
            "INSERT INTO `coupons_project`.`coupons` " +
                    "(`company_id`, `category_id`, `title`, `description`, " +
                    "`start_date`, `end_date`, `amount`, `price`, `image`)" +
                    "VALUES (?,?,?,?,?,?,?,?,?);";

    public static final String UPDATE_COUPON =
            "UPDATE `coupons_project`.`coupons` " +
                    "SET company_id=?, category_id=?, title=?, description=?, " +
                    "start_date=?, end_date=?, amount=?, price=?, image=?" +
                    "WHERE id=?;";

    public static final String DELETE_COUPON =
            "DELETE FROM `coupons_project`.`coupons` WHERE id=?;";

    public static final String GET_ALL_COUPONS =
            "SELECT * FROM `coupons_project`.`coupons`;";

    public static final String GET_ONE_COUPON =
            "SELECT * FROM `coupons_project`.`coupons` WHERE id=?;";

    public static final String COUNT_COUPON_BY_ID =
            "SELECT COUNT(*) FROM `coupons_project`.`coupons` WHERE `id`=?;";

    public static final String ADD_PURCHASED_COUPON =
            "INSERT INTO `coupons_project`.`customers_vs_coupons` " +
                    "(`customer_id`, `coupon_id`) VALUES (?,?);";

    public static final String DELETE_PURCHASED_COUPON =
            "DELETE FROM `coupons_project`.`customers_vs_coupons` " +
                    "WHERE `customer_id`=? AND `coupon_id`=?;";

    public static final String DELETE_PURCHASED_COUPON_BY_COUPON_ID =
            "DELETE FROM `coupons_project`.`customers_vs_coupons` WHERE `coupon_id`=?;";

    public static final String GET_COUPONS_BY_COMPANIES =
            "SELECT * FROM `coupons_project`.`coupons` WHERE company_id=?;";

    public static final String ADD_COMPANY =
            "INSERT INTO `coupons_project`.`companies` ( `name`, `email`, `password`) VALUES (?,?,?);";

    public static final String UPDATE_COMPANY =
            "UPDATE `coupons_project`.`companies` SET `name`= ?, `email` = ?, `password` = ? WHERE (`id` = ?);";

    public static final String DELETE_COMPANY =
            "DELETE FROM `coupons_project`.`companies` WHERE (`id` = ?);";

    public static final String GET_ALL_COMPANIES =
            "SELECT * FROM `coupons_project`.`companies`;";

    public static final String GET_SINGLE_COMPANY =
            "SELECT* FROM `coupons_project`.`companies` WHERE (`id` = ?);";

    public static final String COUNT_COMPANY_BY_ID =
            "SELECT COUNT(*) FROM `coupons_project`.`companies` WHERE `id` = ?;";

    public static final String COUNT_COMPANY_BY_NAME =
            "SELECT COUNT(*) FROM `coupons_project`.`companies` WHERE `name` = ?;";

    public static final String COUNT_COMPANY_BY_EMAIL =
            "SELECT COUNT(*) FROM `coupons_project`.`companies` WHERE `email` = ?;";

    public static final String COUNT_COMPANY_BY_PASS_AND_EMAIL =
            "SELECT COUNT(*) FROM `coupons_project`.`companies` WHERE `email` = ? and `password` = ?;";

    public static final String ADD_CUSTOMER =
            "INSERT INTO `coupons_project`.`customers` ( `first_name`, `last_name`, `email`, `password`) " +
                    "VALUES (?,?,?,?);";

    public static final String UPDATE_CUSTOMER =
            "UPDATE `coupons_project`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? " +
                    "WHERE (`id` = ?);";

    public static final String DELETE_CUSTOMER =
            "DELETE FROM `coupons_project`.`customers` WHERE (`id` = ?);";

    public static final String GET_ONE_CUSTOMER =
            "SELECT * FROM `coupons_project`.`customers` WHERE (`id` = ?);";

    public static final String GET_COUPONS_BY_CUSTOMER =
            "SELECT * FROM `coupons_project`.`customers_vs_coupons` WHERE `customer_id`=?;";

    public static final String GET_ALL_CUSTOMERS =
            "SELECT * FROM `coupons_project`.`customers`;";

    public static final String IS_CUSTOMER_EXISTS_BY_EMAIL_AND_PASS =
            "SELECT COUNT(*) FROM `coupons_project`.`customers` WHERE `email` = ? and `password` = ?;";

    public static final String IS_CUSTOMER_EXISTS_BY_EMAIL =
            "SELECT COUNT(*) FROM `coupons_project`.`customers` WHERE `email` = ?;";

    public static final String GET_CATEGORY_NAME =
            "SELECT `name` FROM `coupons_project`.`categories` WHERE id=?;";


    public static final String IS_COUPON_PURCHASED =
            "SELECT COUNT(*) FROM `coupons_project`.`customers_vs_coupons` WHERE `customer_id`=? AND `coupon_id` =?;";

    public static final String GET_COMPANY_ID_BY_EMAIL_AND_PASSWORD =
            "SELECT `id` FROM `coupons_project`.`companies` WHERE `email` = ? AND `password` = ?;";

    public static final String GET_CUSTOMER_ID_BY_EMAIL_AND_PASSWORD =
            "SELECT `id` FROM `coupons_project`.`customers` WHERE `email` = ? AND `password` = ?;";

}

