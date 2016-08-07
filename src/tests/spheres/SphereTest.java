package tests.spheres;

import com.microcrowd.loader.java3d.max3ds.Loader3DS;
import com.sun.j3d.loaders.Loader;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;


public class SphereTest {


    public SphereTest() {

    // Create the universe

    SimpleUniverse universe = new SimpleUniverse();

    // Create a structure to contain objects

    BranchGroup group = new BranchGroup();

    // Set up colors

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

//        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
//
//        Canvas3D c = new Canvas3D(config);
//
//        c.setBackground(Color.CYAN);

    // Create a ball to demonstrate textures

    int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

    //Sphere sphere = new Sphere(0.5f, primflags, ap);

        Loader loader1 = new Loader3DS();
        Scene s = null;

        try {
            s = loader1.load("models/Sphere.3ds");
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }



    group.addChild(s.getSceneGroup());

    // Create lights

    Color3f light1Color = new Color3f(1f, 1f, 1f);

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

    Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);

    DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);

    light1.setInfluencingBounds(bounds);

    group.addChild(light1);

    AmbientLight ambientLight = new AmbientLight(new Color3f(.5f,.5f,.5f));

    ambientLight.setInfluencingBounds(bounds);

    group.addChild(ambientLight);

    // look towards the ball

    universe.getViewingPlatform().setNominalViewingTransform();

    // add the group of objects to the Universe

    universe.addBranchGraph(group);

}

    public static void main(String[] args) {

        new SphereTest();

    }
}

