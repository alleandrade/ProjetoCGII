/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author sawako
 */
public class Enemy {
    private float x;
    private float y;
    private float z;
    private String carModel;
    private boolean isAlive = true;
    private String name;
    private float velocity;
    
    public Enemy() {
    }

    public Enemy(float x, float y, float z, String carModel, String name, float velocity) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.velocity = velocity;
        
        this.carModel = carModel;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
    
}
