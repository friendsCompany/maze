package tests.fromSites;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class SimpleSphere extends Frame implements ActionListener {
    private GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    protected Canvas3D myCanvas3D = new Canvas3D(config);
    BranchGroup contentBranch;
    Transform3D rotateCube;
    TransformGroup viewXfmGroup;
    Transform3D viewXfm;
    TransformGroup rotationGroup;
    private double i = 0.0;

    protected BranchGroup buildViewBranch(Canvas3D c) {
        BranchGroup viewBranch = new BranchGroup();
        viewXfm = new Transform3D();
        viewXfm.set(new Vector3f(0.0f, 0.0f, 10.0f));
        viewXfmGroup = new TransformGroup(viewXfm);
        ViewPlatform myViewPlatform = new ViewPlatform();
        PhysicalBody myBody = new PhysicalBody();
        PhysicalEnvironment myEnvironment = new PhysicalEnvironment();
        viewXfmGroup.addChild(myViewPlatform);
        viewBranch.addChild(viewXfmGroup);
        View myView = new View();
        myView.addCanvas3D(c);
        myView.attachViewPlatform(myViewPlatform);
        myView.setPhysicalBody(myBody);
        myView.setPhysicalEnvironment(myEnvironment);
        return viewBranch;
    }

    protected void addLights(BranchGroup b) {
        // Create a bounds for the lights
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                100.0);
        // Set up the global lights
        Color3f lightColour1 = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f lightDir1 = new Vector3f(-1.0f, -1.0f, -1.0f);
        Color3f lightColour2 = new Color3f(1.0f, 1.0f, 1.0f);
        Point3f lightPosition2 = new Point3f(3.0f, 3.0f, 3.0f);
        Point3f lightAtten2 = new Point3f(1.0f, 0.0f, 1.0f);
        Vector3f lightDir2 = new Vector3f(-1.0f, -1.0f, -1.0f);
        Color3f ambientColour = new Color3f(0.2f, 0.2f, 0.2f);
        AmbientLight ambientLight1 = new AmbientLight(ambientColour);
        ambientLight1.setInfluencingBounds(bounds);
        DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
        light1.setInfluencingBounds(bounds);
        PointLight light2 = new PointLight(lightColour2, lightPosition2,
                lightAtten2);
        light2.setInfluencingBounds(bounds);
        b.addChild(ambientLight1);
        b.addChild(light1);
        b.addChild(light2);
    }

    protected BranchGroup buildContentBranch() {
        contentBranch = new BranchGroup();
        rotateCube = new Transform3D();
        rotateCube.set(new AxisAngle4d(1.0, 1.0, 0.0, Math.PI ));
        rotationGroup = new TransformGroup(rotateCube);
        rotationGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        contentBranch.addChild(rotationGroup);

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
        rotationGroup.addChild(new Sphere(2.0f, primflags, 120, ap));
        addLights(contentBranch);
        return contentBranch;
    }

    public SimpleSphere() {
        VirtualUniverse myUniverse = new VirtualUniverse();
        Locale myLocale = new Locale(myUniverse);
        BranchGroup b2 = buildViewBranch(myCanvas3D);
        myLocale.addBranchGraph(b2);
        myLocale.addBranchGraph(buildContentBranch());
        setTitle("SimpleWorld");
        setSize(400, 400);
        setLayout(new BorderLayout());

        add("Center", myCanvas3D);

        Timer timer = new Timer(100,this);
        timer.start();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        rotateCube.set(new AxisAngle4d(1.0, 1.0, 0.0, i ));
        rotationGroup.setTransform(rotateCube);
        i += 0.01;
    }

    public static void main(String[] args) {
        SimpleSphere sw = new SimpleSphere();
    }


}
