package com.example.rlcsimulator;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class RL implements Initializable {

    public TextField omg;
    public TextField l;
    public TextField v0;
    public TextField nap_v_max;
    public TextField rez_v_max;
    public TextField cew_v_max;
    public TextField pr_v_max;
    public Label nap_v_min;
    public Label rez_v_min;
    public Label cew_v_min;
    public Label pr_v_min;
    public Label vr0;
    public Label vl0;
    public Label i0;
    public Label xl;
    public Label phase;
    @FXML
    public ImageView prad;
    @FXML
    public ImageView rez;
    @FXML
    public ImageView cewka;
    @FXML
    public ImageView nap;
    private Stage stage;
    private Scene scene;
    private Parent root;
    ArrayList<Widok> wykresy = new ArrayList<>();
    ArrayList<ImageView> img_view = new ArrayList<>();

    @FXML
    private Pane paneOne;
    @FXML
    private Label z;
    @FXML
    private TextField res;




    private double R;
    private double C;
    private double L;
    private double frequency;
    private double omega;
    private double Phase;
    private double PhaseInRad;

    private double Vo;
    private double VRo;
    private double VCo;
    private double VLo;
    private double t;
    private double Io;
    private double XL;
    private double Z;


    void calculate() {
        this.omega = this.frequency*2*Math.PI;
        this.XL = (double)Math.round((float)(10.0D * this.omega * this.L)) / 10.0D;
        this.Z = (double)Math.round(10.0D * Math.sqrt(this.R * this.R + (this.XL) * (this.XL)))/10.0D;
        this.PhaseInRad = (double)Math.round(10.0D*(Math.atan((this.XL) / this.R)))/10.0D;
        this.Phase = (double)Math.round(10.0D*(((Math.atan((this.XL) / this.R))/(Math.PI))*180))/10.0D;
        this.Io = this.Vo / this.Z;
        this.VRo = this.Io * this.R;
        this.VLo = this.Io * this.XL;
        phase.setText(Double.toString(Phase));
        xl.setText(Double.toString(XL));
        z.setText(Double.toString(Z));
        if(animation !=null) {
            animation.stop();
            prad_wyk.zmiana_wart(Io, omega, PhaseInRad, Double.parseDouble(pr_v_max.getText()));
            rez_wyk.zmiana_wart(VRo, omega, PhaseInRad, Double.parseDouble(rez_v_max.getText()));
            cew_wyk.zmiana_wart(VLo, omega, PhaseInRad - (Math.PI / 2), Double.parseDouble(cew_v_max.getText()));
            nap_wyk.zmiana_wart(Vo, omega, 0, Double.parseDouble(nap_v_max.getText()));
            animation.start();
        }
    }
    /*
        void drawSourceSignal(Graphics var1) {
            var1.setColor(Color.orange.darker());
            double var2 = this.Vo * Math.sin((this.omega * this.t) + this.phase);
            var1.fillOval(212, 418 - (int)(2.0D * var2) - 3, 6, 6);
            var1.drawLine(212 - (int)(0.5D * var2), 382, 212 + (int)(0.5D * var2), 382);
            var1.drawLine(298 - (int)(0.5D * var2), 382, 298 + (int)(0.5D * var2), 382);
            if (var2 > 0.0D) {
                var1.drawLine(212, 382 - (int)(0.5D * var2), 212, 382 + (int)(0.5D * var2));
            } else {
                var1.drawLine(298, 382 - (int)(0.5D * var2), 298, 382 + (int)(0.5D * var2));
            }

            for(int var4 = 1; var4 < 100; ++var4) {
                double var5 = this.Vo * Math.sin(this.omega * (this.t + 0.08D * (double)var4 / 100.0D) + this.phase);
                var1.drawLine(215 + var4 - 1, 418 - (int)(2.0D * var2), 215 + var4, 418 - (int)(2.0D * var5));
                var2 = var5;
            }

            var1.drawString((double)Math.round((float)(this.Vo * 70.71D)) / 100.0D + " V", 132, 400);
        }
    */
    public void goToCircuitSelect(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("circuitSelect.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    private Widok nap_wyk;
    private Widok prad_wyk;
    private Widok rez_wyk;
    private Widok cew_wyk;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        L = Double.parseDouble(l.getText());
        Vo = Double.parseDouble(v0.getText());
        R = Double.parseDouble(res.getText());
        frequency = Double.parseDouble(omg.getText());
        nap_v_min.setText(nap_v_max.getText());
        cew_v_min.setText(cew_v_max.getText());
        rez_v_min.setText(rez_v_max.getText());
        pr_v_min.setText(pr_v_max.getText());


        this.L= this.L/(1_000);
        this.C= this.C/(1_000_000);
        calculate();
        vr0.setText(Double.toString(Math.round(100.0D*VRo)/100.0D));
        vl0.setText(Double.toString(Math.round(100.0D*VLo)/100.0D));
        i0.setText(Double.toString(Math.round(100.0D*Io)/100.0D));

        //paneOne.setBackground(new Background(new BackgroundFill(Color.web("#FF0000"), CornerRadii.EMPTY, Insets.EMPTY)));

        omg.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d*")){
                    omg.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });
        omg.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:

                        if(omg.getText().isEmpty()){
                            omg.setText("0");
                        }
                        frequency = Double.parseDouble(omg.getText());
                        if(frequency>1000){
                            frequency = 1000;
                            omg.setText("1000");
                        }
                        calculate();
                        vr0.setText(Double.toString(Math.round(100.0D*VRo)/100.0D));
                        vl0.setText(Double.toString(Math.round(100.0D*VLo)/100.0D));
                        i0.setText(Double.toString(Math.round(100.0D*Io)/100.0D));
                        break;
                    default:
                        break;
                }
            }
        });

        l.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d*")){
                    l.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });



        v0.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d*")){
                    v0.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });
        v0.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:

                        if(v0.getText().isEmpty()){
                            v0.setText("0");
                        }
                        Vo = Double.parseDouble(v0.getText());
                        if(Vo>24){
                            Vo = 24;
                            v0.setText("24");
                        }

                        calculate();
                        vr0.setText(Double.toString(Math.round(100.0D*VRo)/100.0D));
                        vl0.setText(Double.toString(Math.round(100.0D*VLo)/100.0D));
                        i0.setText(Double.toString(Math.round(100.0D*Io)/100.0D));
                        break;
                    default:
                        break;
                }
            }
        });


        l.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        if(l.getText().isEmpty()){
                            l.setText("0");
                        }
                        L = Double.parseDouble(l.getText());
                        if(L>20000){
                            L = 20000;
                            l.setText("20000");
                        }
                        if(L<10){
                            L = 10;
                            l.setText("10");
                        }

                        L= L/(1_000);
                        calculate();
                        vr0.setText(Double.toString(Math.round(100.0D*VRo)/100.0D));
                        vl0.setText(Double.toString(Math.round(100.0D*VLo)/100.0D));
                        i0.setText(Double.toString(Math.round(100.0D*Io)/100.0D));
                        break;
                    default:
                        break;
                }
            }
        });
        res.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d*")){
                    res.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });
        res.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        if(res.getText().isEmpty()){
                            res.setText("0");
                        }
                        R = Double.parseDouble(res.getText());
                        if(R>250){
                            R = 250;
                            res.setText("250");
                        }
                        calculate();
                        vr0.setText(Double.toString(Math.round(100.0D*VRo)/100.0D));
                        vl0.setText(Double.toString(Math.round(100.0D*VLo)/100.0D));
                        i0.setText(Double.toString(Math.round(100.0D*Io)/100.0D));
                        break;
                    default:
                        break;
                }
            }
        });
        nap_v_max.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        if(nap_v_max.getText().isEmpty()){
                            nap_v_max.setText("0");
                        }
                        nap_v_min.setText(nap_v_max.getText());
                        calculate();
                        break;
                    default:
                        break;
                }
            }
        });
        rez_v_max.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        if(rez_v_max.getText().isEmpty()){
                            rez_v_max.setText("0");
                        }
                        rez_v_min.setText(rez_v_max.getText());
                        calculate();
                        break;
                    default:
                        break;
                }
            }
        });
        cew_v_max.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        if(cew_v_max.getText().isEmpty()){
                            cew_v_max.setText("0");
                        }
                        cew_v_min.setText(cew_v_max.getText());
                        calculate();
                        break;
                    default:
                        break;
                }
            }
        });

        pr_v_max.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        if(pr_v_max.getText().isEmpty()){
                            pr_v_max.setText("0");
                        }
                        pr_v_min.setText(pr_v_max.getText());
                        calculate();
                        break;
                    default:
                        break;
                }
            }
        });
        this.prad_wyk = new Widok();
        prad_wyk.Stworz_widok(Io, omega, PhaseInRad, Double.parseDouble(pr_v_max.getText()),(int) prad.getFitWidth(), (int) prad.getFitHeight(), Color.red);
        this.rez_wyk = new Widok();
        rez_wyk.Stworz_widok(VRo, omega, PhaseInRad, Double.parseDouble(rez_v_max.getText()),(int) rez.getFitWidth(), (int) rez.getFitHeight(), Color.black);
        this.cew_wyk = new Widok();
        cew_wyk.Stworz_widok(VLo, omega, PhaseInRad-(Math.PI/2), Double.parseDouble(cew_v_max.getText()),(int) cewka.getFitWidth(), (int) cewka.getFitHeight(), Color.blue);
        this.nap_wyk = new Widok();
        nap_wyk.Stworz_widok(Vo, omega, 0, Double.parseDouble(nap_v_max.getText()),(int) nap.getFitWidth(), (int) nap.getFitHeight(), Color.cyan);
        wykresy.add(prad_wyk);
        wykresy.add(rez_wyk);
        wykresy.add(cew_wyk);
        wykresy.add(nap_wyk);
        img_view.add(prad);
        img_view.add(rez);
        img_view.add(cewka);
        img_view.add(nap);
        //animation = new Mythreth(10);
        animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                nextmove();
            }
        };
        animation.start();
    }
    AnimationTimer animation;



    public void nextmove(){
        for(int i = 0; i<wykresy.size(); i++){
            wykresy.get(i).pomaluj();
            img_view.get(i).setImage(SwingFXUtils.toFXImage(wykresy.get(i).pomalowane(), null));

        }
    }




}
