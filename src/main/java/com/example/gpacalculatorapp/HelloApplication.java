package com.example.gpacalculatorapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
       openHomePage(stage);

    }

    public static void main(String[] args) {
        launch();
    }

    private void openHomePage(Stage stage) {
        Button button1 = new Button("Enroll A course");
        Button button2 = new Button("GPA Calculator");
        Button button3 = new Button("Insert Student Degree");
        Button button4 = new Button("Suggest A Subject");
        Button button5 = new Button("Display Enrolled Courses");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(button1, button2, button3, button4, button5);

        button1.setOnAction(e -> openEnrollCourse(stage));
        button2.setOnAction(e -> openGpaCalculator(stage));
        button3.setOnAction(e -> openInsertingDegree(stage));
        button4.setOnAction(e -> openSuggestCourses(stage));
        button5.setOnAction(e -> openEnrolledCourses(stage));

        Scene scene = new Scene(layout, 800, 500);
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.show();
    }

    private void  openEnrollCourse(Stage primaryStage) {
        TextField textField1 = new TextField("Enter course id");
        TextField textField2 = new TextField("Enter student id");
        Button enrollBtn = new Button("Enroll");
        Button homeBtn = new Button("Go To Home");
        TextArea confirmMessage = new TextArea();
        confirmMessage.setPrefSize(300, 150);

        enrollBtn.setOnAction(e -> {
            var courseId = Integer.parseInt(textField1.getText());
            var studentId = Integer.parseInt(textField2.getText());
            try {
                makeTrustCertifications();
            } catch (KeyManagementException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }

            String baseUrl = "https://localhost:7172/api/Features/EnrollCourseToStudent";
            baseUrl += "?courseId=" + courseId + "&studentId=" + studentId;

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                var response = callAPI(connection);
                confirmMessage.setText(response);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (ProtocolException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(textField1, textField2, confirmMessage, homeBtn, enrollBtn);
        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        homeBtn.setOnAction(e -> {
            primaryStage.close();
            openHomePage(primaryStage);
        });

    }

    private void openGpaCalculator(Stage primaryStage) {
        TextField textField1 = new TextField("Enter student id");
        Button calcBtn = new Button("Calculate GPA");
        Button homeBtn = new Button("Go To Home");
        TextArea confirmMessage = new TextArea();
        confirmMessage.setPrefSize(300, 150);

        calcBtn.setOnAction(e -> {
            var studentId = Integer.parseInt(textField1.getText());

            try {
                makeTrustCertifications();
            } catch (KeyManagementException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }

            String baseUrl = "https://localhost:7172/api/Features/CalculateTotalGPAFor";
            baseUrl += "?studentId=" + studentId;

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                var response = callAPI(connection);
                confirmMessage.setText(response);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (ProtocolException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(textField1, calcBtn, homeBtn, confirmMessage);
        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        homeBtn.setOnAction(e -> {
            primaryStage.close();
            openHomePage(primaryStage);
        });

    }

    private void openInsertingDegree(Stage primaryStage) {
        TextField textField1 = new TextField("Enter course id");
        TextField textField2 = new TextField("Enter student id");
        TextField textField3 = new TextField("Enter student mark");

        Button updateBtn = new Button("Update Degree");
        Button homeBtn = new Button("Go To Home");
        TextArea confirmMessage = new TextArea();
        confirmMessage.setPrefSize(300, 150);

        updateBtn.setOnAction(e -> {
            var studentId = Integer.parseInt(textField2.getText());
            var courseId = Integer.parseInt(textField1.getText());
            var studentMark = Integer.parseInt(textField3.getText());

            try {
                makeTrustCertifications();
            } catch (KeyManagementException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }

            String baseUrl = "https://localhost:7172/api/Features/" + studentId + "/" + courseId
                    + "?studentMark=" + studentMark;

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                var response = callAPI(connection);
                confirmMessage.setText(response);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (ProtocolException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(textField1, textField2, textField3, homeBtn, updateBtn, confirmMessage);
        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        homeBtn.setOnAction(e -> {
            primaryStage.close();
            openHomePage(primaryStage);
        });


    }

    private void openSuggestCourses(Stage primaryStage) {
        TextField textField1 = new TextField("Enter student id");
        Button suggest = new Button("Suggest Courses");
        Button homeBtn = new Button("Go To Home");
        TextArea confirmMessage = new TextArea();
        confirmMessage.setPrefSize(300, 150);

        suggest.setOnAction(e -> {
            var studentId = Integer.parseInt(textField1.getText());

            try {
                makeTrustCertifications();
            } catch (KeyManagementException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }

            String baseUrl = "https://localhost:7172/api/Features/SuggestCourses";
            baseUrl += "?studentId=" + studentId;

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                var response = callAPI(connection);
                confirmMessage.setText(response);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (ProtocolException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(textField1, homeBtn, suggest, confirmMessage);
        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        homeBtn.setOnAction(e -> {
            primaryStage.close();
            openHomePage(primaryStage);
        });


    }

    private void openEnrolledCourses(Stage primaryStage) {
        TextField textField1 = new TextField("Enter student id");
        Button getEnrolledBtn = new Button("Get Enrolled Courses");
        Button homeBtn = new Button("Go To Home");
        TextArea confirmMessage = new TextArea();
        confirmMessage.setPrefSize(300, 150);

        getEnrolledBtn.setOnAction(e -> {
            var studentId = Integer.parseInt(textField1.getText());

            try {
                makeTrustCertifications();
            } catch (KeyManagementException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }

            String baseUrl = "https://localhost:7172/api/Features/GetEnrolledCourses";
            baseUrl += "?studentId=" + studentId;

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                var response = callAPI(connection);
                confirmMessage.setText(response);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (ProtocolException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(textField1, homeBtn, getEnrolledBtn, confirmMessage);
        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        homeBtn.setOnAction(e -> {
            primaryStage.close();
            openHomePage(primaryStage);
        });


    }

    private void makeTrustCertifications() throws KeyManagementException, NoSuchAlgorithmException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };


        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    }

    private String callAPI(HttpURLConnection connection) throws MalformedURLException, ProtocolException {
        String responseMessage = "";
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    responseMessage += line;
                }
            } catch (Exception e) {
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String line;
                    StringBuilder errorResponse = new StringBuilder();
                    while ((line = errorReader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    responseMessage = errorResponse.toString();
                }
            }

            connection.disconnect();
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return responseMessage;

    }
}