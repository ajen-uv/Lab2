import java.util.Scanner;
import java.io.PrintStream;

public class Main {

    // ======================
    // Глобальные переменные.
    // ======================

    static int n;
    static char [][]a;

    static PrintStream out = System.out;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // =================================================
        // Пункт 1: Считываем размер матрицы и заполняем ее.
        // =================================================

        n = in.nextInt();
        a= new char[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                a[i][j] = in.next().charAt(0);
            }
        }

        // Вызов подпрограмм для пунктов.
        sortColumns();
        printMostFrequentChar();
        printSpiral();
        encryptAndPrint();

    }

    // ===========================================================================
    // Пункт 2 : Сортировка столбцов по возрастанию гласных, затем по сумме ASCII.
    // ===========================================================================

    static void sortColumns(){

        int[] vowels = new int[n]; // Массив для хранения количества гласных в каждом столбце.
        int[] sum = new int[n];    // Массив для хранения суммы ASCII-кодов в каждом столбце.

        // Шаг 1: Считаем гласные и суммы кодов для каждого столбца.

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){

                char ch = a[i][j];
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'
                        || ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U'){
                    vowels[j]++;
                }
                sum[j] += (int) ch;
            }
        }

        // Шаг 2: Создаем массив индексов. Сортируем номера столбцов.

        int[] order = new int[n];
        for(int j = 0; j < n; j++){
            order[j] = j;
        }

        // Шаг 3: Пузырьковая сортировка массива индексов.

        for(int i = 0; i < n - 1; i++){
            for(int j = 0; j < n - 1 - i; j++){
                int x = order[j];
                int y = order[j + 1];
                boolean needSwap = false;

                // Сравнение по:

                    // 1. Количеству гласных.
                if(vowels[x] > vowels[y]){
                    needSwap = true;

                    // 2. Если гласных поровну - по сумме ASCII-кодов.
                }else if(vowels[x] == vowels[y]){
                    if(sum[x] > sum[y]){
                        needSwap = true;

                        // 3. Если и коды равны - по изначальному индексу.
                    }else if(sum[x] == sum[y] && x > y){
                        needSwap = true;
                    }
                }

                // Меняем местами номера столбцов.

                if(needSwap){
                    order[j] = y;
                    order[j + 1] = x;
                }
            }
        }

        // Шаг 4: Собираем новый массив по отсортированным номерам столбцов.

        char [][] sorted = new char[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                sorted[i][j] = a[i][order[j]];
            }
        }

        // Запоминаем обновленный массив.

        a = sorted;

        out.println("Пункт 2 (сортировка)");

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                out.print(a[i][j] + " ");
            }
            out.println();
        }
    }

    // ==============================
    // Пункт 3 : Самый частый символ.
    // ==============================

    static void printMostFrequentChar(){

        // Массив индексов букв без учета регистра.

        int[] count = new int[26];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                char ch = a[i][j];

                if(ch >= 'a' && ch <= 'z'){
                    count[ch - 'a']++;      // Для строчных.
                }else if(ch >= 'A' && ch <= 'Z'){
                    count[ch - 'A']++;      // Для заглавных.
                }
            }
        }

        // Находим максимальную частоту повторений.

        int max = 0;
        for(int i = 0; i < 26; i++){
            if(count[i] > max){
                max = count[i];
            }
        }

        out.println("Пункт 3 (символ, который чаще всего встречается)");
        for(int i = 0; i < 26; i++){
            if(count[i] == max){
                out.print((char)('a' + i) + " "); // Преобразует индекс обратно в символ.
            }
        }
        out.println();
    }
    // ============================================
    // Пункт 4 : Вывод элементов массива по спирали
    // ============================================

    static void printSpiral(){
        out.println("Пункт 4 (элементы массива в виде спирали)");

        int r,c; // Координаты клетки.

        if(n % 2 == 1){
            r = n/2;
            c = n/2;
        }else{
            r = n/2;
            c = n/2 -1;
        }

        int total = n * n;
        int done = 0; // Сколько элементов напечатаны.

        // Печатаем центральный элемент

        out.print(a[r][c] + " ");
        done++;

        int step = 1; // Длина текущего шага

        while(done < total){
            // Вправо
            for(int i = 0; i < step && done < total; i++){
                c++;
                out.print(a[r][c] + " ");
                done++;
            }
            // Вверх
            for(int i = 0; i < step && done < total; i++){
                r--;
                out.print(a[r][c] + " ");
                done++;
            }
            step++;

            // Влево
            for(int i = 0; i < step && done < total; i++){
                c--;
                out.print(a[r][c] + " ");
                done++;
            }
            // Вниз
            for(int i = 0; i < step && done < total; i++){
                r++;
                out.print(a[r][c] + " ");
                done++;
            }
            step++;
        }
        out.println();
    }

    // ===================
    // Пункт 5: Шифрование
    // ===================

    static void encryptAndPrint(){
        out.println("Пункт 5 (зашифрованный массив)");

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                char ch = a[i][j];

                if(ch >= 'a' && ch <= 'z'){
                    ch = (char)(ch - 32); // Из строчной в заглавную
                }else if(ch >= 'A' && ch <= 'Z'){
                    ch = (char)(ch + 32); // Из заглавной в строчную
                }

                a[i][j] = ch;
                out.print(a[i][j] + " ");
            }
            out.println();
        }
    }
}