package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.text.Charsets;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws Exception {
        if (!Files.lines(Paths.get(inputName)).allMatch(t -> (t.matches("^\\d+$"))))
            throw new IllegalArgumentException();

        List<Integer> prices = new ArrayList<Integer>();
        List<Integer> delta = new ArrayList<Integer>();
        List<String> inputLines = Files.readAllLines(Paths.get(inputName));

        prices.add(Integer.parseInt(inputLines.get(0)));
        for (int k = 1; k < inputLines.size(); k++) {
            prices.add(Integer.parseInt(inputLines.get(k)));
            delta.add(prices.get(prices.size() - 1) - prices.get(prices.size() - 2));
        }
        if (delta.isEmpty()) throw new IllegalArgumentException();

        int maxSum = delta.get(0), left = 0, right = 0, sum = 0, minLeft = -1;
        for (int i = 0; i < delta.size(); i++) {
            sum += delta.get(i);
            if (sum > maxSum) {
                maxSum = sum;
                left = minLeft + 1;
                right = i;
            }
            if (sum < 0) {
                sum = 0;
                minLeft = i;
            }
        }
        return new Pair<Integer, Integer>(left + 1, right + 2);
    }
    // Трудоемкость - O(n)
    // Ресурсоемкость - O(n)

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String first, String second) {
        int[][] dynamic = new int[first.length() + 1][second.length() + 1];
        int i, j;
        int maxLen = 0, maxI = 0;

        for (i = 1; i < first.length() + 1; i++) {
            for (j = 1; j < second.length() + 1; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    dynamic[i][j] = dynamic[i - 1][j - 1] + 1;
                    if (dynamic[i][j] > maxLen ||
                            dynamic[i][j] == maxLen && i < maxI) {
                        maxLen = dynamic[i][j];
                        maxI = i;
                    }
                }
            }
        }
        return first.substring(maxI - maxLen, maxI);
    }
    // Трудоемкость - O(first.length() * second.length())
    // Ресурсоемкость - O(first.length() * second.length())

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        throw new NotImplementedError();
    }

    /**
     * Балда
     * Сложная
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) throws Exception {
        if (!Files.lines(Paths.get(inputName)).allMatch(t -> (t.matches("^[A-ZА-Я\\s]+$"))))
            throw new IllegalArgumentException();
        List<String> inputLines = Files.readAllLines(Paths.get(inputName));
        List<String[]> table = new ArrayList<String[]>();
        Set<String> ans = new HashSet<String>();

        for (String line: inputLines) {
            table.add(line.split(" "));
        }
        for (String[] line: table)
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(0).length; j++) {
                for (String word: words) {
                    if (table.get(i)[j].equals(word.substring(0, 1))) {
                        if (dfs(i, j, table, word.substring(1))) {
                            ans.add(word);
                        }
                    }
                }
            }
        }
        return ans;
    }

    private static boolean dfs(int i, int j, List<String[]> table, String suffix) {
        if (suffix.isEmpty()) return true;
        int x, y;
        Iterator<Integer> iterX = Arrays.asList(i - 1, i, i, i + 1).iterator();
        Iterator<Integer> iterY = Arrays.asList(j, j - 1, j + 1, j).iterator();
        for (int k = 0; k < 4; k++){
            x = iterX.next();
            y = iterY.next();
            if (x > -1 && x < table.size() && y > -1 && y < table.get(0).length &&
                    table.get(x)[y].charAt(0) == suffix.charAt(0) &&
                    dfs(x, y, table, suffix.substring(1))) {
                return true;
            }
        }
        return false;
    }
    // Пусть n - количество строк в матрице, m - количество столбцов в матрице,
    // k - количество слов в множестве words, len - длина самого большого слова в множестве words
    // Трудоемкость - О(n * m * k * len)
    // Ресурсоемкость - О(n * m)

}

