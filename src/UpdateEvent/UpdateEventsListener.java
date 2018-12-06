package UpdateEvent;

/**
 * Интерфейс для создания слушателей событий обновления базы данных.
 *
 *      Метод интерфейса принимает сообщение об обновлении:
 *          UPDATE_JOURNAL - обновление в журнале
 *          UPDATE_CARS - обновление в списке машин
 *          UPDATE_CLIENTS - обновление в списке клиентов
 */
public interface UpdateEventsListener {
    byte UPDATE_JOURNAL = 1;
    byte UPDATE_CARS = 2;
    byte UPDATE_CLIENTS = 3;

    void onDataChanged(byte updateMsg);
}
