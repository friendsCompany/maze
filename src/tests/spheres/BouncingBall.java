package tests.spheres;


import java.applet.Applet;

import java.awt.*;

import java.awt.event.*;

import com.sun.j3d.utils.applet.MainFrame;

import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;

import javax.swing.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.Sphere;


public class BouncingBall extends Applet //implements ActionListener
{
    BranchGroup objRoot;

    private TransformGroup objTrans1;
    private TransformGroup objTrans2;

    private Transform3D trans = new Transform3D();

    private float height=0.0f;

    private float sign = 1.0f; // going up or down

    private Timer timer;

    private float xloc=0.0f;

    private double i = -1.0;



    public void createSceneGraph() {

        objRoot = new BranchGroup();

        //__________________________________________

        objTrans1 = new TransformGroup();

        objTrans1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        objRoot.addChild(objTrans1);



        Sphere sphere = new Sphere(0.25f);

        objTrans1 = new TransformGroup();

        objTrans1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D pos11 = new Transform3D();

        pos11.setTranslation(new Vector3f(0.0f,0.0f,0.0f));

        objTrans1.setTransform(pos11);

        objTrans1.addChild(sphere);

        objRoot.addChild(objTrans1);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

        Color3f light1Color = new Color3f(1.0f, 0.0f, 0.5f);

        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);

        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);

        light1.setInfluencingBounds(bounds);

        objRoot.addChild(light1);

        // Set up the ambient light

        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);

        AmbientLight ambientLightNode = new AmbientLight(ambientColor);

        ambientLightNode.setInfluencingBounds(bounds);

        objRoot.addChild(ambientLightNode);

        createSceneGraph1();
    }

    public void createSceneGraph1() {

        objTrans2 = new TransformGroup();

        objTrans2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        objRoot.addChild(objTrans2);


        Sphere sphere = new Sphere(0.25f);

        objTrans2 = new TransformGroup();

        objTrans2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D pos11 = new Transform3D();

        pos11.setTranslation(new Vector3f(1.0f,0.0f,0.0f));

        objTrans2.setTransform(pos11);

        objTrans2.addChild(sphere);

        objRoot.addChild(objTrans2);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

        Color3f light1Color = new Color3f(1.0f, 0.0f, 0.5f);

        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);

        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);

        light1.setInfluencingBounds(bounds);

        objRoot.addChild(light1);

        // Set up the ambient light

        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);

        AmbientLight ambientLightNode = new AmbientLight(ambientColor);

        ambientLightNode.setInfluencingBounds(bounds);

        objRoot.addChild(ambientLightNode);
    }



    public BouncingBall() {

        setLayout(new BorderLayout());

        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        Canvas3D c = new Canvas3D(config);

        add("Center", c);

        //timer = new Timer(100,this);

//        timer.start();


        // Create a simple scene and attach it to the virtual universe

        createSceneGraph();

        BranchGroup scene = objRoot;

        SimpleUniverse u = new SimpleUniverse(c);

        u.getViewingPlatform().setNominalViewingTransform();

        u.addBranchGraph(scene);

    }



//    public void actionPerformed(ActionEvent e ) {
//
//            height += 0.1 * sign;
//
//            if (Math.abs(height *2) >= 1 ) sign = -1.0f * sign;
//
//            if (height<-0.4f) {
//
//                trans.setScale(new Vector3d(1.0, .8, 1.0));
//
//            }
//
//            else {
//
//                trans.setScale(new Vector3d(1.0, 1.0, 1.0));
//
//            }
//
//            trans.setTranslation(new Vector3f(xloc,height,0.0f));
//
//            objTrans.setTransform(trans);
//
//        Transform3D rotation = new Transform3D();
//        Transform3D temp = new Transform3D();
//
//
//    }

    public static void main(String[] args) {

        BouncingBall bb = new BouncingBall();

        MainFrame mf = new MainFrame(bb, 500, 500);
        mf.setTitle("Bouncing Sphere");
        mf.setLocation(300,100);

    }

}
