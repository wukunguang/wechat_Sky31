package util;

import beans.MessageBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wukunguang on 15-8-5.
 */
public class MessageUntil {


    protected static String PREFIX_CDATA    = "<![CDATA[";
    protected static String SUFFIX_CDATA    = "]]>";
    private final static String Appid= "wxc5d217408956f8ea";
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {

        Map<String,String> map = new HashMap<String,String>();


        SAXReader saxReader = new SAXReader();
        InputStream ins = request.getInputStream();
        Document document = saxReader.read(ins);
        Element root = document.getRootElement();
        List<Element> list = root.elements();

        for (Element e : list){

            map.put(e.getName(),e.getText());

        }

        ins.close();

        return map;
    }


    public static String messageToXml(MessageBean messageBean){




        XStream xStream = initXStream(true);


        xStream.alias("xml", MessageBean.class);



        return xStream.toXML(messageBean);
    }




    /**
     * 初始化XStream
     * 可支持某一字段可以加入CDATA标签
     * 如果需要某一字段使用原文
     * 就需要在String类型的text的头加上"<![CDATA["和结尾处加上"]]>"标签，
     * 以供XStream输出时进行识别
     * @param isAddCDATA 是否支持CDATA标签
     * @return
     */
    public static XStream initXStream(boolean isAddCDATA){
        XStream xstream = null;
        if(isAddCDATA){
            xstream =  new XStream(
                    new XppDriver() {
                        public HierarchicalStreamWriter createWriter(Writer out) {
                            return new PrettyPrintWriter(out) {
                                protected void writeText(QuickWriter writer, String text) {
                                    if(text.startsWith(PREFIX_CDATA)
                                            && text.endsWith(SUFFIX_CDATA)) {
                                        writer.write(text);
                                    }else{
                                        super.writeText(writer, text);
                                    }
                                }
                            };
                        };
                    }
            );
        }else{
            xstream = new XStream();
        }
        return xstream;
    }


    public static String reWriteURL(String wechatURL , int mid){
        String myurl = null;
        try {
            myurl = URLEncoder.encode(StringUtil.MYURL+"?mid="+mid+"&massageResult=","UTF-8");    //转码，把URL转码为UTF-8格式
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String url = wechatURL.replace("APPID", Appid).replace("REDIRECT_URI", myurl).
                replace("SCOPE", "snsapi_base").replace("STATE","123");




        return url;
    }


}
