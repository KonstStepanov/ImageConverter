package image;

/**
 *класс задания цветовой схемы в виде символов
 */
public class ColorSchema implements TextColorSchema {
    @Override
    public char convert(int color) {
        char c = ' ';
        switch (color / 30) {
            case 0 -> c = '#'; // 0-29
            case 1 -> c = '$'; // 30-59
            case 2 -> c = '@'; // 60-89
            case 3 -> c = '*'; // 90-119
            case 4 -> c = '%'; // 120-149
            case 5 -> c = '-'; // 150-179
            case 6 -> c = '+'; // 180-209
            case 7, 8 -> c = '\''; // 210-239, 240-255
            default -> {
            }
        }

        return c;
    }
}