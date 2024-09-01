package org.task;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StudentManagement {

  public static void main(String[] args) {
    List<Student> students = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    int choice;

    while (true) {
      try {
        System.out.println("1. 学生を追加");
        System.out.println("2. 学生を削除");
        System.out.println("3. 点数を更新");
        System.out.println("4. 平均点を計算");
        System.out.println("5. 全学生の情報を表示");
        System.out.println("6. 終了");
        System.out.print("数字を選択してください: ");
        choice = scanner.nextInt();
        scanner.nextLine();  // 改行を消費

        switch (choice) {
          case 1:
            System.out.print("学生の名前を入力してください: ");
            String nameToAdd = scanner.nextLine();
            if (nameToAdd.isBlank()) {
              throw new IllegalArgumentException("名前は空にできません。");
            }
            System.out.print(nameToAdd + "の点数を入力してください: ");
            int scoreToAdd = scanner.nextInt();
            scanner.nextLine();  // 改行を消費
            if (scoreToAdd < 0 || scoreToAdd > 100) {
              throw new IllegalArgumentException("点数は0から100の間で入力してください。");
            }
            students.add(new Student(nameToAdd, scoreToAdd));
            break;

          case 2:
            System.out.print("削除する学生の名前を入力してください: ");
            String nameToRemove = scanner.nextLine();
            if (nameToRemove.isBlank()) {
              throw new IllegalArgumentException("名前は空にできません。");
            }
            boolean removed = students.removeIf(student -> student.getName().equals(nameToRemove));
            if (!removed) {
              System.out.println("エラー：指定された名前の学生が見つかりませんでした。");
            }
            break;

          case 3:
            System.out.print("点数を更新する学生の名前を入力してください: ");
            String nameToUpdate = scanner.nextLine();
            if (nameToUpdate.isBlank()) {
              throw new IllegalArgumentException("名前は空にできません。");
            }
            boolean found = false;
            for (Student student : students) {
              if (student.getName().equals(nameToUpdate)) {
                System.out.print(nameToUpdate + "の新しい点数を入力してください: ");
                int newScore = scanner.nextInt();
                scanner.nextLine();  // 改行を消費
                if (newScore < 0 || newScore > 100) {
                  throw new IllegalArgumentException(
                      "点数は0から100の間で入力してください。");
                }
                student.setScore(newScore);
                found = true;
                break;
              }
            }
            if (!found) {
              System.out.println("エラー：指定された名前の学生が見つかりませんでした。");
            }
            break;

          case 4:
            if (students.isEmpty()) {
              System.out.println("エラー：学生データがありません。");
            } else {
              double averageScore = students.stream()
                  .mapToInt(Student::getScore)
                  .average()
                  .orElse(0.0);
              System.out.println("平均点: " + averageScore + "点");
            }
            break;

          case 5:
            if (students.isEmpty()) {
              System.out.println("エラー：学生データがありません。");
            } else {
              System.out.println("学生一覧:");
              students.forEach(System.out::println);
            }
            break;

          case 6:
            System.out.println("プログラムを終了します。");
            scanner.close();
            return;

          default:
            System.out.println("エラー：無効な選択です。もう一度試してください。");
        }
      } catch (InputMismatchException e) {
        System.out.println("エラー：入力が不正です。適切な数値を入力してください。");
        scanner.nextLine(); // 不正な入力を消費してリセット
      } catch (IllegalArgumentException e) {
        System.out.println("エラー: " + e.getMessage());
      }
    }
  }
}
