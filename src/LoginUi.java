import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginUi extends Application{

    private Scene sceneLogin;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Account.read();
        Item.read();

        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setVgap(8);
        loginGrid.setHgap(4);

        TextField tfUsername = new TextField();
        PasswordField tfPassword = new PasswordField();

        Label info = new Label("");

        Button btLogin = new Button("Login");
        Button btRegister = new Button("Register");
        btLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Account.login(tfUsername.getText(), tfPassword.getText());
                    primaryStage.close();
                }catch (Exception e) {
                    info.setText("Login Failed");
                }
            }
        });
        btRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setSceneRegister(primaryStage);
            }
        });

        info.setTextFill(Color.RED);

        loginGrid.add(new Label("Username:"), 0, 0);
        loginGrid.add(tfUsername, 1, 0);
        loginGrid.add(new Label("Password:"), 0, 1);
        loginGrid.add(tfPassword, 1, 1);

        HBox btBox = new HBox();
        btBox.setAlignment(Pos.CENTER_LEFT);
        btBox.setSpacing(10);
        btBox.getChildren().add(btLogin);
        btBox.getChildren().add(btRegister);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20,20,20,20));
        vBox.getChildren().add(loginGrid);
        vBox.getChildren().add(btBox);
        vBox.getChildren().add(info);

        sceneLogin = new Scene(vBox);

        primaryStage.setTitle("Login");
        primaryStage.setScene(sceneLogin);
        primaryStage.show();

    }

    private void setSceneRegister(Stage stage) {
        GridPane registerGrid = new GridPane();
        registerGrid.setAlignment(Pos.CENTER);
        registerGrid.setVgap(8);
        registerGrid.setHgap(4);

        TextField tfUsername = new TextField();
        PasswordField tfPassword = new PasswordField();
        PasswordField tfConfirm = new PasswordField();

        Label info = new Label("");
        info.setTextFill(Color.RED);

        Button btCancel = new Button("Cancel");
        Button btConfirm = new Button("Confirm");
        btCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(sceneLogin);
            }
        });
        btConfirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tfPassword.getText().equals(tfConfirm.getText())) {
                    try {
                        Account.register(tfUsername.getText(), tfPassword.getText(), false);
                    }catch (UsernameInUseException ex) {
                        info.setText("Username is already in use");
                    }catch (Exception ex) {
                        info.setText("Register failed");
                    }
                }
                else {
                    info.setText("Two passwords do not match");
                }
            }
        });

        registerGrid.add(new Label("Username:"), 0, 0);
        registerGrid.add(tfUsername, 1, 0);
        registerGrid.add(new Label("Password:"), 0, 1);
        registerGrid.add(tfPassword, 1, 1);
        registerGrid.add(new Label("Confirm:"),0,2);
        registerGrid.add(tfConfirm,1,2);

        HBox btBox = new HBox();
        btBox.setAlignment(Pos.CENTER_LEFT);
        btBox.setSpacing(10);
        btBox.getChildren().add(btCancel);
        btBox.getChildren().add(btConfirm);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20,20,20,20));
        vBox.getChildren().add(registerGrid);
        vBox.getChildren().add(btBox);
        vBox.getChildren().add(info);

        Scene sceneRegister;
        sceneRegister = new Scene(vBox);
        stage.setTitle("Register");
        stage.setScene(sceneRegister);
    }

}