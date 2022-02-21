package com.util;

import com.Main;
import com.exception.*;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.people.*;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Asks a user a person's value.
 */

public class ScannerManager {
    private final int MAX_X = 416;
    private final int MIN_HEIGHT = 0;
    private final int MAX_HEIGHT = 300;
    private Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");
    private Pattern patternSymbols = Pattern.compile("[a-zA-Z]+");

    private Scanner userScanner;
    private boolean fileMode;

    public ScannerManager(Scanner scanner) {
        this.userScanner = scanner;
        fileMode = false;
    }

    /**
     * Sets a scanner to scan user input.
     *
     * @param userScanner Scanner to set.
     */
    public void setUserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * @return Scanner, which uses for user input.
     */
    public Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * Sets studyGroup asker mode to 'File Mode'.
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets studyGroup asker mode to 'User Mode'.
     */
    public void setUserMode() {
        fileMode = false;
    }

    /**
     * Asks a user the studyGroup's name.
     *
     * @param inputTitle title of input.
     * @param minLength  min length of string
     * @param maxLength  max length of string
     * @param typeOfName shows what kind of name user need to enter
     * @return name
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askName(String inputTitle, int minLength, int maxLength, String typeOfName) throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                System.out.println(inputTitle);
                System.out.print(Main.INPUT_INFO);
                name = userScanner.nextLine().trim();
                if (fileMode) System.out.println(name);
                if (name.equals("")) throw new NotNullException();
                if (name.length() <= minLength) throw new NotInBoundsException();
                if (name.length() >= maxLength) throw new NotInBoundsException();
                if (!patternSymbols.matcher(name).matches()) throw new WrongNameException();
                break;
            } catch (WrongNameException exception) {
                ConsoleManager.printErr(String.format("%s не может быть числом!", typeOfName));
            } catch (NoSuchElementException exception) {
                ConsoleManager.printErr(String.format("%s не распознано!", typeOfName));
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotNullException exception) {
                ConsoleManager.printErr(String.format("%s не может быть пустым!", typeOfName));
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                ConsoleManager.printErr("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                ConsoleManager.printErr(String.format("Длина строки не входит в диапазон (%d; %d)", minLength, maxLength));
            }
        }
        return name;
    }

    /**
     * Asks a user the person's name
     *
     * @return person's name
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askPersonName() throws IncorrectInputInScriptException {
        return askName("Введите имя персоны:", 0, Integer.MAX_VALUE, "Имя человека");
    }

    /**
     * Asks a user the person's X coordinate.
     *
     * @param withLimit set bounds for X
     * @return person's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askXOfCoordinates(boolean withLimit) throws IncorrectInputInScriptException {
        String strX = "";
        long x;
        while (true) {
            try {
                if (withLimit)
                    System.out.println("Введите координату X < " + MAX_X + ":");
                else
                    System.out.println("Введите координату X:");
                System.out.print(Main.INPUT_INFO);
                strX = userScanner.nextLine().trim();
                if (fileMode) System.out.println(strX);
                x = Long.parseLong(strX);
                if (withLimit && x >= MAX_X) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                ConsoleManager.printErr("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInBoundsException exception) {
                ConsoleManager.printErr("Координата X должна быть в диапазоне (" + 0
                        + ";" + (withLimit ? MAX_X : Long.MAX_VALUE) + ")!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strX).matches())
                    ConsoleManager.printErr("Координата X должна быть в диапазоне (" + Long.MAX_VALUE
                            + ";" + (withLimit ? MAX_X : Long.MAX_VALUE) + ")!");
                else
                    ConsoleManager.printErr("Координата X должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                ConsoleManager.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Asks a user the person's Y coordinate.
     *
     * @return person's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public float askYOfCoordinates() throws IncorrectInputInScriptException {
        String strY = "";
        float y;
        while (true) {
            try {
                System.out.println("Введите координату Y:");
                System.out.print(Main.INPUT_INFO);
                strY = userScanner.nextLine().trim();
                if (fileMode) System.out.println(strY);
                y = Float.parseFloat(strY);
                break;
            } catch (NoSuchElementException exception) {
                ConsoleManager.printErr("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strY).matches()) //why
                    ConsoleManager.printErr("Координата Y должна быть в диапазоне (" + Float.MIN_VALUE
                            + ";" + Float.MAX_VALUE + ")!");
                else
                    ConsoleManager.printErr("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                ConsoleManager.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Asks a user the person's coordinates.
     *
     * @return person's coordinates.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        long x = askXOfCoordinates(true);
        float y = askYOfCoordinates();
        return new Coordinates(x, y);
    }

    /**
     * Asks a user the person's height
     *
     * @return Person's height
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public int askPersonHeight() throws IncorrectInputInScriptException {
        String strHeight = "";
        int height;
        while (true) {
            try {
                System.out.println("Введите рост персоны:");
                System.out.print(Main.INPUT_INFO);
                strHeight = userScanner.nextLine().trim();
                if (fileMode) System.out.println(strHeight);
                height = Integer.parseInt(strHeight);
                if (height <= MIN_HEIGHT || height >= MAX_HEIGHT) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                ConsoleManager.printErr("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strHeight).matches())
                    ConsoleManager.printErr("Число должно быть в диапазоне (" + MIN_HEIGHT + ";" + MAX_HEIGHT + ")!");
                else
                    ConsoleManager.printErr("Рост должен быть представлен числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                ConsoleManager.printErr("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                ConsoleManager.printErr("Число должно быть в диапазоне (" + MIN_HEIGHT + ";" + MAX_HEIGHT + ")!");
            }
        }
        return height;
    }

    /**
     * Asks a user the person's eye color
     *
     * @return Person's eye color
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Color askPersonEyeColor() throws IncorrectInputInScriptException {
        String strEyeColor;
        Color color;
        while (true) {
            try {
                System.out.println("Список цветов - " + Color.nameList());
                System.out.println("Введите цвет глаз:");
                System.out.print(Main.INPUT_INFO);
                strEyeColor = userScanner.nextLine().trim();
                if (fileMode) System.out.println(strEyeColor);
                color = Color.valueOf(strEyeColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                ConsoleManager.printErr("Цвет глаз не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                ConsoleManager.printErr("Данного цвета глаз нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                ConsoleManager.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return color;
    }

    /**
     * Asks a user the person's hair color
     *
     * @return Person's hair color
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Color askPersonHairColor() throws IncorrectInputInScriptException {
        String strHairColor;
        Color color;
        while (true) {
            try {
                System.out.println("Список цветов - " + Color.nameList());
                System.out.println("Введите цвет волос:");
                System.out.print(Main.INPUT_INFO);
                strHairColor = userScanner.nextLine().trim();
                if (fileMode) System.out.println(strHairColor);
                color = Color.valueOf(strHairColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                ConsoleManager.printErr("Цвет волос не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                ConsoleManager.printErr("Данного цвета волос нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                ConsoleManager.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return color;
    }

    /**
     * Asks a user the person's nationality.
     *
     * @return nationality of the person
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Country askPersonNationality() throws IncorrectInputInScriptException {
        String strNationality;
        Country nationality;
        while (true) {
            try {
                System.out.println("Список национальностей - " + Country.nameList());
                System.out.println("Введите национальность человека:");
                System.out.print(Main.INPUT_INFO);
                strNationality = userScanner.nextLine().trim();
                if (fileMode) System.out.println(strNationality);
                nationality = Country.valueOf(strNationality.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                ConsoleManager.printErr("Национальность не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                ConsoleManager.printErr("Данной национальности нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                ConsoleManager.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return nationality;
    }

    /**
     * Asks a user the X/Y/Z coordinate of location.
     *
     * @param coordinateAxis name of coordinateAxis
     * @return value of coordinate
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public double askCoordinateOfLoсation(String coordinateAxis) throws IncorrectInputInScriptException {
        String strCoordinate = "";
        double coordinate;
        while (true) {
            try {
                System.out.printf("Введите координату локации %s:%n", coordinateAxis);
                System.out.print(Main.INPUT_INFO);
                strCoordinate = userScanner.nextLine().trim();
                if (fileMode) System.out.println(strCoordinate);
                coordinate = Double.parseDouble(strCoordinate);
                break;
            } catch (NoSuchElementException exception) {
                ConsoleManager.printErr(String.format("Координата %s не распознана!", coordinateAxis));
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strCoordinate).matches())
                    ConsoleManager.printErr("Координата должна быть в диапазоне (" + Double.MIN_VALUE
                            + ";" + Double.MAX_VALUE + ")!");
                else
                    ConsoleManager.printErr("Координата должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                ConsoleManager.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return coordinate;
    }

    /**
     * Asks a user the person's location
     *
     * @return person's location
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Location askPersonLocation() throws IncorrectInputInScriptException {
        double x = askCoordinateOfLoсation("X");
        double y = askCoordinateOfLoсation("Y");
        int z = (int) askCoordinateOfLoсation("Z");
        String name = askName("Введите название локации:", 0, Integer.MAX_VALUE, "Название локации");
        return new Location(x, y, z, name);
    }

    /**
     * Asks a user a question.
     *
     * @param question A question.
     * @return Answer (true/false).
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                System.out.println(finalQuestion);
                System.out.print(Main.INPUT_INFO);
                answer = userScanner.nextLine().trim();
                if (fileMode) System.out.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                ConsoleManager.printErr("Ответ не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInBoundsException exception) {
                ConsoleManager.printErr("Ответ должен быть представлен знаками '+' или '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                ConsoleManager.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }

    /**
     * Asks user name or absolute path of file to read and save a collection
     * @return name or absolute path of file
     */
    public String askFileName() {
        String fileName;
        while (true) {
            try {
                System.out.println("Введите файла для записи и чтения коллекции:");
                System.out.print(Main.INPUT_INFO);
                fileName = userScanner.nextLine().trim();
                if (fileName.equals("")) throw new NoSuchElementException();
                File file = new File(fileName);
                if(!file.exists()) throw new IllegalArgumentException();
                break;
            } catch (NoSuchElementException exception) {
                ConsoleManager.printErr("Имя файла не распознан!");
            } catch (IllegalStateException exception) {
                ConsoleManager.printErr("Непредвиденная ошибка!");
                System.exit(0);
            } catch (IllegalArgumentException exception){
                ConsoleManager.printErr("Файла с таким именем не существует!");
            }
        }
        return fileName;
    }
}


