package com.securedb.pharmacy.utils;

public interface GlobalConstants {
    String ICON_RESOURCE = "pharmancy.ico";
    String IMAGE_RESOURCE = "/resources/pharmacy.png";
    String MAIN_FRAME_TITTLE = "Аптечный Навигатор";
    String INFO_SAVED = "Сохранено!";

    String ERR_WRONG_INDEX = "Неправильный почтовый индекс!";
    String ERR_RECORD_NOT_FIND = "Почтовое отделение не найдено!";
    String ERR_DB_NOT_CONNECT = "Нет доступа к базе данных. \nПроверьте подключение и попробуйте снова.";
    String ERR_CLIENT_IN_USE = "Невозможно удалить Клиента так как у его есть Заказ!";
    String ERR_DRUG_IN_USE = "Невозможно удалить Лекарство так как используется в Заказе!";
    String ERR_ORDER_NOT_SELECT = "Пожалуйста, выберете Заказ!";
    String ERR_DRUG_NOT_SELECT = "Пожалуйста, выберете Лекарство!";
    String ERR_CANNOT_PARSE_DRUG = "Невозможно распознать Лекартсво!";
    String ERR_SQL_OR_CNF = "Класс драйвера не найден!";

    String DB_CONNECT_URL = "jdbc:h2:tcp://localhost/~/db/PostOffices";
    String DB_JDBC_DRIVER = "org.h2.Driver";
    String DB_USERNAME = "sa";
    String DB_PASSWORD = "";
}
