package tests.fromSites;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GraphicsConfigTemplate;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.AudioDevice;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Group;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Locale;
import javax.media.j3d.Material;
import javax.media.j3d.NodeComponent;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import com.sun.j3d.audioengines.javasound.JavaSoundMixer;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;

/**
 * Allows the various Java 3D Appearance components to be specified
 * interactively and applies the Appearance to an object in a scene.
 * <p>
 * This example can only be run as an application as it uses a MenuBar which can
 * only be associated with a Frame (in AWT)
 */
public class AppearanceTest extends Java3dApplet {
    private static int m_kWidth = 400;

    private static int m_kHeight = 400;

    private Frame m_Frame = null;

    private Appearance m_Appearance = null;

    private AppearanceComponent[] m_ComponentArray = null;

    public AppearanceTest() {
        m_Appearance = new Appearance();



        m_ComponentArray = new AppearanceComponent[] {
                new PolygonComponent(m_Appearance),
                new ColoringComponent(m_Appearance),
                new LineComponent(m_Appearance),
                new MaterialComponent(m_Appearance),
                new PointComponent(m_Appearance),
                new RenderingComponent(m_Appearance),
                new TransparencyComponent(m_Appearance),
                new TextureAttributesComponent(m_Appearance),
                new TexGenComponent(m_Appearance) };
    }

    protected void addCanvas3D(Canvas3D c3d) {
        if (m_Frame != null) {
            MenuBar menuBar = new MenuBar();

            for (int n = 0; n < m_ComponentArray.length; n++)
                menuBar.add(m_ComponentArray[n].createMenu());

            m_Frame.setMenuBar(menuBar);
        }

        setLayout(new BorderLayout());
        add(c3d, BorderLayout.CENTER);
        doLayout();
    }

    protected double getScale() {
        return 0.1;
    }

    protected BranchGroup createSceneBranchGroup() {
        BranchGroup objRoot = super.createSceneBranchGroup();

        TransformGroup zoomTg = new TransformGroup();
        zoomTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        zoomTg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        // attach a navigation behavior to the position of the viewer
        KeyNavigatorBehavior key = new KeyNavigatorBehavior(zoomTg);
        key.setSchedulingBounds(createApplicationBounds());
        key.setEnable(true);
        objRoot.addChild(key);

        // create a TransformGroup to flip the hand onto its end and enlarge it.
        TransformGroup objTrans1 = new TransformGroup();
        Transform3D tr = new Transform3D();
        objTrans1.getTransform(tr);
        tr.setEuler(new Vector3d(0.5 * Math.PI, 0.6, 0));
        objTrans1.setTransform(tr);

        // Set up the global lights
        Color3f lColor1 = new Color3f(0.7f, 0.7f, 0.7f);
        Vector3f lDir1 = new Vector3f(-1.0f, -1.0f, -1.0f);
        Color3f alColor = new Color3f(0.2f, 0.2f, 0.2f);

        AmbientLight aLgt = new AmbientLight(alColor);
        aLgt.setInfluencingBounds(getApplicationBounds());
        DirectionalLight lgt1 = new DirectionalLight(lColor1, lDir1);
        lgt1.setInfluencingBounds(getApplicationBounds());

        objRoot.addChild(aLgt);
        objRoot.addChild(lgt1);

        int nScale = 50;

        Box box = new Box(nScale, nScale, nScale, Primitive.GENERATE_NORMALS
                | Primitive.GENERATE_TEXTURE_COORDS, m_Appearance);

        Shape3D frontFace = box.getShape(Box.LEFT);

        // create a new left face so we can
        // assign per-vertex colors

        GeometryArray geometry = new QuadArray(4, GeometryArray.COORDINATES
                | GeometryArray.NORMALS | GeometryArray.COLOR_4
                | GeometryArray.TEXTURE_COORDINATE_2);

        nScale = 40;

        final float[] verts = {
                // left face
                -1.0f * nScale, -1.0f * nScale, 1.0f * nScale, -1.0f * nScale,
                1.0f * nScale, 1.0f * nScale, -1.0f * nScale, 1.0f * nScale,
                -1.0f * nScale, -1.0f * nScale, -1.0f * nScale, -1.0f * nScale };

        final float[] colors = {
                // left face
                1, 0, 0, 0, 0, 1, 0, 0.2f, 0, 0, 1, 0.8f, 0, 0, 0, 1, };

        float[] tcoords = {
                // left
                1, 0, 1, 1, 0, 1, 0, 0 };

        Vector3f normalVector = new Vector3f(-1.0f, 0.0f, 0.0f);

        geometry.setColors(0, colors, 0, 4);

        for (int n = 0; n < 4; n++)
            geometry.setNormal(n, normalVector);

        geometry.setTextureCoordinates(0, tcoords, 0, 4);

        geometry.setCoordinates(0, verts);

        frontFace.setGeometry(geometry);

        // connect the scenegraph
        objTrans1.addChild(box);
        zoomTg.addChild(objTrans1);
        objRoot.addChild(zoomTg);

        return objRoot;
    }

