package org.webeng.framework.data;

/**
 *
 * @author giuse
 */
public interface DataItemProxy {

    boolean isModified();

    void setModified(boolean dirty);

}
