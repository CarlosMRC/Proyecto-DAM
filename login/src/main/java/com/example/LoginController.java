package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    private Button backToLoginButton;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private Button loginButton;

    @FXML
    private VBox loginPane;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private VBox registrationPane;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField loginUsernameField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Text txtMessage;

    @FXML
    private void showRegistrationPane() {
        registrationPane.toFront();
        clearAllText();
    }

    @FXML
    private void showLoginPane() {
        loginPane.toFront();
        clearAllText();
    }

    private void clearAllText() {
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        loginUsernameField.clear();
        loginPasswordField.clear();
    }

    @FXML
    private void realizarPeticionRegistro() {
        String nombreUsuario = usernameField.getText();
        String correoElectronico = emailField.getText();
        String contraseña = passwordField.getText();
        String confirmarContraseña = confirmPasswordField.getText();

        if (nombreUsuario.isEmpty() || correoElectronico.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
            txtMessage.setText("Por favor, rellene todos los campos");
        } else if (!contraseña.equals(confirmarContraseña)) {
            txtMessage.setText("Las contraseñas no coinciden");
            passwordField.clear();
            confirmPasswordField.clear();
        } else {
            clearAllText();
            registrarUsuario(nombreUsuario, correoElectronico, contraseña);
        }
    }

    @FXML
    private void realizarPeticionInicioSesion() {
        String nombreUsuario = loginUsernameField.getText();
        String contraseña = loginPasswordField.getText();

        iniciarSesion(nombreUsuario, contraseña);
    }

    public void registrarUsuario(String nombreUsuario, String correoElectronico, String contraseña) {
        try {
            URL url = new URL("http://localhost:8080/registro");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construir el cuerpo de la solicitud en formato JSON
            String cuerpoSolicitud = String.format(
                    "{\"nombreUsuario\":\"%s\",\"correoElectronico\":\"%s\",\"contraseña\":\"%s\"}",
                    nombreUsuario, correoElectronico, contraseña);

            // Escribir el cuerpo de la solicitud en el flujo de salida
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = cuerpoSolicitud.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            // Leer la respuesta del servidor
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                StringBuilder respuesta = new StringBuilder();
                String respuestaLinea = null;
                while ((respuestaLinea = br.readLine()) != null) {
                    respuesta.append(respuestaLinea.trim());
                }
                System.out.println("Respuesta del servidor: " + respuesta.toString());
                txtMessage.setText(respuesta.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iniciarSesion(String nombreUsuario, String contraseña) {
        try {
            URL url = new URL("http://localhost:8080/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construir el cuerpo de la solicitud en formato JSON
            String cuerpoSolicitud = String.format("{\"nombreUsuario\":\"%s\",\"contraseña\":\"%s\"}",
                    nombreUsuario, contraseña);
    
             // Escribir el cuerpo de la solicitud en el flujo de salida
             try (OutputStream os = connection.getOutputStream()) {
                byte[] input = cuerpoSolicitud.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            // Leer la respuesta del servidor
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                StringBuilder respuesta = new StringBuilder();
                String respuestaLinea = null;
                while ((respuestaLinea = br.readLine()) != null) {
                    respuesta.append(respuestaLinea.trim());
                }
                System.out.println("Respuesta del servidor: " + respuesta.toString());
                txtMessage.setText(respuesta.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
