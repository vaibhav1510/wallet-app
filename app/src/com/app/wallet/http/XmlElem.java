package com.app.wallet.http;


import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class XmlElem {
    
    public static void main(String[] args) throws Exception {
        XmlElem xml = XmlElem.createFromFile("1.xml");
//        log(xml.ncdata$("checkout_form"));
    }
    
    public static XmlElem createFromFile(String path) throws Exception {
        String xmlStr = FileUtils.readFileToString(new File(path));
        return create(xmlStr);
    }
    
    public static XmlElem create(String xmlStr) {
        try {
            return new XmlElem(doc(xmlStr).getDocumentElement());
        } catch(Exception exp) {
    		throw new RuntimeException("testing");
//            throw asRtExp(exp);
        }
    }
    
    public static Document doc(String content) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(content)));
        return doc;
    }
    
    private Element root;
    private Element curr;

    public XmlElem(Element elem) {
        this.root = elem;
        this.curr = elem;
    }
    
    // number of element nodes under the current node 
    public int noOfChilds() {
        int childs = 0;
        NodeList nl = curr.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                ++childs;
            }
        }
        return childs;
    }
    
    private Element findChild(String name) {
        NodeList nl = curr.getChildNodes();
        Element child = null;
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE && name.equals(node.getNodeName())) {
                if(child != null) {
                    throw new RuntimeException("More the one node present with tag name [" + name + "]");
                } else {
                    child = (Element)node;
                }
            }
        }
        return child;
    }

    private XmlElem gotoRoot() {
        this.curr = this.root;
        return this;
    }
    
    private XmlElem gotoNode(String name) {
        Element child = findChild(name);
        if(child == null) { // no such node
            return null;
        }
        this.curr = child;
        return this;
    }
    
    // the curr element's tag name
    public String name() {
        return curr.getTagName();
    }
    
    public List<XmlElem> nlist(String ... nodes) {
        gotoRoot();
//        assert_(nodes.length > 0, "nodes cannot be empty");
        int lastIndex = nodes.length - 1;
        for (int i = 0;i < lastIndex; i++) {
            XmlElem elem = gotoNode(nodes[i]);
            if(elem == null && i < lastIndex) {
                throw new NoSuchElementException("No Such node [" + nodes[i] + "]");
            }
        }
        return childList(nodes[lastIndex]);
    }

    private List<XmlElem> childList(String name) {
        NodeList nl = curr.getChildNodes();
         List<XmlElem> toRet = new ArrayList<XmlElem>();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE && name.equals(node.getNodeName())) {
                toRet.add(new XmlElem((Element)node));
            }
        }
        return toRet;
    }
    
    public XmlElem n(String ... nodes) {
        gotoRoot();
//        assert_(nodes.length > 0, "nodes cannot be empty");
        int lastIndex = nodes.length - 1;
        for (int i = 0;i < lastIndex; i++) {
            XmlElem elem = gotoNode(nodes[i]);
            if(elem == null && i < lastIndex) {
                throw new NoSuchElementException("No Such node [" + nodes[i] + "]");
            }
        }
        return child(nodes[lastIndex]);
    }
    
    private XmlElem child(String name) {
        Element child = findChild(name);
        return child == null ? null : new XmlElem(child);
    }
    
    public XmlElem n$(String ... nodes) {
        gotoRoot();
        if(nodes.length == 0) { // empty
            return this;
        }
        int lastIndex = nodes.length - 1;
        for (int i = 0;i < lastIndex ; i++) {
            XmlElem elem = gotoNode(nodes[i]);
            if(elem == null && i < lastIndex) {
                throw new NoSuchElementException("No Such node [" + nodes[i] + "]");
            }
        }
        return gotoNode(nodes[lastIndex]);
    }
    
    public String ntext$(String ... nodes) {
        XmlElem e = n$(nodes);
        return e == null/*node not found*/ ? null : e.text(); 
    }
    
    public String ncdata$(String ... nodes) {
        XmlElem e = n$(nodes);
        return e == null/*node not found*/ ? null : e.cdata(); 
    }
    
    public Integer nint$(String ... nodes) {
        String text = ntext$(nodes);
        return text == null ? null : Integer.valueOf(text);
    }
    
    public boolean nbool$(String ... nodes) {
        String text = ntext$(nodes);
        return text == null ? false : Boolean.valueOf(text);
    }
    
    public String attr(String name) {
        return curr.getAttribute(name);
    }
    
    // text value for the element
    public String text() {
        NodeList nl = curr.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if(node.getNodeType() == Node.TEXT_NODE) {
                String val = node.getNodeValue();
                return val == null || val.isEmpty() ? null : val;
            }
        }
        return null;
    }
    
    public String cdata() {
        NodeList nl = curr.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if(node.getNodeType() == Node.CDATA_SECTION_NODE) {
                String val = node.getNodeValue();
                return val == null || val.isEmpty() ? null : val;
            }
        }
        return null;
    }
    
    /*public boolean bool() {
        String text = text();
        return text == null ? false : Boolean.valueOf(text);
    }
    
    public Integer integer() {
        String text = text();
        return text == null ? null : Integer.valueOf(text);
    }*/
    
    @Override
    public String toString() {
        try {
            return new XMLBuilder(root, null).elementAsString(_outProps);
        } catch(Exception exp) {
    		throw new RuntimeException("testing");
//            throw asRtExp(exp);
        }
    }

    private static Properties _outProps = null;
    public static Properties outProps() {
        if(_outProps != null) {
            return _outProps;
        }
        _outProps = new Properties();
        _outProps.put(OutputKeys.METHOD, "xml");
        _outProps.put(OutputKeys.INDENT, "yes");
        _outProps.put("{http://xml.apache.org/xslt}indent-amount", "2");
        return _outProps;
    }
}
