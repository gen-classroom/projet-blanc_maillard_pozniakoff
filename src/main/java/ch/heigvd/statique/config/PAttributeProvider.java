package ch.heigvd.statique.config;

import org.commonmark.node.Image;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

import java.util.Map;

class PAttributeProvider implements AttributeProvider {
    @Override
    public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
        if (node instanceof Image) {
            attributes.put("style", "width:150px;height:200px;position:relative;left:50%;margin-left:-100px;");
        }
        if(node instanceof Link){
            attributes.put("target", "_blank");
        }
    }
}