    public static void main(String[] args) {
        AppearanceTest appearanceTest = new AppearanceTest();
        appearanceTest.saveCommandLineArguments(args);

        Frame frame = (Frame) new MainFrame(appearanceTest, m_kWidth, m_kHeight);

        appearanceTest.m_Frame = frame;
        appearanceTest.initJava3d();
    }
}

/*******************************************************************************
 * Copyright (C) 2001 Daniel Selman
 *
 * First distributed with the book "Java 3D Programming" by Daniel Selman and
 * published by Manning Publications. http://manning.com/selman
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, version 2.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * The license can be found on the WWW at: http://www.fsf.org/copyleft/gpl.html
 *
 * Or by writing to: Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA.
 *
 * Authors can be contacted at: Daniel Selman: daniel@selman.org
 *
 * If you make changes you think others would like, please contact one of the
 * authors or someone at the www.j3d.org web site.
 ******************************************************************************/

//*****************************************************************************
/**
 * Java3dApplet
 *
 * Base class for defining a Java 3D applet. Contains some useful methods for
 * defining views and scenegraphs etc.
 *
 * @author Daniel Selman
 * @version 1.0
 */
//*****************************************************************************

abstract class Java3dApplet extends Applet {
    public static int m_kWidth = 300;

    public static int m_kHeight = 300;

    protected String[] m_szCommandLineArray = null;

    protected VirtualUniverse m_Universe = null;

    protected BranchGroup m_SceneBranchGroup = null;

    protected Bounds m_ApplicationBounds = null;

    //  protected com.tornadolabs.j3dtree.Java3dTree m_Java3dTree = null;

    public Java3dApplet() {
    }

    public boolean isApplet() {
        try {
            System.getProperty("user.dir");
            System.out.println("Running as Application.");
            return false;
        } catch (Exception e) {
        }

        System.out.println("Running as Applet.");
        return true;
    }

    public URL getWorkingDirectory() throws java.net.MalformedURLException {
        URL url = null;

        try {
            File file = new File(System.getProperty("user.dir"));
            System.out.println("Running as Application:");
            System.out.println("   " + file.toURL());
            return file.toURL();
        } catch (Exception e) {
        }

        System.out.println("Running as Applet:");
        System.out.println("   " + getCodeBase());

        return getCodeBase();
    }

    public VirtualUniverse getVirtualUniverse() {
        return m_Universe;
    }

    //public com.tornadolabs.j3dtree.Java3dTree getJ3dTree() {
    //return m_Java3dTree;
    //  }

    public Locale getFirstLocale() {
        java.util.Enumeration e = m_Universe.getAllLocales();

        if (e.hasMoreElements() != false)
            return (Locale) e.nextElement();

        return null;
    }

    protected Bounds getApplicationBounds() {
        if (m_ApplicationBounds == null)
            m_ApplicationBounds = createApplicationBounds();

        return m_ApplicationBounds;
    }

