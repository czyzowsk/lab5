package agh.czyzowsk;

/**
 * Created by Damian on 2017-05-17.
 */

/**
 * Created by Damian on 2017-03-29.
 */

public class SimEngine {

    double masa, wspSprezystosci, wspTlumienia, dluSwobodnaSprezyny, przyspGrawitacyjne, czas;
    Vector2D polozenieMasy, predkoscMasy, polozeniePktZawieszenia, silaWypadkowa,silaTlumika, silaSprezyny,
            silaGrawitacji;

    SimEngine(double m, double k, double c, double l, Vector2D xp, Vector2D v, Vector2D xpz, double g){
        masa = m;
        wspSprezystosci = k;
        wspTlumienia = c;
        dluSwobodnaSprezyny = l;
        polozenieMasy = xp;
        predkoscMasy = v;
        polozeniePktZawieszenia = xpz;
        przyspGrawitacyjne = g;
        czas = 0;
    }

    public void obliczenia(double krok_czasowy){

        silaGrawitacji = new Vector2D(0, masa*przyspGrawitacyjne);

        silaSprezyny = polozeniePktZawieszenia
                .diffOfVector(polozenieMasy)
                .normalizedVector()
                .multipOfVector(wspSprezystosci*(polozenieMasy
                        .diffOfVector(polozeniePktZawieszenia)
                        .lenthOfVector()- dluSwobodnaSprezyny));

        silaTlumika = predkoscMasy.multipOfVector(-wspTlumienia);


        silaWypadkowa = silaGrawitacji.sumWithVector(silaSprezyny).sumWithVector(silaTlumika);

        if(polozenieMasy.getY() <= polozeniePktZawieszenia.getY()){
            predkoscMasy.setY(-predkoscMasy.getY());
        }

        czas += krok_czasowy;
        predkoscMasy = predkoscMasy.sumWithVector(silaWypadkowa.multipOfVector(krok_czasowy/masa));
        polozenieMasy=polozenieMasy.sumWithVector(predkoscMasy.multipOfVector(krok_czasowy));

    }

    public double getDluSwobodnaSprezyny() {
        return dluSwobodnaSprezyny;
    }

    public double getCzas() {
        return czas;
    }

    public double getMasa() {
        return masa;
    }

    public double getPrzyspGrawitacyjne() {
        return przyspGrawitacyjne;
    }

    public double getWspSprezystosci() {
        return wspSprezystosci;
    }

    public double getWspTlumienia() {
        return wspTlumienia;
    }

    public Vector2D getPolozenieMasy() {
        return polozenieMasy;
    }

    public Vector2D getPolozeniePktZawieszenia() {
        return polozeniePktZawieszenia;
    }

    public Vector2D getPredkoscMasy() {
        return predkoscMasy;
    }

    public Vector2D getSilaTlumika() {
        return silaTlumika;
    }

    public Vector2D getSilaWypadkowa() {
        return silaWypadkowa;
    }

    public Vector2D getSilaGrawitacji() {
        return silaGrawitacji;
    }

    public Vector2D getSilaSprezyny() {
        return silaSprezyny;
    }

    public void reset(){
        getPredkoscMasy().setX(0);
        getPredkoscMasy().setY(0);
    }
}