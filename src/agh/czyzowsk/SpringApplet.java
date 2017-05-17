package agh.czyzowsk;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Timer;

public class SpringApplet extends javax.swing.JApplet implements MouseListener, MouseMotionListener, ActionListener, Runnable {
    /**
     *
     */

    //deklaracja pol prywatnych
    private SimTask simTask;
    private SimEngine simEngine;
    private Timer timer;
    Date currentDate = new Date();
    //definicja promienia masy (kula)
    int promien = 40;
    //deklaracja wartosci logicznej przechowujacej stan myszy
    private boolean mouseDragging;
    //deklaracja pol tekstowych
    TextField m, k, c, l, g, tF;
    //deklaracja przycisku reset
    Button reset;
    double getX, getY;
    //polozenie startowe wektora X
    double xX = 340;
    double yY = 180;
    Label mL, kL, cL, lL, gL, tL;

    @Override
    public void init() {

        currentDate.setTime(0);
        Vector2D xz = new Vector2D(475, 30);
        Vector2D x = new Vector2D(xX, yY);
        Vector2D v = new Vector2D(0, 0);
        simEngine = new SimEngine(1, 1, 1, 400, x, v, xz, 10);
        timer = new Timer();
        simTask = new SimTask(simEngine, this, 0.001);
        timer.scheduleAtFixedRate(simTask, currentDate.getTime(), 1);
        //inicjalizacja pola przechowujacego stan myszy
        mouseDragging = false;
        addMouseListener(this);
        addMouseMotionListener(this);

        //inicjalizacja przycisku RESET
        reset = new Button("RESET");
        //inicjalizacja pozostalych pol tekstowych
        m = new TextField(String.valueOf(simEngine.getMasa()));
        k = new TextField(String.valueOf(simEngine.getWspSprezystosci()));
        c = new TextField(String.valueOf(simEngine.getWspTlumienia()));
        l = new TextField(String.valueOf(simEngine.getDluSwobodnaSprezyny()));
        g = new TextField(String.valueOf(simEngine.getPrzyspGrawitacyjne()));
        tF = new TextField(String.valueOf(simTask.getT()));
        mL = new Label("Masa:");
        kL = new Label("Wsp. sprezystosci:");
        cL = new Label("Tlumienie:");
        lL = new Label("Dlugosc swobodna:");
        gL = new Label("Grawitacja:");
        tL = new Label("Tempo:");
        reset.setBackground(Color.RED);

        //dodawanie elementow do apletu (dodawane od lewej zgodnie z kolejnoscia)
        add(mL);
        add(m);
        add(kL);
        add(k);
        add(cL);
        add(c);
        add(lL);
        add(l);
        add(gL);
        add(g);
        add(tL);
        add(tF);
        add(reset);
        reset.addActionListener(this);

        this.setSize(900, 600);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setVisible(true);
    }

    //w celu wyeliminowania migania obrazu zastosowane zostalo podwojne buforowanie
    //obiekt abstrakcyjnej klasy Image
    private Image i;
    //obiekt ktory posluzy za dodatkowy bufor
    private Graphics bufor;

    @Override
    public void paint(Graphics g) {
        //tworzenie obrazu poza ekranem (jest to kopia obrazu wyswietlanego na aplecie), ktory uzyty zostanie do podwojnego buforowania
        i = createImage(this.getWidth(), this.getHeight());
        //tworzenie kopii grafiki poza ekranem i zapisanie jej do obiektu bufor
        bufor = i.getGraphics();
        //wyrysowanie grafiki w obszarze apleta
        paintComponent(bufor);
        //wyrysowanie obrazu
        g.drawImage(i, 0, 0, this);
    }