    protected Bounds createApplicationBounds() {
        m_ApplicationBounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                100.0);
        return m_ApplicationBounds;
    }

    protected Background createBackground() {
        Background back = new Background(new Color3f(0.9f, 0.9f, 0.9f));
        back.setApplicationBounds(createApplicationBounds());
        return back;
    }

    public void initJava3d() {
        //  m_Java3dTree = new com.tornadolabs.j3dtree.Java3dTree();
        m_Universe = createVirtualUniverse();

        Locale locale = createLocale(m_Universe);

        BranchGroup sceneBranchGroup = createSceneBranchGroup();

        ViewPlatform vp = createViewPlatform();
        BranchGroup viewBranchGroup = createViewBranchGroup(
                getViewTransformGroupArray(), vp);

        createView(vp);

        Background background = createBackground();

        if (background != null)
            sceneBranchGroup.addChild(background);

        //    m_Java3dTree.recursiveApplyCapability(sceneBranchGroup);
        //  m_Java3dTree.recursiveApplyCapability(viewBranchGroup);

        locale.addBranchGraph(sceneBranchGroup);
        addViewBranchGroup(locale, viewBranchGroup);

        onDoneInit();
    }

    protected void onDoneInit() {
        //  m_Java3dTree.updateNodes(m_Universe);
    }

    protected double getScale() {
        return 1.0;
    }

    public TransformGroup[] getViewTransformGroupArray() {
        TransformGroup[] tgArray = new TransformGroup[1];
        tgArray[0] = new TransformGroup();

        // move the camera BACK a little...
        // note that we have to invert the matrix as
        // we are moving the viewer
        Transform3D t3d = new Transform3D();
        t3d.setScale(getScale());
        t3d.setTranslation(new Vector3d(0.0, 0.0, -20.0));
        t3d.invert();
        tgArray[0].setTransform(t3d);

        return tgArray;
    }

    protected void addViewBranchGroup(Locale locale, BranchGroup bg) {
        locale.addBranchGraph(bg);
    }

    protected Locale createLocale(VirtualUniverse u) {
        return new Locale(u);
    }

    protected BranchGroup createSceneBranchGroup() {
        m_SceneBranchGroup = new BranchGroup();
        return m_SceneBranchGroup;
    }

    protected View createView(ViewPlatform vp) {
        View view = new View();

        PhysicalBody pb = createPhysicalBody();
        PhysicalEnvironment pe = createPhysicalEnvironment();

        AudioDevice audioDevice = createAudioDevice(pe);

        if (audioDevice != null) {
            pe.setAudioDevice(audioDevice);
            audioDevice.initialize();
        }

        view.setPhysicalEnvironment(pe);
        view.setPhysicalBody(pb);

        if (vp != null)
            view.attachViewPlatform(vp);

        view.setBackClipDistance(getBackClipDistance());
        view.setFrontClipDistance(getFrontClipDistance());

        Canvas3D c3d = createCanvas3D();
        view.addCanvas3D(c3d);
        addCanvas3D(c3d);

        return view;
    }

    protected PhysicalBody createPhysicalBody() {
        return new PhysicalBody();
    }

    protected AudioDevice createAudioDevice(PhysicalEnvironment pe) {
        JavaSoundMixer javaSoundMixer = new JavaSoundMixer(pe);

        if (javaSoundMixer == null)
            System.out.println("create of audiodevice failed");

        return javaSoundMixer;
    }

    protected PhysicalEnvironment createPhysicalEnvironment() {
        return new PhysicalEnvironment();
    }

    protected float getViewPlatformActivationRadius() {
        return 100;
    }

    protected ViewPlatform createViewPlatform() {
        ViewPlatform vp = new ViewPlatform();
        vp.setViewAttachPolicy(View.RELATIVE_TO_FIELD_OF_VIEW);
        vp.setActivationRadius(getViewPlatformActivationRadius());

        return vp;
    }

    protected Canvas3D createCanvas3D() {
        GraphicsConfigTemplate3D gc3D = new GraphicsConfigTemplate3D();
        gc3D.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);
        GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getScreenDevices();

        Canvas3D c3d = new Canvas3D(gd[0].getBestConfiguration(gc3D));
        c3d.setSize(getCanvas3dWidth(c3d), getCanvas3dHeight(c3d));

        return c3d;
    }

    protected int getCanvas3dWidth(Canvas3D c3d) {
        return m_kWidth;
    }

    protected int getCanvas3dHeight(Canvas3D c3d) {
        return m_kHeight;
    }

    protected double getBackClipDistance() {
        return 100.0;
    }

    protected double getFrontClipDistance() {
        return 1.0;
    }

    protected BranchGroup createViewBranchGroup(TransformGroup[] tgArray,
                                                ViewPlatform vp) {
        BranchGroup vpBranchGroup = new BranchGroup();

        if (tgArray != null && tgArray.length > 0) {
            Group parentGroup = vpBranchGroup;
            TransformGroup curTg = null;

            for (int n = 0; n < tgArray.length; n++) {
                curTg = tgArray[n];
                parentGroup.addChild(curTg);
                parentGroup = curTg;
            }

            tgArray[tgArray.length - 1].addChild(vp);
        } else
            vpBranchGroup.addChild(vp);

        return vpBranchGroup;
    }

    protected void addCanvas3D(Canvas3D c3d) {
        setLayout(new BorderLayout());
        add(c3d, BorderLayout.CENTER);
        doLayout();
    }

    protected VirtualUniverse createVirtualUniverse() {
        return new VirtualUniverse();
    }

    protected void saveCommandLineArguments(String[] szArgs) {
        m_szCommandLineArray = szArgs;
    }

    protected String[] getCommandLineArguments() {
        return m_szCommandLineArray;
    }
}

class LineComponent extends AppearanceComponent {
    public LineComponent(Appearance app) {
        super(app);
    }

    protected int[] getCapabilities() {
        return new int[] { LineAttributes.ALLOW_ANTIALIASING_WRITE,
                LineAttributes.ALLOW_PATTERN_WRITE,
                LineAttributes.ALLOW_WIDTH_WRITE };
    }

    protected NodeComponent createComponent() {
        return (NodeComponent) new LineAttributes();
    }

    protected void setAppearanceCapability() {
        m_Appearance.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
    }

    protected void assignToAppearance() {
        m_Appearance.setLineAttributes((LineAttributes) m_NodeComponent);
    }

    protected void assignNullToAppearance() {
        m_Appearance.setLineAttributes(null);
    }

    protected String getName() {
        return "Line";
    }

    protected String[] getMenuItemNames() {
        return new String[] { "-", "Antialiasing", "-", "On", "Off", "-",
                "Pattern", "-", "Dash", "DashDot", "Dot", "Solid", "-",
                "Width", "-", "1", "2", "5", "10" };
    }

    private LineAttributes getLineAttributes() {
        return (LineAttributes) m_NodeComponent;
    }

    public void onOn() {
        getLineAttributes().setLineAntialiasingEnable(true);
    }

    public void onOff() {
        getLineAttributes().setLineAntialiasingEnable(false);
    }

    public void onDash() {
        getLineAttributes().setLinePattern(LineAttributes.PATTERN_DASH);
    }

    public void onDashDot() {
        getLineAttributes().setLinePattern(LineAttributes.PATTERN_DASH_DOT);
    }

    public void onDot() {
        getLineAttributes().setLinePattern(LineAttributes.PATTERN_DOT);
    }

    public void onSolid() {
        getLineAttributes().setLinePattern(LineAttributes.PATTERN_SOLID);
    }

    public void on1() {
        getLineAttributes().setLineWidth(1);
    }

