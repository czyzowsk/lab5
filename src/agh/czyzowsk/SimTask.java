package agh.czyzowsk;

import java.util.TimerTask;

/**
 * Created by Damian on 2017-03-29.
 */
public class SimTask extends TimerTask {

    //deklaracja pol prywatnych
    private SimEngine simEngine;
    private SpringApplet springApplet;
    double krok_czasowy;

    //konstruktor z parametrami
    public SimTask(SimEngine simEngine, SpringApplet springApplet, double krok_czasowy){
        this.simEngine=simEngine;
        this.springApplet=springApplet;
        this.krok_czasowy=krok_czasowy;
    }

    public double getT(){return krok_czasowy;}
    public void setT(double krok_czasowy){this.krok_czasowy=krok_czasowy;}

    //przesloniecie metody abstrakcyjnej run
    public void run(){
        simEngine.obliczenia(this.getT());
        springApplet.repaint();
    }
}
