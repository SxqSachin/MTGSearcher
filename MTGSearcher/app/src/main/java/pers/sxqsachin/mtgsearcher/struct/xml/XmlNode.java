package pers.sxqsachin.mtgsearcher.struct.xml;

/**
 *
 * XmlNode
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class XmlNode {

    private String  mTag;
    private String  mText;

    public XmlNode() {
        this("", "");
    }

    public XmlNode(String tag, String text) {
        mTag = tag;
        mText = text;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }
}
