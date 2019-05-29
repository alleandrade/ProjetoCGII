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
    boolean IsRunning = true;
    
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

        inputManager.addMapping("Rotate",
                 new KeyTrigger(KeyInput.KEY_SPACE),
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        inputManager.addListener(actionListener,
                new String[]{"Pause"});

        inputManager.addListener(analogListener,
                new String[]{"Accelerate", "Right", "Left", "Re", "Pause"});
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
                    car.rotate(0, valRotate, 0);
                    car.move(0, 0, -0.01f);
                }
                if (name.equals("Right")) {
                    System.out.println(".onAnalog()");
                }
                if (name.equals("Left")) {
                    
                }
                if (name.equals("Re")) {
                    car.move(0, 0, 0.01f);


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
        Box b = new Box(0.1f, 15, 0.1f);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        //Spatial teapot = assetManager.loadModel("Models/Ferrari/Car.mesh.xml");
        Spatial roda = assetManager.loadModel("Models/Ferrari/Car.mesh.xml");
        
        for(int i = 0 ; i < 4 ; i++) {
            
            if (i == 0) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelBackLeft.mesh.xml");

                roda2.move(-roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, -roda.getLocalTranslation().z / 2);
                car.attachChild(roda2);
            }
            if (i == 1) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelBackRight.mesh.xml");
                
                roda2.move(-roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, roda.getLocalTranslation().z / 2);
                car.attachChild(roda2);
            }

            if (i == 2) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelFrontLeft.mesh.xml");

                roda2.move(roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, -roda.getLocalTranslation().z / 2);
                car.attachChild(roda2);
            }

            if (i == 3) {
                Spatial roda2 = assetManager.loadModel("Models/Ferrari/WheelFrontRight.mesh.xml");

                roda2.move(roda.getLocalTranslation().x / 2, -roda.getLocalTranslation().y / 2, roda.getLocalTranslation().z / 2);
                car.attachChild(roda2);
            }
        }
        //Material mat = new Material(assetManager,"Models/Jeep_Renegade_2016_obj/car_jeep_ren.jpg");
        car.attachChild(roda);
        rootNode.attachChild(car);
        
        initKeys();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