    public void paintComponent(Graphics g) {
        //ustawienie rozmiaru appletu
        this.setSize(950, 700);

        //zapisanie rozmiaru appletu do zmiennej d
        Dimension d = getSize();

        //okreslenie koloru tla
        g.setColor(getBackground());
        setBackground(Color.BLACK);
        g.fillRect(0, 0, d.width, d.height);

        //naniesienie siatki wspolrzednych
        for (int i = 0; i < d.width; ) {
            g.setColor(Color.DARK_GRAY);
            g.drawLine(i, 0, i, d.height);
            i += 10;
        }
        for (int i = 0; i < d.height; ) {
            g.setColor(Color.DARK_GRAY);
            g.drawLine(0, i, d.width, i);
            i += 10;
        }

        g.setColor(Color.WHITE);

        //liczba zwojow sprezyny
        double z = 80;
        //tangens kata potrzebny do rysowania sprezyny
        double tan = (simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / (simEngine.getPolozenieMasy().getX() - simEngine.getPolozeniePktZawieszenia().getX());
        //rysowanie sprezyny
        for (int i = 0; i < z; i++) {
            if (i != 0 && i != z - 1) {
                g.drawLine((int) simEngine.getPolozeniePktZawieszenia().getY() + (int) ((simEngine.getPolozeniePktZawieszenia().getY() + i * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z) - simEngine.getPolozeniePktZawieszenia().getY()) / tan) + 10, (int) (simEngine.getPolozeniePktZawieszenia().getY() + i * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z)), (int) simEngine.getPolozeniePktZawieszenia().getX() + (int) ((simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z) - simEngine.getPolozeniePktZawieszenia().getY()) / tan) - 10, (int) (simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z)));
                g.drawLine((int) simEngine.getPolozeniePktZawieszenia().getY() + (int) ((simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z) - simEngine.getPolozeniePktZawieszenia().getY()) / tan) - 10, (int) (simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z)), (int) simEngine.getPolozeniePktZawieszenia().getX() + (int) ((simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z) - simEngine.getPolozeniePktZawieszenia().getY()) / tan) + 10, (int) (simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z)));
            } else if (i == 0) {
                g.drawLine((int) simEngine.getPolozeniePktZawieszenia().getY(), (int) (simEngine.getPolozeniePktZawieszenia().getY() + i * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z)), (int) simEngine.getPolozeniePktZawieszenia().getY() + (int) ((simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z) - simEngine.getPolozeniePktZawieszenia().getY()) / tan) - 10, (int) (simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z)));
                g.drawLine((int) simEngine.getPolozeniePktZawieszenia().getY() + (int) ((simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z) - simEngine.getPolozeniePktZawieszenia().getY()) / tan) - 10, (int) (simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z)), (int) simEngine.getPolozeniePktZawieszenia().getX() + (int) ((simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z) - simEngine.getPolozeniePktZawieszenia().getY()) / tan) + 10, (int) (simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z)));
            } else {
                g.drawLine((int) simEngine.getPolozeniePktZawieszenia().getY() + (int) ((simEngine.getPolozeniePktZawieszenia().getY() + i * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z) - simEngine.getPolozeniePktZawieszenia().getY()) / tan) + 10, (int) (simEngine.getPolozeniePktZawieszenia().getY() + i * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z)), (int) simEngine.getPolozeniePktZawieszenia().getX() + (int) ((simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z) - simEngine.getPolozeniePktZawieszenia().getY()) / tan), (int) (simEngine.getPolozeniePktZawieszenia().getY() + (i + 1) * ((simEngine.getPolozenieMasy().getY() - simEngine.getPolozeniePktZawieszenia().getY()) / z)));
            }
        }

        //utwierdzenie masy- linia prosta i kreskowanie
        g.drawLine(0, (int) simEngine.getPolozeniePktZawieszenia().getY(), d.width, (int) simEngine.getPolozeniePktZawieszenia().getY());
        for (int i = 0; i < d.width; i += (d.width) / 80) {
            g.drawLine(i, (int) simEngine.getPolozeniePktZawieszenia().getY(), i + (d.width) / 80, 0);
        }

        //rysowanie masy
        g.drawOval((int) simEngine.getPolozenieMasy().getX() - promien, (int) simEngine.getPolozenieMasy().getY(), 2 * promien, 2 * promien);
        //wypisaine czasu
        g.drawString("Czas: " + simEngine.getCzas() / 1000 + "s.", 10, 50);

        //wysokosc grotu wektora
        int grot = 15;
        //tablica zawierajaca wektory, ktore nastepnie beda rysowane
        Vector2D[] wektory = {simEngine.getSilaWypadkowa(), simEngine.getSilaSprezyny(), simEngine.getSilaTlumika(),
                simEngine.getSilaGrawitacji(), simEngine.getPredkoscMasy()};

        //tablica zawierajaca kolory wektorow
        Color[] kolory = {Color.red, Color.blue, Color.magenta, Color.green, Color.yellow};

        //rysowanie wektorow
        for (int i = 0; i < wektory.length; i++) {

            g.setColor(kolory[i]);
            //kat rozwarcia grotow
            double alfa = 15;

            //wartosci uzywane do okreslenia polozenia grotow wektorow
            double x1prim = Math.cos(Math.toRadians(alfa)) * Math.abs(wektory[i].normalizedVector().multipOfVector(grot).getX()) - Math.sin(Math.toRadians(alfa)) * Math.abs(wektory[i].normalizedVector().multipOfVector(grot).getY());
            double y1prim = Math.sin(Math.toRadians(alfa)) * Math.abs(wektory[i].normalizedVector().multipOfVector(grot).getX()) + Math.cos(Math.toRadians(alfa)) * Math.abs(wektory[i].normalizedVector().multipOfVector(grot).getY());
            double x2prim = Math.cos(Math.toRadians(-alfa)) * Math.abs(wektory[i].normalizedVector().multipOfVector(grot).getX()) - Math.sin(Math.toRadians(-alfa)) * Math.abs(wektory[i].normalizedVector().multipOfVector(grot).getY());
            double y2prim = Math.sin(Math.toRadians(-alfa)) * Math.abs(wektory[i].normalizedVector().multipOfVector(grot).getX()) + Math.cos(Math.toRadians(-alfa)) * Math.abs(wektory[i].normalizedVector().multipOfVector(grot).getY());

            g.drawLine((int) simEngine.getPolozenieMasy().getX(), (int) simEngine.getPolozenieMasy().getY() + promien, (int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX(), (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien);
            //warunek if stworzony w celu okreslenia zwrotu wektora (wskazanie grota strzalki)
            if (wektory[i].getY() >= 0 && wektory[i].getX() <= 0) {
                g.drawLine((int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX(), (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien, (int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX() + (int) x1prim, (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien - (int) y1prim);
                g.drawLine((int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX(), (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien, (int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX() + (int) x2prim, (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien - (int) y2prim);
            } else if (wektory[i].getY() <= 0 && wektory[i].getX() >= 0) {
                g.drawLine((int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX(), (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien, (int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX() - (int) x1prim, (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien + (int) y1prim);
                g.drawLine((int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX(), (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien, (int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX() - (int) x2prim, (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien + (int) y2prim);
            } else if (wektory[i].getY() >= 0 && wektory[i].getX() >= 0) {
                g.drawLine((int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX(), (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien, (int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX() - (int) x1prim, (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien - (int) y1prim);
                g.drawLine((int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX(), (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien, (int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX() - (int) x2prim, (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien - (int) y2prim);
            } else if (wektory[i].getY() <= 0 && wektory[i].getX() <= 0) {
                g.drawLine((int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX(), (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien, (int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX() + (int) x1prim, (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien + (int) y1prim);
                g.drawLine((int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX(), (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien, (int) simEngine.getPolozenieMasy().getX() + (int) wektory[i].getX() + (int) x2prim, (int) simEngine.getPolozenieMasy().getY() + (int) wektory[i].getY() + promien + (int) y2prim);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == reset) {
//            timer.cancel();
//            Vector2D nowyWektor = new Vector2D(xX, yY);
//            //resetowanie predkosci
//            simEngine.reset();
//            //inicjalizacja nowych wartosci obiektu simEngine
//            simEngine.setX(nowyWektor);
//            simEngine.setM(Double.parseDouble(m.getText()));
//            simEngine.setG(Double.parseDouble(g.getText()));
//            simEngine.setK(Double.parseDouble(k.getText()));
//            simEngine.setC(Double.parseDouble(c.getText()));
//            simEngine.setL(Double.parseDouble(l.getText()));
//            simTask.setT(Double.parseDouble(tF.getText()));
//            simEngine.t = 0;
//            this.repaint();
//        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseDragging == true) {
            //ustawienie pozycji masy obiektu simEngine zgodnie z pozycja kursora
            simEngine.getPolozenieMasy().setX(e.getX());
            simEngine.getPolozenieMasy().setY(e.getY());
            this.repaint();
            e.consume();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //odczytanie pozycji kursora
        getX = e.getX();
        getY = e.getY();
        //warunek na to czy pozycja kursora znajduje sie w obrebie powierzchni masy
        if ((getX - simEngine.getPolozenieMasy().getX()) * (getX - simEngine.getPolozenieMasy().getX()) + (getY - simEngine.getPolozenieMasy().getY() - promien) * (getY - simEngine.getPolozenieMasy().getY() - promien) <= promien * promien) {
            simTask.cancel();
            simEngine.reset();
            mouseDragging = true;
            e.consume();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mouseDragging == true) {
            Timer newTimer = new Timer();
            simTask = new SimTask(simEngine, this, simTask.getT());
            newTimer.scheduleAtFixedRate(simTask, currentDate.getTime(), 1);
            mouseDragging = false;
            e.consume();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }
}