    public void on2() {
        getLineAttributes().setLineWidth(2);
    }

    public void on5() {
        getLineAttributes().setLineWidth(5);
    }

    public void on10() {
        getLineAttributes().setLineWidth(10);
    }
}

class MaterialComponent extends AppearanceComponent {
    public MaterialComponent(Appearance app) {
        super(app);
    }

    protected int[] getCapabilities() {
        return new int[] { Material.ALLOW_COMPONENT_WRITE };
    }

    protected NodeComponent createComponent() {
        return (NodeComponent) new Material();
    }

    protected void setAppearanceCapability() {
        m_Appearance.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
    }

    protected void assignToAppearance() {
        m_Appearance.setMaterial((Material) m_NodeComponent);
    }

    protected void assignNullToAppearance() {
        m_Appearance.setMaterial(null);
    }

    protected String getName() {
        return "Material";
    }

    protected String[] getMenuItemNames() {
        return new String[] { "-", "Ambient", "-", "A_White", "A_Black",
                "A_Blue", "-", "Diffuse", "-", "D_White", "D_Black", "D_Blue",
                "-", "Emissive", "-", "E_White", "E_Black", "E_Blue", "-",
                "Specular", "-", "S_White", "S_Black", "S_Blue", "-",
                "Lighting", "-", "On", "Off", "-", "Shininess", "-", "1", "70", };
    }

    private Material getMaterial() {
        return (Material) m_NodeComponent;
    }

    public void onA_White() {
        getMaterial().setAmbientColor(1, 1, 1);
    }

    public void onA_Black() {
        getMaterial().setAmbientColor(0, 0, 0);
    }

    public void onA_Blue() {
        getMaterial().setAmbientColor(0, 0, 1);
    }

    public void onE_White() {
        getMaterial().setEmissiveColor(1, 1, 1);
    }

    public void onE_Black() {
        getMaterial().setEmissiveColor(0, 0, 0);
    }

    public void onE_Blue() {
        getMaterial().setEmissiveColor(0, 0, 1);
    }

    public void onD_White() {
        getMaterial().setDiffuseColor(1, 1, 1);
    }

    public void onD_Black() {
        getMaterial().setDiffuseColor(0, 0, 0);
    }

    public void onD_Blue() {
        getMaterial().setDiffuseColor(0, 0, 1);
    }

    public void onS_White() {
        getMaterial().setSpecularColor(1, 1, 1);
    }

    public void onS_Black() {
        getMaterial().setSpecularColor(0, 0, 0);
    }

    public void onS_Blue() {
        getMaterial().setSpecularColor(0, 0, 1);
    }

    public void onOn() {
        getMaterial().setLightingEnable(true);
    }

    public void onOff() {
        getMaterial().setLightingEnable(false);
    }

    public void on1() {
        getMaterial().setShininess(1);
    }

    public void on70() {
        getMaterial().setShininess(70);
    }
}

class PointComponent extends AppearanceComponent {
    public PointComponent(Appearance app) {
        super(app);
    }

    protected int[] getCapabilities() {
        return new int[] { PointAttributes.ALLOW_ANTIALIASING_WRITE,
                PointAttributes.ALLOW_SIZE_WRITE };
    }

    protected NodeComponent createComponent() {
        return (NodeComponent) new PointAttributes();
    }

    protected void setAppearanceCapability() {
        m_Appearance.setCapability(Appearance.ALLOW_POINT_ATTRIBUTES_WRITE);
    }

    protected void assignToAppearance() {
        m_Appearance.setPointAttributes((PointAttributes) m_NodeComponent);
    }

    protected void assignNullToAppearance() {
        m_Appearance.setPointAttributes(null);
    }

    protected String getName() {
        return "Point";
    }

    protected String[] getMenuItemNames() {
        return new String[] { "-", "Antialiasing", "-", "On", "Off", "-",
                "Size", "-", "1", "5", "10" };
    }

    private PointAttributes getPointAttributes() {
        return (PointAttributes) m_NodeComponent;
    }

    public void onOn() {
        getPointAttributes().setPointAntialiasingEnable(true);
    }

    public void onOff() {
        getPointAttributes().setPointAntialiasingEnable(false);
    }

    public void on1() {
        getPointAttributes().setPointSize(1);
    }

    public void on5() {
        getPointAttributes().setPointSize(5);
    }

    public void on10() {
        getPointAttributes().setPointSize(10);
    }
}

class PolygonComponent extends AppearanceComponent {
    public PolygonComponent(Appearance app) {
        super(app);
    }

    protected int[] getCapabilities() {
        return new int[] { PolygonAttributes.ALLOW_CULL_FACE_WRITE,
                PolygonAttributes.ALLOW_MODE_WRITE,
                PolygonAttributes.ALLOW_NORMAL_FLIP_WRITE,
                PolygonAttributes.ALLOW_OFFSET_WRITE };
    }

    protected NodeComponent createComponent() {
        return (NodeComponent) new PolygonAttributes();
    }

    protected void setAppearanceCapability() {
        m_Appearance.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
    }

    protected void assignToAppearance() {
        m_Appearance.setPolygonAttributes((PolygonAttributes) m_NodeComponent);
    }

