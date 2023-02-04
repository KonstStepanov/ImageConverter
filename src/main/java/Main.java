import image.ImageToChar;
import image.TextGraphicsConverter;
import server.GServer;

/**
 * класс инициализации сервера и класса конвертера
 */
public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new ImageToChar();

        GServer server = new GServer(converter);
        server.start();
    }
}
