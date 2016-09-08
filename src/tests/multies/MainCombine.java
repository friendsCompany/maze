package tests.multies;


import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import java.applet.Applet;
import java.awt.*;

public class MainCombine extends Frame {
    private Map map = new Map();

    public static void main(String[] args) {
        new MainCombine().frameConfig();

    }

    private void frameConfig(){
        setTitle("hello");
        setSize(400, 400);
        setLayout(new BorderLayout());
        setVisible(true);
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        add("Center", canvas);
        setFocusable(true);

        SimpleUniverse u = map.getSimpleUniverse(canvas);

        BranchGroup scene = map.createSceneGraph();

        u.getViewingPlatform().setNominalViewingTransform();
        u.getViewer().getView().setBackClipDistance(100.0);

        u.addBranchGraph(scene);
    }

}
