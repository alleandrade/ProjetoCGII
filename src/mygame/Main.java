package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
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

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    Node car = new Node();
    Node chasis = new Node();
    Node rodas = new Node();
    boolean IsRunning = true;
    boolean isRotateRight = false;
    boolean isRotateLeft = false;
    Quaternion orig = new Quaternion();
    
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

        inputManager.addListener(actionListener,
                new String[]{"Pause"});

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
                    System.out.println("entrou");
                    //rootNode.rotate(0, valRotate, 0);
                    
                    car.move(0, 0, -0.01f);
                }
                
                else if (name.equals("Re")) {
                    //rootNode.rotate(0, valRotate, 0);
                    car.move(0, 0, 0.01f);
                }
                
                if (name.equals("Right") && !isRotateRight) {
                    if (valRotate == 0)
                        valRotate += -Math.PI/10f;
                    
                    car.rotate(0, valRotate, 0);
                    isRotateRight = true;
                }
                
                else if (name.equals("Left") && !isRotateLeft) {
                    if (valRotate == 0)
                        valRotate += Math.PI + 10;
                    
                    car.rotate(0, valRotate, 0);
                    isRotateLeft = true;
                }
                else {
                    if (!name.equals("Right") && !name.equals("Left") ) {
                        car.setLocalRotation(orig);
                        valRotate = 0;
                        isRotateLeft = false;
                        isRotateRight = false;
                    }
                }
                if (name.equals("BuildCar")) {
                        car.detachAllChildren();
                    
                    buildCar();
                }
            } else {
                System.out.println("Press P to unpause.");
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
        
        //buildCar();
        buildScenario();
        initKeys();
        
        rootNode.setLocalTranslation(0, -5, -10);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public void buildCar() {
        Spatial roda = assetManager.loadModel("Models/Ferrari/Car.mesh.xml");
        roda.setName("Car1");
        
        for(int i = 0 ; i < 4 ; i++) {
            
            if (i == 0) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelBackLeft.mesh.xml");
                roda2.setName("Wheel0");
                
                roda2.move(-roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, -roda.getLocalTranslation().z / 2);
                rodas.attachChild(roda2);
            }
            if (i == 1) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelBackRight.mesh.xml");
                roda2.setName("Wheel1");
                roda2.move(-roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, roda.getLocalTranslation().z / 2);
                rodas.attachChild(roda2);
            }

            if (i == 2) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelFrontLeft.mesh.xml");
                roda2.setName("Wheel2");
                roda2.move(roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, -roda.getLocalTranslation().z / 2);
                rodas.attachChild(roda2);
            }

            if (i == 3) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelFrontRight.mesh.xml");
                roda2.setName("Wheel3");
                roda2.move(roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, roda.getLocalTranslation().z / 2);
                rodas.attachChild(roda2);
            }
        }
        orig = car.getLocalRotation().clone();
        //Material mat = new Material(assetManager,"Models/Jeep_Renegade_2016_obj/car_jeep_ren.jpg");
        chasis.attachChild(roda);
        car.attachChild(chasis);
        car.attachChild(rodas);
        rootNode.attachChild(car);
    }
    
    public void buildScenario () {
        Box b = new Box(720, 720, 0.001f);
        Geometry geom = new Geometry("Box", b);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/scenario.png"));

        geom.setMaterial(mat);
        geom.move(0, 350, -800);
        rootNode.attachChild(geom);
        
        b = new Box(720, 1500, 0.001f);
        geom = new Geometry("Box", b);

        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/floor.png"));
        
        geom.setMaterial(mat);
        geom.move(0, -20, -800);
        geom.rotate((float) (Math.PI/2), 0, 0);
        rootNode.attachChild(geom);
    }
}
