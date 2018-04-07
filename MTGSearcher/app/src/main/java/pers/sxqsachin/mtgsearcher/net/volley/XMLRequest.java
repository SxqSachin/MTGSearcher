package pers.sxqsachin.mtgsearcher.net.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 *
 * XMLRequest
 *
 * Created by songxinqi-sachin on 16-6-4.
 */
public class XMLRequest extends Request<XmlPullParser> {

    private final Response.Listener<XmlPullParser>  mListener;
    private String  mCharsetName;

    public XMLRequest(String url, Response.Listener<XmlPullParser> listener, Response.ErrorListener errListener) {
        this(Method.GET, url, listener, errListener);
    }

    public XMLRequest(int method, String url, Response.Listener<XmlPullParser> listener, Response.ErrorListener errListener) {
        this(method, url, "UTF-8", listener, errListener);
    }

    public XMLRequest(int method, String url, String charsetName, Response.Listener<XmlPullParser> listener, Response.ErrorListener errListener) {
        super(method, url, errListener);

        mListener = listener;
        mCharsetName = charsetName;
    }

    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
        try {
            String xmlString = new String(response.data, mCharsetName);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlString));
            return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (XmlPullParserException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(XmlPullParser response) {
        mListener.onResponse(response);
    }
}
