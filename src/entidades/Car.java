/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Alex
 */
public class Car extends SimpleApplication {
    private Box box;
    private Spatial spatial;

    public void createCar(Node nodeCarBody, Node nodeCarWheels ) {
        Box b = new Box(2, 1.5f, 1);
        Geometry geom = new Geometry("Box", b);

        //Corpo carro
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        nodeCarBody.attachChild(geom);

        b = new Box(1.2f, 1, 1);
        geom = new Geometry("Box", b);

        //Laterais carro
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        geom.move(-3.2f, -0.5f, 0);
        geom.setMaterial(mat);
        nodeCarBody.attachChild(geom);

        b = new Box(1.2f, 1, 1);
        geom = new Geometry("Box", b);

        //Laterais carro
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        geom.move(3.2f, -0.5f, 0);
        geom.setMaterial(mat);
        nodeCarBody.attachChild(geom);

        b = new Box(0.6f, 0.5f, 0.2f);
        geom = new Geometry("Roda1", b);

        //Roda
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        geom.move(3.2f, -1.5f, 1);
        geom.setMaterial(mat);
        nodeCarWheels.attachChild(geom);

        b = new Box(0.6f, 0.5f, 0.2f);
        geom = new Geometry("Roda2", b);

        //Roda
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        geom.move(3.2f, -1.5f, -1);
        geom.setMaterial(mat);
        nodeCarWheels.attachChild(geom);

        b = new Box(0.6f, 0.5f, 0.2f);
        geom = new Geometry("Roda3", b);

        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        geom.move(-3.2f, -1.5f, -1);
        geom.setMaterial(mat);
        nodeCarWheels.attachChild(geom);

        b = new Box(0.6f, 0.5f, 0.2f);
        geom = new Geometry("Roda4", b);

        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        geom.move(-3.2f, -1.5f, 1);
        geom.setMaterial(mat);
        nodeCarWheels.attachChild(geom);    }

    @Override
    public void simpleInitApp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
