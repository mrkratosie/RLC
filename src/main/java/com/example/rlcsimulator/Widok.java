package com.example.rlcsimulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Widok { //extends JPanel{

    private int x;
    private int y;
    private  BufferedImage obrazek;
    private int czas;
    private double frequency;
    private double omega;
    private double phase;
    private double Vo;
    private double Vo_max;

    private double procent;
    private Color color;

    public Widok(){
        
    }
    public void Stworz_widok(double Amplituda,double omega,double faza, double Vo_max, int x,int y, Color color){
        this.color = color;
        this.Vo_max= Vo_max;
        this.omega = omega;
        this.phase = faza;
        this.Vo = Amplituda;
        this.x = x;
        this.y = y;
        this.czas = 0;
        this.obrazek = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB );
        pol_proc();
        pomaluj();
    }
    public BufferedImage pomalowane(){
        return obrazek;
    }
    private int oblicz(int i){
        /*
        double var2 = this.Vo * Math.sin((10 * (this.czas+i)/100) + this.phase);
        System.out.println(var2);
        var2 = (((var2/Vo)*y)/2)+(y/2);
        System.out.println(var2);
        return (int) var2;
         */
        double var2 = procent*(y/2 * Math.sin((((this.omega/(100*Math.PI))*((2*Math.PI)/x)) * (this.czas+i)) - this.phase)) +y/2;
        return (int) var2;

    }
    private void pol_proc(){
        if(Vo > Vo_max){
            procent = Vo/Vo_max;
        }else{
            procent = Vo/Vo_max;
        }
    }

    public void pomaluj(){  //sama sie urzywa
        czas++;
        Graphics2D g2D = pomalowane().createGraphics();
        g2D.setStroke(new BasicStroke(2));
        g2D.setColor(Color.white);
        g2D.setBackground(Color.WHITE);
        g2D.fillRect(0, 0, x, y);
        g2D.setColor(color);
        int ostatni = oblicz(0);
        int nowy;
        for (int i =1 ;i<x;i++){
            nowy = oblicz(i);
            g2D.drawLine(i - 1, ostatni,i, nowy);
            ostatni = nowy;
        }

    }
    public void zmiana_wart(double Amplituda,double omega,double faza, double v0_max){
        this.Vo_max = v0_max;
        this.Vo = Amplituda;
        this.omega = omega;
        this.phase = faza;
        this.czas = 0;
        pol_proc();
    }

}
