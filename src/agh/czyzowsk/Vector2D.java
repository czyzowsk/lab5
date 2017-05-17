package agh.czyzowsk;

/**
 * Created by Damian on 2017-03-30.
 */
public class Vector2D {
    private double x, y;
    private int lenthOfVector;

    Vector2D(){
        x = 0;
        y = 0;
    }

    Vector2D(double newX, double newY){
        x = newX;
        y = newY;
    }

    public Vector2D sumWithVector(Vector2D vect){
        return new Vector2D(vect.getX() +x,vect.getY() +y);
    }

    public Vector2D diffOfVector(Vector2D vect){
        return new Vector2D(vect.getX() - x,vect.getY() - y);
    }

    public Vector2D multipOfVector(double a){
        return  new Vector2D(a*getX(), a*getY());
    }


    public int lenthOfVector(){
        lenthOfVector = (int)Math.sqrt(getX()*getX() + getY()*getY());
        return lenthOfVector;
    }

    public Vector2D normalizedVector(){
        double versorX = getX()/lenthOfVector();
        double versorY = getY()/lenthOfVector();

        return new Vector2D(versorX, versorY);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }
}
