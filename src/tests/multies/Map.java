package tests.multies;


import com.microcrowd.loader.java3d.max3ds.Loader3DS;
import com.sun.j3d.loaders.Loader;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyListener;

public class Map extends Applet //implements KeyListener
{
    private TransformGroup tg = new TransformGroup();
    private Transform3D t3d = new Transform3D();
    private SimpleUniverse myUniverse;

    public Map(){

    }

//    protected BranchGroup buildViewBranch() {
//        BranchGroup viewBranch = new BranchGroup();
//        Transform3D viewXfm = new Transform3D();
//        viewXfm.set(new Vector3f(0.0f, 0.0f, 10.0f));
//        TransformGroup viewXfmGroup = new TransformGroup(viewXfm);
//        ViewPlatform myViewPlatform = new ViewPlatform();
//        PhysicalBody myBody = new PhysicalBody();
//        PhysicalEnvironment myEnvironment = new PhysicalEnvironment();
//        viewXfmGroup.addChild(myViewPlatform);
//        viewBranch.addChild(viewXfmGroup);
//        View myView = new View();
//        myView.addCanvas3D(canvas);
//        myView.attachViewPlatform(myViewPlatform);
//        myView.setPhysicalBody(myBody);
//        myView.setPhysicalEnvironment(myEnvironment);
//        return viewBranch;
//    }

    public SimpleUniverse getSimpleUniverse(Canvas3D canvass){
        myUniverse = new SimpleUniverse(canvass);
        return myUniverse;
    }

    public BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();

        BoundingSphere bounds = new BoundingSphere(new Point3d(), 10000.0);

        //tg = universe.getViewingPlatform().getViewPlatformTransform();

        /////////////////////////////////////////////////////////////////////

        KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(tg);
        keyNavBeh.setSchedulingBounds(bounds);
        PlatformGeometry platformGeom = new PlatformGeometry();
        platformGeom.addChild(keyNavBeh);
        myUniverse.getViewingPlatform().setPlatformGeometry(platformGeom);

        /////////////////////////////////////////////////////////////////////

        objRoot.addChild(createMap());

        Background background = new Background();
        background.setColor(0f, 0.2f, 0.5f);
        background.setApplicationBounds(bounds);
        objRoot.addChild(background);

        return objRoot;
    }

    private BranchGroup createMap() {

        BranchGroup objRoot = new BranchGroup();

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
}
