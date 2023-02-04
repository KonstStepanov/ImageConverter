package image;

/**
 *класс кастомного исключения в случае ошибки соотношения сторон
 */
public class BadImageSizeException extends Exception {
    public BadImageSizeException(double ratio, double maxRatio) {
        super("Максимальное соотношение сторон изображения " + maxRatio + ", а у этой " + ratio);
    }
}