    protected void assignNullToAppearance() {
        m_Appearance.setPolygonAttributes(null);
    }

    protected String getName() {
        return "Polygon";
    }

    protected String[] getMenuItemNames() {
        return new String[] { "-", "Cull", "-", "Back", "Front", "None", "-",
                "Mode", "-", "Fill", "Line", "Point", "-", "Normal", "-",
                "Flip_ON", "Flip_OFF", "-", "Offset", "-", "0", "10", "50",
                "200" };
    }

    private PolygonAttributes getPolygonAttributes() {
        return (PolygonAttributes) m_NodeComponent;
    }

    public void onBack() {
        getPolygonAttributes().setCullFace(PolygonAttributes.CULL_BACK);
    }

    public void onFront() {
        getPolygonAttributes().setCullFace(PolygonAttributes.CULL_FRONT);
    }

    public void onNone() {
        getPolygonAttributes().setCullFace(PolygonAttributes.CULL_NONE);
    }

    public void onFill() {
        getPolygonAttributes().setPolygonMode(PolygonAttributes.POLYGON_FILL);
    }

    public void onLine() {
        getPolygonAttributes().setPolygonMode(PolygonAttributes.POLYGON_LINE);
    }

    public void onPoint() {
        getPolygonAttributes().setPolygonMode(PolygonAttributes.POLYGON_POINT);
    }

    public void onFlip_ON() {
        getPolygonAttributes().setBackFaceNormalFlip(true);
    }

    public void onFlip_OFF() {
        getPolygonAttributes().setBackFaceNormalFlip(false);
    }

    public void on0() {
        getPolygonAttributes().setPolygonOffset(0);
    }

    public void on10() {
        getPolygonAttributes().setPolygonOffset(10);
    }

    public void on50() {
        getPolygonAttributes().setPolygonOffset(50);
    }

    public void on200() {
        getPolygonAttributes().setPolygonOffset(200);
    }
}

class RenderingComponent extends AppearanceComponent {
    public RenderingComponent(Appearance app) {
        super(app);
    }

    protected int[] getCapabilities() {
        return new int[] { RenderingAttributes.ALLOW_ALPHA_TEST_FUNCTION_WRITE,
                RenderingAttributes.ALLOW_ALPHA_TEST_VALUE_WRITE,
                RenderingAttributes.ALLOW_DEPTH_ENABLE_READ };
    }

    protected NodeComponent createComponent() {
        return (NodeComponent) new RenderingAttributes();
    }

    protected void setAppearanceCapability() {
        m_Appearance.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
    }

    protected void assignToAppearance() {
        m_Appearance
                .setRenderingAttributes((RenderingAttributes) m_NodeComponent);
    }

    protected void assignNullToAppearance() {
        m_Appearance.setRenderingAttributes(null);
    }

    protected String getName() {
        return "Rendering";
    }

    protected String[] getMenuItemNames() {
        return new String[] { "-", "AlphaTest", "-", "ALWAYS", "NEVER",
                "EQUAL", "NOT_EQUAL", "LESS", "LESS_OR_EQUAL", "GREATER",
                "GREATER_OR_EQUAL", "-", "AlphaTest Value", "-", "0",
                "0point5", "1", "-", "Depth Buffer", "-", "On", "Off", "-",
                "Depth Buffer Write", "-", "Enable", "Disable", };
    }

    private RenderingAttributes getRenderingAttributes() {
        return (RenderingAttributes) m_NodeComponent;
    }

    public void onALWAYS() {
        getRenderingAttributes().setAlphaTestFunction(
                RenderingAttributes.ALWAYS);
    }

    public void onNEVER() {
        getRenderingAttributes()
                .setAlphaTestFunction(RenderingAttributes.NEVER);
    }

    public void onEQUAL() {
        getRenderingAttributes()
                .setAlphaTestFunction(RenderingAttributes.EQUAL);
    }

    public void onNOT_EQUAL() {
        getRenderingAttributes().setAlphaTestFunction(
                RenderingAttributes.NOT_EQUAL);
    }

    public void onLESS() {
        getRenderingAttributes().setAlphaTestFunction(RenderingAttributes.LESS);
    }

    public void onLESS_OR_EQUAL() {
        getRenderingAttributes().setAlphaTestFunction(
                RenderingAttributes.LESS_OR_EQUAL);
    }

    public void onGREATER() {
        getRenderingAttributes().setAlphaTestFunction(
                RenderingAttributes.GREATER);
    }

    public void onGREATER_OR_EQUAL() {
        getRenderingAttributes().setAlphaTestFunction(
                RenderingAttributes.GREATER_OR_EQUAL);
    }

    public void on0() {
        getRenderingAttributes().setAlphaTestValue(0);
    }

    public void on0point5() {
        getRenderingAttributes().setAlphaTestValue(0.5f);
    }

    public void on1() {
        getRenderingAttributes().setAlphaTestValue(1);
    }

    public void onOn() {
        getRenderingAttributes().setDepthBufferEnable(true);
    }

    public void onOff() {
        getRenderingAttributes().setDepthBufferEnable(false);
    }

    public void onEnable() {
        getRenderingAttributes().setDepthBufferWriteEnable(true);
    }

