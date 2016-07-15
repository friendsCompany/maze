package com.friendsCompany.main;

import com.friendsCompany.view.GameMenu;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Main {
    public static void main(String[] args){
        SimpleUniverse simpleUniverse = new SimpleUniverse();
        BranchGroup branchGroup = new BranchGroup();
        Appearance appearance = new Appearance();
        appearance.setColoringAttributes(new ColoringAttributes(2f,0f,0f,ColoringAttributes.ALLOW_COLOR_READ));
        Box box = new Box(100,100,100,appearance);
        branchGroup.addChild(box);

        Color3f color3f = new Color3f(2,2,0);
        BoundingBox boundingBox = new BoundingBox(new Point3d(-10,-10,-10),new Point3d(10,10,10));
        Vector3f vector3f = new Vector3f(5,6,-100);
        DirectionalLight directionalLight = new DirectionalLight(color3f, vector3f);
        directionalLight.setInfluencingBounds(boundingBox);
        branchGroup.addChild(directionalLight);

        simpleUniverse.addBranchGraph(branchGroup);
        simpleUniverse.getViewingPlatform().setNominalViewingTransform();

        //new GameMenu().initMenu();
    }
}
