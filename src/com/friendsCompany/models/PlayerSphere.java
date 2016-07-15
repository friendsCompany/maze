package com.friendsCompany.models;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PlayerSphere extends Applet{

    public PlayerSphere() {

        setLayout(new BorderLayout());

        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas = new Canvas3D(config);

        add("Center", canvas);

        BranchGroup groups = new BranchGroup();
        Sphere sphere = new Sphere(0.5f);
        Color3f color = new Color3f(0, 0, 3);
        BoundingSphere bounding = new BoundingSphere(new Point3d(0,0,0), 1000);
        groups.addChild(sphere);
        Vector3f direction = new Vector3f(5,-5,-10);
        DirectionalLight light = new DirectionalLight(color, direction);
        light.setInfluencingBounds(bounding);
        groups.addChild(light);

        SimpleUniverse simpleUniverse = new SimpleUniverse(canvas);

        simpleUniverse.getViewingPlatform().setNominalViewingTransform();
        simpleUniverse.addBranchGraph(groups);

    }
}
