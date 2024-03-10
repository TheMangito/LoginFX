package com.example.login;

import com.resend.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.SendEmailRequest;
import com.resend.services.emails.model.SendEmailResponse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class HelloController {
    @FXML
    private Pane pane;
    @FXML
    private Button createAccount;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private AnchorPane registerPane;
    @FXML
    private Button backToLogin;

    @FXML
    private Button loginButton;
    @FXML
    private Button signUp;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    @FXML
    private CheckBox checkBox;
    private List<User> users;
    @FXML
    private TextField usernameLogin;
    @FXML
    private PasswordField passwordLogin;
    @FXML
    private CheckBox rememberMeBox;

    @FXML
    private Label welcomeText;
    @FXML
    private AnchorPane welcomePane;

    @FXML Button loginClose;

    private File registers = new File("src/main/resources/registers/registers.json");
    private File rememberMe = new File("src/main/resources/registers/rememberMe.json");

    public void initialize() throws IOException {
        updateRegister();
        loadRememberMe();
        System.out.println(randomNumber());
    }

    public void loadRememberMe() throws IOException {
        String json = FileUtils.readFileToString(rememberMe, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        User rememberUser = objectMapper.readValue(json, User.class);
        usernameLogin.setText(rememberUser.getUsername());
        passwordLogin.setText(rememberUser.getPassword());
    }
    public void updateRegister(){
        try {
            String json = FileUtils.readFileToString(registers, StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            users = objectMapper.readValue(json, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void signUp() throws ResendException, IOException {
        if (!name.getText().isEmpty() && !email.getText().isEmpty() && !username.getText().isEmpty() && !password.getText().isEmpty() &&
        checkBox.isSelected()){

            User user = new User(name.getText(), username.getText(), password.getText(), email.getText());
            ObjectMapper mapper = new ObjectMapper();
            users.add(user);

            try {
                ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                writer.writeValue(registers, users);
                sendMail(username.getText(), "1", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void createAccount(){
        loginPane.setVisible(false);
        registerPane.setVisible(true);
    }
    public void backToLogin(){
        loginPane.setVisible(true);
        registerPane.setVisible(false);
    }

    public void loginButton(){
        for(User user :users){
            if(user.getUsername().equals(usernameLogin.getText()) && user.getPassword().equals(passwordLogin.getText())){
                loginPane.setVisible(false);
                registerPane.setVisible(false);
                welcomeText.setText("Bienvenid@ "+user.getUsername()+".");
                welcomePane.setVisible(true);
                if (rememberMeBox.isSelected()){
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
                        writer.writeValue(rememberMe, user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
    public String readHTMLFile() throws IOException{
        FileInputStream fis = new FileInputStream("src/main/resources/com/example/login/htmlMail.txt");
        String stringTooLong = IOUtils.toString(fis, "UTF-8");
        return stringTooLong;
    }

    public int randomNumber(){
        return (int)Math.floor(Math.random()*(999999-100000+1)+100000);
    }
    public void sendMail(String username, String codigo, String email) throws ResendException, IOException {
        Resend resend = new Resend("re_S4LJFBDN_KGvhab1dgqQJkPE1j1ZQzKR8");

        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .from("onboarding@resend.dev")
                .to("themanguito21@gmail.com", "sr.esmoquin@outlook.com")
                .subject("Codigo De Verificacion")
                .html("<img src=\"https://i.imgur.com/CCvfOXa.png\">\n" +
                        "<h1 style=\"width: 975px; padding: 20px; color: #f4f4f7; text-align: center; background-color: #343773; font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;\"><b>Bienvenido   " + username +"</b></h1>\n" +
                        "<p style=\"width: 975px; color: #131313; padding: 20px; text-align: center; font-family: Arial, Helvetica, sans-serif; font-size: 12px;\">¡Bienvenido/a a nuestra aplicación! Estamos encantados de tenerte con nosotros. Esperamos que disfrutes de todas las funciones y servicios que ofrecemos. Si necesitas ayuda o tienes alguna pregunta, no dudes en contactarnos. ¡Que tengas una excelente experiencia!</p>\n" +
                        "<p style=\"width: 975px; color: #131313; padding: 20px; text-align: center; font-family: Arial, Helvetica, sans-serif; font-size: 12px;\"><strong>Código de verificación en dos pasos: "+ codigo +"</strong></p>\n" +
                        "<p> </p>")
                .build();

        SendEmailResponse data = resend.emails().send(sendEmailRequest);

    }
    public void logout(){
        loginPane.setVisible(true);
        welcomePane.setVisible(false);
        registerPane.setVisible(false);
    }

    public void closeLogin(){
        Platform.exit();
    }




}