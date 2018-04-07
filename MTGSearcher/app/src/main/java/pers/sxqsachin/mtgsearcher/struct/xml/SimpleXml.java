package pers.sxqsachin.mtgsearcher.struct.xml;

import java.util.Vector;

/**
 *
 * SimpleXml
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class SimpleXml implements Xml {

    private Vector<XmlNode>     mNodes;

    public SimpleXml() {
        mNodes = new Vector<>();
    }

    public void addNode(XmlNode node) {
        mNodes.add(node);
    }

    @Override
    public String getBody() {
        return null;
    }
}