    public void onDisable() {
        getRenderingAttributes().setDepthBufferWriteEnable(false);
    }
}

class TexGenComponent extends AppearanceComponent {
    TexCoordGeneration m_TexCoordGeneration = null;

    final float m_PlaneFactor = 0.02f;

    public TexGenComponent(Appearance app) {
        super(app);
    }

    protected int[] getCapabilities() {
        return new int[] { TexCoordGeneration.ALLOW_ENABLE_WRITE };
    }

    protected NodeComponent createComponent() {
        return (NodeComponent) new TexCoordGeneration(
                TexCoordGeneration.OBJECT_LINEAR,
                TexCoordGeneration.TEXTURE_COORDINATE_3);
    }

    protected void setAppearanceCapability() {
        m_Appearance.setCapability(Appearance.ALLOW_TEXGEN_WRITE);
    }

    protected void assignToAppearance() {
        m_TexCoordGeneration = new TexCoordGeneration();
        m_TexCoordGeneration.duplicateNodeComponent(m_NodeComponent);
        m_Appearance.setTexCoordGeneration(m_TexCoordGeneration);
    }

    protected void assignNullToAppearance() {
        m_Appearance.setTexCoordGeneration(null);
    }

    protected String getName() {
        return "TexCoordGen";
    }

    protected String[] getMenuItemNames() {
        return new String[] { "-", "Enable", "-", "On", "Off", "-", "GenMode",
                "-", "EYE_LINEAR", "OBJECT_LINEAR", "SPHERE_MAP", "-",
                "Format", "-", "TEXTURE_COORDINATE_2", "TEXTURE_COORDINATE_3",
                "-", "Plane R", "-", "R_1_0_0_0", "R_0_1_0_5", "R_0_0_1_0",
                "R_0_0_0_1", "-", "Plane S", "-", "S_1_0_0_0", "S_0_1_0_5",
                "S_0_0_1_0", "S_0_0_0_1", "-", "Plane T", "-", "T_1_0_0_0",
                "T_0_1_0_5", "T_0_0_1_0", "T_0_0_0_1", };
    }

    private TexCoordGeneration getTexCoordGeneration() {
        return (TexCoordGeneration) m_NodeComponent;
    }

    public void onOn() {
        getTexCoordGeneration().setEnable(true);
        assignToAppearance();
    }

    public void onOff() {
        getTexCoordGeneration().setEnable(false);
        assignToAppearance();
    }

    public void onEYE_LINEAR() {
        getTexCoordGeneration().setGenMode(TexCoordGeneration.EYE_LINEAR);
        assignToAppearance();
    }

    public void onOBJECT_LINEAR() {
        getTexCoordGeneration().setGenMode(TexCoordGeneration.OBJECT_LINEAR);
        assignToAppearance();
    }

    public void onSPHERE_MAP() {
        getTexCoordGeneration().setGenMode(TexCoordGeneration.SPHERE_MAP);
        assignToAppearance();
    }

    public void onTEXTURE_COORDINATE_2() {
        getTexCoordGeneration().setFormat(
                TexCoordGeneration.TEXTURE_COORDINATE_2);
        assignToAppearance();
    }

    public void onTEXTURE_COORDINATE_3() {
        getTexCoordGeneration().setFormat(
                TexCoordGeneration.TEXTURE_COORDINATE_3);
        assignToAppearance();
    }

    public void onR_1_0_0_0() {
        getTexCoordGeneration().setPlaneR(new Vector4f(m_PlaneFactor, 0, 0, 0));
        assignToAppearance();
    }

    public void onR_0_1_0_5() {
        getTexCoordGeneration().setPlaneR(
                new Vector4f(0, m_PlaneFactor, 0, 0.5f));
        assignToAppearance();
    }

    public void onR_0_0_1_0() {
        getTexCoordGeneration().setPlaneR(new Vector4f(0, 0, m_PlaneFactor, 0));
        assignToAppearance();
    }

    public void onR_0_0_0_1() {
        getTexCoordGeneration().setPlaneR(new Vector4f(0, 0, 0, m_PlaneFactor));
        assignToAppearance();
    }

    public void onS_1_0_0_0() {
        getTexCoordGeneration().setPlaneS(new Vector4f(m_PlaneFactor, 0, 0, 0));
        assignToAppearance();
    }

    public void onS_0_1_0_5() {
        getTexCoordGeneration().setPlaneS(
                new Vector4f(0, m_PlaneFactor, 0, 0.5f));
        assignToAppearance();
    }

    public void onS_0_0_1_0() {
        getTexCoordGeneration().setPlaneS(new Vector4f(0, 0, m_PlaneFactor, 0));
        assignToAppearance();
    }

    public void onS_0_0_0_1() {
        getTexCoordGeneration().setPlaneS(new Vector4f(0, 0, 0, m_PlaneFactor));
        assignToAppearance();
    }

    public void onT_1_0_0_0() {
        getTexCoordGeneration().setPlaneT(new Vector4f(m_PlaneFactor, 0, 0, 0));
        assignToAppearance();
    }

    public void onT_0_1_0_5() {
        getTexCoordGeneration().setPlaneT(
                new Vector4f(0, m_PlaneFactor, 0, 0.5f));
        assignToAppearance();
    }

