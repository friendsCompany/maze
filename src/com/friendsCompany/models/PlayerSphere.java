package com.friendsCompany.models;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PlayerSphere extends Applet implements ActionListener {

    private TransformGroup objTrans;

    private Transform3D trans = new Transform3D();

    private float height=0.0f;

    private float sign = 1.0f; // going up or down

    private Timer timer;

    private float xloc=0.0f;

    private double i = -1.0;



    public BranchGroup createSceneGraph() {

        // Create the root of the branch graph



        BranchGroup objRoot = new BranchGroup();

        objTrans = new TransformGroup();

        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        objRoot.addChild(objTrans);

        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);

        Color3f red = new Color3f(0.7f, .15f, .15f);

        // Set up the texture map

        TextureLoader loader = new TextureLoader("res/images/Arizona.jpg","LUMINANCE", new Container());

        Texture texture = loader.getTexture();

        texture.setBoundaryModeS(Texture.WRAP);

        texture.setBoundaryModeT(Texture.WRAP);

        texture.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );



        // Set up the texture attributes

        //could be REPLACE, BLEND or DECAL instead of MODULATE

        TextureAttributes texAttr = new TextureAttributes();

        texAttr.setTextureMode(TextureAttributes.MODULATE);

        Appearance ap = new Appearance();

        ap.setTexture(texture);

        ap.setTextureAttributes(texAttr);

        //set up the material

        ap.setMaterial(new Material(red, black, red, black, 1.0f));



        // Create a ball to demonstrate textures

        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

        // Create a simple shape leaf node, add it to the scene graph.

        Sphere sphere = new Sphere(0.25f, primflags, ap);

        objTrans = new TransformGroup();

        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D pos1 = new Transform3D();

        pos1.setTranslation(new Vector3f(0.0f,0.0f,0.0f));

        objTrans.setTransform(pos1);

        objTrans.addChild(sphere);

        objRoot.addChild(objTrans);

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

        return objRoot;

    }

    public PlayerSphere() {

        setLayout(new BorderLayout());

        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        Canvas3D c = new Canvas3D(config);

        add("Center", c);

        timer = new Timer(100,this);

        timer.start();


        // Create a simple scene and attach it to the virtual universe

        BranchGroup scene = createSceneGraph();

        SimpleUniverse u = new SimpleUniverse(c);

        u.getViewingPlatform().setNominalViewingTransform();

        u.addBranchGraph(scene);

    }



    public void actionPerformed(ActionEvent e ) {

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

//        Transform3D rotation = new Transform3D();
//        Transform3D temp = new Transform3D();
//          Math.PI / 2

        trans.rotY(i = i + 0.1);

        objTrans.setTransform(trans);

    }

    public static void initPlayer() {

        PlayerSphere playerSphere = new PlayerSphere();

        MainFrame mf = new MainFrame(playerSphere, 500, 500);
        mf.setTitle("Bouncing Sphere");
        mf.setLocation(300,100);

    }

}
