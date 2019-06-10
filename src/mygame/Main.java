package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import entidades.Car;
import entidades.Enemy;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    Node car = new Node();
    Node chasis = new Node();
    Node rodas = new Node();
    Node allEnemies = new Node();
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    boolean IsRunning = true;
    boolean isRotateRight = false;
    boolean isRotateLeft = false;
    Quaternion orig = new Quaternion();
    Car player = new Car(0,0,0,"Models/Ferrari/Car.mesh.xml","Player", 0.01f);
    long enemyTime = System.currentTimeMillis();
    int enemyNumber = 0;
       /**
     * Use ActionListener to respond to pressed/released inputs (key presses,
     * mouse clicks)
     */
    
    public void initKeys() {
        inputManager.addMapping("Accelerate",
                new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("Right",
                new KeyTrigger(KeyInput.KEY_L));
        inputManager.addMapping("Left",
                new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Re",
                new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("BuildCar",
                new KeyTrigger(KeyInput.KEY_N));
        inputManager.addMapping("Pause",
                new KeyTrigger(KeyInput.KEY_P));



        inputManager.addListener(analogListener,
                new String[]{"Accelerate", "Right", "Left", "Re", "Pause", "BuildCar"});
    }

    private boolean isRunning = true;
    //Usado para operações do tipo on/off 

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed,
                float tpf) {
            if (name.equals("Pause") && !keyPressed) {
                isRunning = !isRunning;

            }
        }
    };
    float valRotate = 0;

    //Usar para operações que são contínuas dentro da aplicação (navegação) 
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            Spatial modelo;
            if (isRunning) {
                if (name.equals("Accelerate")) {              
                    car.setLocalTranslation(car.getLocalTranslation().x, 0, car.getLocalTranslation().z - player.getVelocity());
                }
                
                else if (name.equals("Re")) {
                    car.setLocalTranslation(car.getLocalTranslation().x, 0, car.getLocalTranslation().z + player.getVelocity());
                }
                
                if (name.equals("Right") && car.getLocalTranslation().x + player.getVelocity() < 8) {
                    car.setLocalTranslation(car.getLocalTranslation().x + player.getVelocity(), 0, car.getLocalTranslation().z);
                    System.out.println("Direita " + car.getLocalTranslation().x);
                }
                
                else if (name.equals("Left") && car.getLocalTranslation().x - player.getVelocity() > -8) {
                    car.setLocalTranslation(car.getLocalTranslation().x - player.getVelocity(), 0, car.getLocalTranslation().z);
                    System.out.println("Esquerda " + car.getLocalTranslation().x);
                }

                if (name.equals("BuildCar")) {
                        car.detachAllChildren();
                    
                    buildCar();
                }
                
                if (name.equals("Pause")) {
                    isRunning = false;
                }
            } else {
                System.out.println("Press P to unpause.");
                if (name.equals("Pause")) {
                    isRunning = true;
                }
            }
        }
    };

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
        
    }
    
    @Override
    public void simpleInitApp() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        rootNode.attachChild(allEnemies);
        
        buildCar();
        buildScenario();
        initKeys();
        
        rootNode.setLocalTranslation(0, -5, -10);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        generateEnemy();
        for (Enemy enmy : enemies) {
            allEnemies.getChild(enmy.getName()).setLocalTranslation(enmy.getX(), enmy.getY(), allEnemies.getChild(enmy.getName()).getLocalTranslation().z + enmy.getVelocity());
        }
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public void buildCar() { 
        Spatial roda = assetManager.loadModel(player.getCarModel());
        roda.setLocalTranslation(player.getX(), player.getY(), player.getZ());
        roda.setName(player.getName());
        
        for(int i = 0 ; i < 4 ; i++) {
            
            if (i == 0) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelBackLeft.mesh.xml");
                roda2.setName("Wheel0");
                
                roda2.setLocalTranslation(-roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, -roda.getLocalTranslation().z / 2);
                rodas.attachChild(roda2);
            }
            if (i == 1) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelBackRight.mesh.xml");
                roda2.setName("Wheel1");
                roda2.setLocalTranslation(-roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, roda.getLocalTranslation().z / 2);
                rodas.attachChild(roda2);
            }

            if (i == 2) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelFrontLeft.mesh.xml");
                roda2.setName("Wheel2");
                roda2.setLocalTranslation(roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, -roda.getLocalTranslation().z / 2);
                rodas.attachChild(roda2);
            }

            if (i == 3) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelFrontRight.mesh.xml");
                roda2.setName("Wheel3");
                roda2.setLocalTranslation(roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, roda.getLocalTranslation().z / 2);
                rodas.attachChild(roda2);
            }
        }
        orig = car.getLocalRotation().clone();
        chasis.attachChild(roda);
        car.attachChild(chasis);
        car.attachChild(rodas);
        rootNode.attachChild(car);
    }
    
    public void buildScenario () {
        Box b = new Box(500, 300, 0.001f);
        Geometry geom = new Geometry("Box", b);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/night.jpg"));

        geom.setMaterial(mat);
        geom.move(0, 70, -800);
        rootNode.attachChild(geom);
        
        b = new Box(50, 150, 0.001f);
        geom = new Geometry("Box", b);

        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/road.png"));
        
        geom.setMaterial(mat);
        geom.move(0, -20, -100);
        geom.rotate((float) (Math.PI/2),(float) -Math.PI, 0);
        rootNode.attachChild(geom);
    }
    
    public boolean collisionDetect (Spatial a, Spatial b) {
        boolean ret = false;
        
        
        return ret;
    }
    
    public void generateEnemy () {
        Random rnd = new Random();
        int valAux = rnd.nextInt(8);
        Enemy e;
        Node enemyWheel = new Node();
        Node enemyCar = new Node();
        Node enemyChasis = new Node();
        
        if (rnd.nextInt(2) == 0) {
            valAux *= -1;
        }
        
        if (System.currentTimeMillis() >= enemyTime) {
            if (valAux % 2 == 0) {
                e = new Enemy(valAux, 0, -150, "Models/Ferrari/Car.mesh.xml", "Enemy"+enemyNumber, 0.1f);
            }
            else {
                e = new Enemy(valAux, 0, -150, "Models/Ferrari/Car.mesh.xml", "Enemy"+enemyNumber, 0.4f);               
            }
            System.out.println("X = " + valAux);
            System.out.println("Gerou inimigo");
            enemyTime += 2500;
            enemyNumber++;
            enemies.add(e);
            
            //Gerando carro inimigo
            Spatial roda = assetManager.loadModel(e.getCarModel());
            roda.setLocalTranslation(e.getX(), e.getY(), e.getZ());
            roda.setName(e.getName());

            for (int i = 0; i < 4; i++) {

                if (i == 0) {
                    Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelBackLeft.mesh.xml");
                    roda2.setName("Wheel0");

                    roda2.setLocalTranslation(e.getX() - 0.15f, -e.getY() / 2, e.getZ() + 0.15f);
                    enemyWheel.attachChild(roda2);
                }
                if (i == 1) {
                    Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelBackRight.mesh.xml");
                    roda2.setName("Wheel1");
                    roda2.setLocalTranslation(e.getX() + 0.15f, -e.getY() / 2, e.getZ() + 0.15f);
                    enemyWheel.attachChild(roda2);
                }

                if (i == 2) {
                    Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelFrontLeft.mesh.xml");
                    roda2.setName("Wheel2");
                    roda2.setLocalTranslation(e.getX() - 0.15f, -e.getY() / 2, e.getZ() - 0.15f);
                    enemyWheel.attachChild(roda2);
                }

                if (i == 3) {
                    Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelFrontRight.mesh.xml");
                    roda2.setName("Wheel3");
                    roda2.setLocalTranslation(e.getX() + 0.15f, -roda.getLocalTranslation().y / 2, e.getZ() - 0.15f);
                    enemyWheel.attachChild(roda2);
                }
            }
            orig = car.getLocalRotation().clone();
            enemyChasis.attachChild(roda);
            enemyCar.attachChild(enemyChasis);
            enemyCar.attachChild(enemyWheel);
            enemyCar.setName(e.getName());
            allEnemies.attachChild(enemyCar);
        }
        
    }
}
