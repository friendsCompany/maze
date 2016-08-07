package tests.cubes;


import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

//   HelloJava3Db renders a single, rotated cube.

public class TestCube2 extends Applet implements ActionListener{
    TransformGroup objTrans ;
    Transform3D rotate = new Transform3D();
    Timer timer = new Timer(100,this);
    private double rotateX = 0.0;


    public BranchGroup createSceneGraph() {

        BranchGroup objRoot = new BranchGroup();

        objTrans = new TransformGroup();

        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        objRoot.addChild(objTrans);

        objTrans.addChild(new ColorCube(0.4));

        objRoot.compile();

        return objRoot;
    }

    public TestCube2() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);

        timer.start();

        BranchGroup scene = createSceneGraph();
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(scene);
    }

    public static void main(String[] args) {
        TestCube2 testCube2 = new TestCube2();
        Frame frame = new MainFrame(testCube2, 256, 256);
        frame.setTitle("Rotating Cube");
        frame.setLocation(300,100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        rotate.rotX(rotateX);
        objTrans.setTransform(rotate);
        rotateX+=0.1;
    }
}
