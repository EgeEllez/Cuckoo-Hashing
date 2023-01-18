package com.example.demo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Scanner;
import javafx.geometry.Insets;


public class CuckooVisualization extends Application {
    private CuckooHashing cuckoo;
    private GridPane gridPane;
    private BorderPane borderPane;
    public CuckooVisualization(CuckooHashing cuckoo) {
        this.cuckoo = cuckoo;
    }

    @Override
    public void start(Stage primaryStage) {
        borderPane = new BorderPane();
        gridPane = new GridPane();
        initialize();
        borderPane.setCenter(gridPane);
        primaryStage.setScene(new Scene(borderPane, 250, 650));
        primaryStage.show();
    }

    public void initialize() {
        gridPane.getChildren().clear();
        for (int i = 0; i < cuckoo.ver; i++) {
            Label tableName = new Label("Table " + (i + 1));
            tableName.setPadding(new Insets(5));
            gridPane.add(tableName, i, 0);
            for (int j = 0; j < cuckoo.MAXN; j++) {
                Label label = new Label();
                if (cuckoo.hashtable[i][j] == -2147483648) {
                    label.setText("None");
                    label.setPadding(new Insets(2));
                } else {
                    label.setText(Integer.toString(cuckoo.hashtable[i][j]));
                }
                gridPane.add(label, i, j + 1);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        int MAXN;
        int ver;
        do {
            System.out.print("Enter the value of table (maximum 5): ");
            ver = sc2.nextInt();
        } while (ver > 5);
        do {
            System.out.print("Enter the value of size(minimum 10 maximum 30 ");
            MAXN = sc.nextInt();
        } while (MAXN < 10 || MAXN > 30);

        CuckooHashing ch = new CuckooHashing(MAXN, ver);
        CuckooVisualization cv = new CuckooVisualization(ch);

        Platform.runLater(() -> {
            try {
                cv.init();
                cv.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        while (true) {
            System.out.println("Enter the operation you want to perform (insert, search, delete, exit): ");
            String operation = sc.next();
            if (operation.equalsIgnoreCase("insert")) {
                System.out.println("Enter the key you want to insert: ");
                int key = sc.nextInt();
                ch.insert(key);
                System.out.println(key + " inserted.");
            } else if (operation.equalsIgnoreCase("search")) {
                System.out.println("Enter the key you want to search: ");
                int key = sc.nextInt();
                if (ch.search(key)) {
                    System.out.println(key + " found.");
                } else {
                    System.out.println(key + " not found.");
                }
            } else if (operation.equalsIgnoreCase("delete")) {
                System.out.println("Enter the key you want to delete: ");
                int key = sc.nextInt();
                if (ch.delete(key)) {
                    System.out.println(key + " deleted.");
                } else {
                    System.out.println(key + " not found.");
                }
            }else if (operation.equalsIgnoreCase("exit")) {
                break;
            }else {
                System.out.println("Invalid operation. Please enter a valid operation.");
            }
            ch.print_table();
            System.out.println("Number of collisions: " + ch.collisions);
            System.out.println("Load Factor: " + ch.loadFactor);

            Platform.runLater(cv::initialize);
        }
    }
}

