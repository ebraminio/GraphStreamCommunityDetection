package pdgs.utils;

import java.util.Iterator;
import java.util.Random;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

/**
 * This class provides styling methods for several graph elements and graph 
 * Viewers.
 * To use it, create a UIToolbox object, providing a Graph object (where the 
 * styling will be applied) as a parameter.
 * @author Ilias Trichopoulos <itrichop@csd.auth.gr>
 * @author Anastasis Andronidis <anastasis90@yahoo.gr>
 */
public class UIToolbox {
    
    private final SpriteManager sm;
    
    public UIToolbox(Graph graph) {
        this.sm = new SpriteManager(graph);
    }
    
    /**
     * Method to add styling attributes (ui.label, ui.style) to a node
     * @param n
     */
    public static void StyleNode(Node n) {
        n.setAttribute("ui.label", n.getIndex());
        n.setAttribute("ui.style", "size:20px;");
    }
    
    /**
     * Add a sprite in the graph Viewer window. Default X coordinate: 20.
     * @param spriteName the text that will be displayed as name of the sprite. Also, the id for the specific Sprite.
     * @param spriteValue the value (Number) next to the name of the sprite.
     * @param spritePosition the Y coordinate of the sprite. Default X position is 20. 
     */
    public void addSprite(String spriteName, Number spriteValue, int spritePosition) {
        Sprite spr = this.sm.addSprite(spriteName);
        spr.setPosition(StyleConstants.Units.PX, 20, spritePosition, 0);
        spr.setAttribute("ui.label",
                String.format(spriteName+": %s", spriteValue));
        spr.setAttribute("ui.style", "size: 0px; text-color: rgb(150,100,100); text-size: 20;");
    }
    
    /**
     * Remove a Sprite from the graph Viewer window.
     * @param spriteName the id of the sprite.
     */
    public void removeSprite(String spriteName) {
        this.sm.removeSprite(spriteName);
    }
    
    public static void ColorCommunities(Graph graph, Integer[] ids) {
        // Used for colors.
        Random color = new Random();

        int fixedColor = color.nextInt(255);
        for (Integer id : ids) {
            graph.getNode(id).setAttribute("visited", 1);
            graph.getNode(id).addAttribute("ui.style", "fill-color: rgb(" + fixedColor + "," + fixedColor + "," + fixedColor + "); size: 20px;");
        }

        for (Node n : graph.getEachNode()) {
            if (!n.hasAttribute("visited")) {
                int r = color.nextInt(255);
                int g = color.nextInt(255);
                int b = color.nextInt(255);

                n.setAttribute("visited", 1);
                n.addAttribute("ui.style", "fill-color: rgb(" + r + "," + g + "," + b + "); size: 20px;");
                Iterator<Node> breadth = n.getBreadthFirstIterator();
                while (breadth.hasNext()) {
                    Node next = breadth.next();
                    if (!next.hasAttribute("visited")) {
                        next.setAttribute("visited", 1);
                        next.addAttribute("ui.style", "fill-color: rgb(" + r + "," + g + "," + b + "); size: 20px;");
                    }
                }
            }
        }
    }
}