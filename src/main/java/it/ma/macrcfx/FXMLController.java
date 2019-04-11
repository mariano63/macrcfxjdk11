package it.ma.macrcfx;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class FXMLController implements Initializable {
    
    @FXML
    private Label labelSHA1;
    @FXML
    private Label labelMD5;
    @FXML
    private Label labelFile;
    @FXML
    private Label labelSHA256;
    @FXML
    private Label labelSHA512;
    @FXML
    private CheckBox cbMD5;
    @FXML
    private CheckBox cbSHA1;
    @FXML
    private CheckBox cbSHA256;
    @FXML
    private CheckBox cbSHA512;
    @FXML
    private GridPane gridPane1;
    @FXML
    private Button buttonChooseFile;
    @FXML
    private TextField textCheck;
    @FXML
    private MenuItem copyCheckSHA512;
    @FXML
    private MenuItem copyCheckSHA256;
    @FXML
    private MenuItem copyCheckSHA1;
    @FXML
    private MenuItem copyCheckMD5;
    @FXML
    private Button buttonCheck;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    private void calculateCrcCommons(File file) {
        String hdigestMD5;
        String hdigestSHA1;
        String hdigestSHA256;
        String hdigestSHA512;
        try {
            hdigestMD5 = new DigestUtils(MessageDigestAlgorithms.MD5).digestAsHex(file);
            hdigestSHA1 = new DigestUtils(MessageDigestAlgorithms.SHA_1).digestAsHex(file);
            hdigestSHA256 = new DigestUtils(MessageDigestAlgorithms.SHA_256).digestAsHex(file);
            hdigestSHA512 = new DigestUtils(MessageDigestAlgorithms.SHA_512).digestAsHex(file);
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        labelMD5.setText(hdigestMD5);
        labelSHA1.setText(hdigestSHA1);
        labelSHA256.setText(hdigestSHA256);
        labelSHA512.setText(hdigestSHA512);
    }

    @FXML
    private void onActionbuttonChooseFile(ActionEvent event) {
        String filePath = (String)
                MainApp.PROP.getOrDefault("filePath", System.getProperty("user.dir"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File forInitialDirectory = new File(filePath);
        if (forInitialDirectory.exists())
            fileChooser.setInitialDirectory(forInitialDirectory);
        File file = fileChooser.showOpenDialog((Stage)labelFile.getScene().getWindow());
        if (file != null){
            labelFile.setText(file.getAbsolutePath());
            Object put = MainApp.PROP.put("filePath", file.getParent());
            Logger.getLogger(FXMLController.class.getName()).log(Level.INFO, "filepath={0}", put);
            calculateCrcCommons(file);
        }
    }

    @FXML
    private void OncopyCheck(ActionEvent event) {
        String text = ((MenuItem)event.getSource()).getId();
        switch(text){
            case "copyCheckMD5":
                textCheck.setText(labelMD5.getText());
                break;
            case "copyCheckSHA1":
                textCheck.setText(labelSHA1.getText());
                break;
            case "copyCheckSHA256":
                textCheck.setText(labelSHA256.getText());
                break;
            case "copyCheckSHA512":
                textCheck.setText(labelSHA512.getText());
                break;
        }
    }

    @FXML
    private void onButtonCheck(ActionEvent event) {
        cbMD5.setSelected(textCheck.getText().equalsIgnoreCase(labelMD5.getText()));
        cbSHA1.setSelected(textCheck.getText().equalsIgnoreCase(labelSHA1.getText()));
        cbSHA256.setSelected(textCheck.getText().equalsIgnoreCase(labelSHA256.getText()));
        cbSHA512.setSelected(textCheck.getText().equalsIgnoreCase(labelSHA512.getText()));
    }
}
