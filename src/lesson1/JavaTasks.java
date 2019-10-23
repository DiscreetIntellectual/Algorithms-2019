package lesson1;

import kotlin.NotImplementedError;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) {
        try {
            FileReader inputFile = new FileReader(inputName);
            Scanner inputScan = new Scanner(inputFile);
            FileWriter outputFile = new FileWriter(outputName);
            int[] countList = new int[7731];
            int temp;
            while (inputScan.hasNextLine()) {
                temp = (int) ( Double.parseDouble(inputScan.nextLine()) * 10 + 2730);
                countList[temp]++;
            }
            for (int i = 0; i < 7731; i++) {
                for (int j = 0; j < countList[i]; j++) {
                    outputFile.write((i - 2730) / 10.0 + "\n");
                }
            }
            inputFile.close();
            outputFile.close();
        }
        catch (IOException e) {}
    }
    // Трудоемкость: O(n)
    // Ресурсоемкость: О(1)    <- int[7731] ~ O(1)

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        try {
            FileReader inputFile = new FileReader(inputName);
            Scanner inputScan = new Scanner(inputFile);
            FileWriter outputFile = new FileWriter(outputName);
            List<Integer> seq = new ArrayList<Integer>();
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            int temp;
            int maxCount = -1;
            int maxKey = 0;
            List<String> maxKeys = new ArrayList<String>();
            List<String> ans = new ArrayList<String>();
            while (inputScan.hasNextLine()) {
                temp = Integer.parseInt(inputScan.nextLine());
                seq.add(temp);
                if (map.containsKey(temp))
                    map.put(temp, map.get(temp) + 1);
                else map.put(temp, 0);
                if (map.get(temp) > maxCount || (map.get(temp) == maxCount && temp < maxKey)) {
                    maxKey = temp;
                    maxCount = map.get(temp);
                }
            }
            for (int i: seq) {
                if (i == maxKey) maxKeys.add(Integer.toString(i));
                else ans.add(Integer.toString(i));
            }
            ans.addAll(maxKeys);
            outputFile.write(String.join("\n", ans));
            inputFile.close();
            outputFile.close();
        }
        catch (IOException e) {}
    }
    // Трудоемкость: O(n)
    // Ресурсоемкость: О(n)

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        int point1 = 0;
        int point2 = first.length;
        int i = 0;
        while (point1 < first.length && point2 < second.length) {
            if (first[point1].compareTo(second[point2]) < 0) {
                second[i] = first[point1];
                point1++;
            }
            else {
                second[i] = second[point2];
                point2++;
            }
            i++;
        }
        if (point2 == second.length) {
            while (i < second.length) {
                second[i] = first[point1];
                point1++;
                i++;
            }
        }
    }
    // Трудоемкость: O(n)
    // Ресурсоемкость: О(1)

}