    public void onT_0_0_1_0() {
        getTexCoordGeneration().setPlaneT(new Vector4f(0, 0, m_PlaneFactor, 0));
        assignToAppearance();
    }

    public void onT_0_0_0_1() {
        getTexCoordGeneration().setPlaneT(new Vector4f(0, 0, 0, m_PlaneFactor));
        assignToAppearance();
    }
}

class TextureAttributesComponent extends AppearanceComponent {
    public TextureAttributesComponent(Appearance app) {
        super(app);
    }

    protected int[] getCapabilities() {
        return new int[] { TextureAttributes.ALLOW_BLEND_COLOR_WRITE,
                TextureAttributes.ALLOW_MODE_WRITE,
                TextureAttributes.ALLOW_TRANSFORM_WRITE, };
    }

    protected NodeComponent createComponent() {
        return (NodeComponent) new TextureAttributes();
    }

    protected void setAppearanceCapability() {
        m_Appearance.setCapability(Appearance.ALLOW_TEXTURE_ATTRIBUTES_WRITE);
    }

    protected void assignToAppearance() {
        m_Appearance.setTextureAttributes((TextureAttributes) m_NodeComponent);
    }

    protected void assignNullToAppearance() {
        m_Appearance.setTextureAttributes(null);
    }

    protected String getName() {
        return "TextureAttributes";
    }

    protected String[] getMenuItemNames() {
        return new String[] { "-", "Mode", "-", "MODULATE", "DECAL", "BLEND",
                "REPLACE", "-", "Blend Color", "-", "T_White_Alpha_0point3",
                "T_Black_Alpha_0point7", "T_Blue_Alpha_1", "-", "Transform",
                "-", "0_degrees", "X_30_degrees", "Y_30_degrees",
                "Z_30_degrees", "-", "Perspective Correction", "-", "NICEST",
                "FASTEST" };
    }

    private TextureAttributes getTextureAttributes() {
        return (TextureAttributes) m_NodeComponent;
    }

    public void onMODULATE() {
        getTextureAttributes().setTextureMode(TextureAttributes.MODULATE);
    }

    public void onDECAL() {
        getTextureAttributes().setTextureMode(TextureAttributes.DECAL);
    }

    public void onBLEND() {
        getTextureAttributes().setTextureMode(TextureAttributes.BLEND);
    }

    public void onREPLACE() {
        getTextureAttributes().setTextureMode(TextureAttributes.REPLACE);
    }

    public void onT_White_Alpha_0point3() {
        getTextureAttributes().setTextureBlendColor(1, 1, 1, 0.3f);
    }

    public void onT_Black_Alpha_0point7() {
        getTextureAttributes().setTextureBlendColor(0, 0, 0, 0.7f);
    }

    public void onT_Blue_Alpha_1() {
        getTextureAttributes().setTextureBlendColor(0, 0, 1, 1);
    }

    public void on0_degrees() {
        Transform3D t3d = new Transform3D();
        getTextureAttributes().setTextureTransform(t3d);
    }

    public void onX_30_degrees() {
        Transform3D t3d = new Transform3D();
        t3d.rotX(Math.toRadians(30));
        getTextureAttributes().setTextureTransform(t3d);
    }

    public void onY_30_degrees() {
        Transform3D t3d = new Transform3D();
        t3d.rotY(Math.toRadians(30));
        getTextureAttributes().setTextureTransform(t3d);
    }

    public void onZ_30_degrees() {
        Transform3D t3d = new Transform3D();
        t3d.rotZ(Math.toRadians(30));
        getTextureAttributes().setTextureTransform(t3d);
    }

    public void onNICEST() {
        getTextureAttributes().setPerspectiveCorrectionMode(
                TextureAttributes.NICEST);
    }

    public void onFASTEST() {
        getTextureAttributes().setPerspectiveCorrectionMode(
                TextureAttributes.FASTEST);
    }
}



class TransparencyComponent extends AppearanceComponent {
    public TransparencyComponent(Appearance app) {
        super(app);
    }

    protected int[] getCapabilities() {
        return new int[] { TransparencyAttributes.ALLOW_MODE_WRITE,
                TransparencyAttributes.ALLOW_VALUE_WRITE };
    }

    protected NodeComponent createComponent() {
        return (NodeComponent) new TransparencyAttributes();
    }

    protected void setAppearanceCapability() {
        m_Appearance
                .setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
    }

    protected void assignToAppearance() {
        m_Appearance
                .setTransparencyAttributes((TransparencyAttributes) m_NodeComponent);
    }

    protected void assignNullToAppearance() {
        m_Appearance.setTransparencyAttributes(null);
    }

    protected String getName() {
        return "Transparency";
    }

    protected String[] getMenuItemNames() {
        return new String[] { "-", "Transparency", "-", "0", "0point2",
                "0point5", "0point8", "1", "-", "Mode", "-", "NONE", "FASTEST",
                "NICEST", "SCREEN_DOOR", "BLENDED" };
    }

    private TransparencyAttributes getTransparencyAttributes() {
        return (TransparencyAttributes) m_NodeComponent;
    }

    public void on0() {
        getTransparencyAttributes().setTransparency(0);
    }

