//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.08.19 at 02:43:00 PM CEST 
//


package test.com.tyrcho.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for streetType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="streetType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="rue"/>
 *     &lt;enumeration value="avenue"/>
 *     &lt;enumeration value="boulevard"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "streetType")
@XmlEnum
public enum StreetType {

    @XmlEnumValue("rue")
    RUE("rue"),
    @XmlEnumValue("avenue")
    AVENUE("avenue"),
    @XmlEnumValue("boulevard")
    BOULEVARD("boulevard");
    private final String value;

    StreetType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StreetType fromValue(String v) {
        for (StreetType c: StreetType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}