package tests.spheres;

import java.applet.*;
import java.awt.*;
import java.awt.event.KeyAdapter;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.microcrowd.loader.java3d.max3ds.Loader3DS;
import com.sun.j3d.loaders.Loader;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.behaviors.keyboard.*;
import com.sun.j3d.loaders.Scene;


public class Mykeynavbeh extends Applet {

    private BranchGroup objRoot;

    private VirtualUniverse myUniverse = null;
    private Canvas3D canvas = null;
    //private TransformGroup viewtrans = null;

    private TransformGroup tg = new TransformGroup();
    private Transform3D t3d = null;

    private TransformGroup transformGroupSphere = null;
    private Transform3D transform3DSphere = null;

    private double x = 0.0;
    private double y = 0.0;
    private double z = 0.0;
    private int r = 0;


    public Mykeynavbeh() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        canvas = new Canvas3D(config);
        add("Center", canvas);

        addKeyListener(new KeyAdapter() {    //обработка событий клавиатуры
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT) {
                    r = r - 10;
                    t3d.setRotation(new AxisAngle4d(0, 0, 0, Math.toRadians(r)));
//                    t3d.setTranslation(new Vector3f((float) x, (float)y, 0.0f));
                    tg.setTransform(t3d);

                }

            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_LEFT) {
                    r = r + 10;
                    t3d.setRotation(new AxisAngle4d(0, 0, 0, Math.toRadians(r)));
//                    t3d.setTranslation(new Vector3f((float) x, (float)y, 0.0f));
                    tg.setTransform(t3d);
                }

            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
                    //y = y + 0.01;
                    //t3d.rotX(-y * 4);
                    z = z + 0.5;
                    t3d.setTranslation(new Vector3f((float) x, (float) y, (float) z));
                    tg.setTransform(t3d);

                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
//                    y = y - 0.01;
//                    t3d.rotX(-y * 4);
                    z = z - 0.5;
                    t3d.setTranslation(new Vector3f((float) x, (float)y, (float)z));
                    tg.setTransform(t3d);
                }
            }
        });
        setFocusable(true);

        createSceneGraph();
        createSceneGraphForSphere();


        myUniverse = new VirtualUniverse();
        Locale myLocale = new Locale(myUniverse);
        BranchGroup b2 = buildViewBranch();
        //myLocale.addBranchGraph(b2);
        myLocale.addBranchGraph(objRoot);
    }

    protected BranchGroup buildViewBranch() {
        BranchGroup viewBranch = new BranchGroup();
        Transform3D viewXfm = new Transform3D();
        viewXfm.set(new Vector3f(0.0f, 0.0f, 10.0f));
        TransformGroup viewXfmGroup = new TransformGroup(viewXfm);
        ViewPlatform myViewPlatform = new ViewPlatform();
        PhysicalBody myBody = new PhysicalBody();
        PhysicalEnvironment myEnvironment = new PhysicalEnvironment();
        viewXfmGroup.addChild(myViewPlatform);
        viewBranch.addChild(viewXfmGroup);
        View myView = new View();
        myView.addCanvas3D(canvas);
        myView.attachViewPlatform(myViewPlatform);
        myView.setPhysicalBody(myBody);
        myView.setPhysicalEnvironment(myEnvironment);
        return viewBranch;
    }

    public void createSceneGraphForSphere(){
//        transformGroupSphere = new TransformGroup();
//
//        transformGroupSphere.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//
//        objRoot.addChild(transformGroupSphere);
//
//
//        Sphere sphere = new Sphere(0.25f);
//
//        transformGroupSphere = new TransformGroup();
//
//        transformGroupSphere.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//
//        transform3DSphere = new Transform3D();
//
//        transform3DSphere.setTranslation(new Vector3f(0.0f,-0.5f,-1f));
//
//        transformGroupSphere.setTransform(transform3DSphere);
//
//        transformGroupSphere.addChild(sphere);
//
//        objRoot.addChild(transformGroupSphere);
//
//        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
//
//        Color3f light1Color = new Color3f(1.0f, 0.0f, 0.5f);
//
//        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
//
//        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
//
//        light1.setInfluencingBounds(bounds);
//
//        objRoot.addChild(light1);
//
//        // Set up the ambient light
//
//        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
//
//        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
//
//        ambientLightNode.setInfluencingBounds(bounds);
//
//        objRoot.addChild(ambientLightNode);

        objRoot = new BranchGroup();
        transform3DSphere = new Transform3D();
        transform3DSphere.set(new AxisAngle4d(0.0, 0.0, 0.0, Math.PI));
        transformGroupSphere = new TransformGroup(transform3DSphere);
        transformGroupSphere.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(transformGroupSphere);

        TextureLoader loader = new TextureLoader("res/images/venmap.jpg","LUMINANCE", new Container());

        Texture texture = loader.getTexture();

        texture.setBoundaryModeS(Texture.WRAP);

        texture.setBoundaryModeT(Texture.WRAP);

        //texture.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );

        TextureAttributes texAttr = new TextureAttributes();

        texAttr.setTextureMode(TextureAttributes.MODULATE);

        Appearance ap = new Appearance();

        ap.setTexture(texture);

        ap.setTextureAttributes(texAttr);

        //set up the material

        // ap.setMaterial(new Material( white ,black, white, black, 1.0f));

        // Create a ball to demonstrate textures

        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;


        Color3f ambientColour = new Color3f(1.0f, 0.0f, 0.0f);
        Color3f diffuseColour = new Color3f(1.0f, 0.0f, 0.0f);
        Color3f specularColour = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f emissiveColour = new Color3f(0.0f, 0.0f, 0.0f);
        float shininess = 20.0f;
        ap.setMaterial(new Material(ambientColour, emissiveColour, diffuseColour, specularColour, shininess));
        transformGroupSphere.addChild(new Sphere(2.0f, primflags, 120, ap));
        //addLights(objRoot);

    }

    private void createSceneGraph() {
        objRoot = new BranchGroup();

        BoundingSphere bounds = new BoundingSphere(new Point3d(), 10000.0);

        //tg = universe.getViewingPlatform().getViewPlatformTransform();

        /////////////////////////////////////////////////////////////////////

//        KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(tg);
//        keyNavBeh.setSchedulingBounds(bounds);
//        PlatformGeometry platformGeom = new PlatformGeometry();
//        platformGeom.addChild(keyNavBeh);
        //myUniverse.getViewingPlatform().setPlatformGeometry(platformGeom);

        /////////////////////////////////////////////////////////////////////

        objRoot.addChild(createMap());

        Background background = new Background();
        background.setColor(0f, 0.2f, 0.5f);
        background.setApplicationBounds(bounds);
        objRoot.addChild(background);

    }

    private BranchGroup createMap() {

        BranchGroup objRoot = new BranchGroup();

        t3d = new Transform3D();

        t3d.setTranslation(new Vector3d(0.0, 0.0, 0.0));
        t3d.setRotation(new AxisAngle4d(0, 0, 0, Math.toRadians(90)));
        t3d.setScale(0.01);

        tg.setTransform(t3d);

        Loader loader = new Loader3DS();
        Scene s = null;

        try {
            s = loader.load("models/test_map_2.3ds");
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }

        tg.addChild(s.getSceneGroup());
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        //BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);

        objRoot.addChild(tg);
        objRoot.addChild(createLight());
        objRoot.compile();

        return objRoot;
    }

    private Light createLight() {
        DirectionalLight light = new DirectionalLight(true, new Color3f(0.0f, 1.1f, 0.4f), new Vector3f(0f, 0.2f, -1.0f));

        light.setInfluencingBounds(new BoundingSphere(new Point3d(), 10000.0));

        return light;
    }

    public static void main(String[] args) {
        Mykeynavbeh applet = new Mykeynavbeh();
        Frame frame = new MainFrame(applet, 800, 600);
        frame.setTitle("Sphere 3D");
    }
}