    public void on0point2() {
        getTransparencyAttributes().setTransparency(0.2f);
    }

    public void on0point5() {
        getTransparencyAttributes().setTransparency(0.5f);
    }

    public void on0point8() {
        getTransparencyAttributes().setTransparency(0.8f);
    }

    public void on1() {
        getTransparencyAttributes().setTransparency(1);
    }

    public void onNONE() {
        getTransparencyAttributes().setTransparencyMode(
                TransparencyAttributes.NONE);
    }

    public void onFASTEST() {
        getTransparencyAttributes().setTransparencyMode(
                TransparencyAttributes.FASTEST);
    }

    public void onNICEST() {
        getTransparencyAttributes().setTransparencyMode(
                TransparencyAttributes.NICEST);
    }

    public void onSCREEN_DOOR() {
        getTransparencyAttributes().setTransparencyMode(
                TransparencyAttributes.SCREEN_DOOR);
    }

    public void onBLENDED() {
        getTransparencyAttributes().setTransparencyMode(
                TransparencyAttributes.BLENDED);
    }
}

abstract class AppearanceComponent implements ActionListener {
    protected NodeComponent m_NodeComponent = null;

    protected Appearance m_Appearance = null;

    public AppearanceComponent(Appearance app) {
        m_Appearance = app;

        m_NodeComponent = createComponent();

        int[] capsArray = getCapabilities();

        if (capsArray != null) {
            for (int n = 0; n < capsArray.length; n++)
                m_NodeComponent.setCapability(capsArray[n]);
        }

        setAppearanceCapability();
        assignToAppearance();
    }

    abstract protected int[] getCapabilities();

    abstract protected void setAppearanceCapability();

    abstract protected NodeComponent createComponent();

    abstract protected void assignToAppearance();

    abstract protected void assignNullToAppearance();

    abstract protected String getName();

    abstract protected String[] getMenuItemNames();

    public Menu createMenu() {
        String szName = getName();
        String[] itemArray = getMenuItemNames();
        ActionListener listener = this;

        Menu menu = new Menu(szName);

        MenuItem menuItem = new MenuItem("Null");
        menuItem.addActionListener(listener);
        menu.add(menuItem);

        menuItem = new MenuItem("Non_Null");
        menuItem.addActionListener(listener);
        menu.add(menuItem);

        for (int n = 0; n < itemArray.length; n++) {
            menuItem = new MenuItem(itemArray[n]);
            menuItem.addActionListener(listener);
            menu.add(menuItem);
        }

        return menu;
    }

    public void onNull() {
        assignNullToAppearance();
    }

    public void onNon_Null() {
        assignToAppearance();
    }

    public void actionPerformed(ActionEvent event) {
        // (primitive) menu command dispatch
        Class classObject = getClass();

        Method[] methodArray = classObject.getMethods();

        for (int n = methodArray.length - 1; n >= 0; n--) {
            if (("on" + event.getActionCommand()).equals(methodArray[n]
                    .getName())) {
                try {
                    methodArray[n].invoke(this, null);
                } catch (InvocationTargetException ie) {
                    System.err
                            .println("Warning. Menu handler threw exception: "
                                    + ie.getTargetException());
                } catch (Exception e) {
                    System.err
                            .println("Warning. Menu dispatch exception: " + e);
                }

                return;
            }
        }
    }
}

class ColoringComponent extends AppearanceComponent {
    public ColoringComponent(Appearance app) {
        super(app);
    }

    protected int[] getCapabilities() {
        return new int[] { ColoringAttributes.ALLOW_COLOR_WRITE,
                ColoringAttributes.ALLOW_SHADE_MODEL_WRITE };
    }

    protected NodeComponent createComponent() {
        return (NodeComponent) new ColoringAttributes();
    }

    protected void setAppearanceCapability() {
        m_Appearance.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
    }

    protected void assignToAppearance() {
        m_Appearance
                .setColoringAttributes((ColoringAttributes) m_NodeComponent);
    }

    protected void assignNullToAppearance() {
        m_Appearance.setColoringAttributes(null);
    }

    protected String getName() {
        return "Coloring";
    }

    protected String[] getMenuItemNames() {
        return new String[] { "-", "Color", "-", "Red", "Green", "Blue", "-",
                "Shade Model", "-", "Fastest", "Nicest", "Flat", "Gouraud" };
    }

    private ColoringAttributes getColoringAttributes() {
        return (ColoringAttributes) m_NodeComponent;
    }

    public void onRed() {
        getColoringAttributes().setColor(1, 0, 0);
    }

    public void onGreen() {
        getColoringAttributes().setColor(0, 1, 0);
    }

    public void onBlue() {
        getColoringAttributes().setColor(0, 0, 1);
    }

    public void onFastest() {
        getColoringAttributes().setShadeModel(ColoringAttributes.FASTEST);
    }

    public void onNicest() {
        getColoringAttributes().setShadeModel(ColoringAttributes.NICEST);
    }

    public void onFlat() {
        getColoringAttributes().setShadeModel(ColoringAttributes.SHADE_FLAT);
    }

    public void onGouraud() {
        getColoringAttributes().setShadeModel(ColoringAttributes.SHADE_GOURAUD);
    }
}
