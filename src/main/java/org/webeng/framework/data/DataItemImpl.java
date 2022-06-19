package org.webeng.framework.data;

/**
 *
 * @author giuse
 * @param <KT> the key type
 */
public class DataItemImpl<KT> implements DataItem<KT> {

    private KT key;
    private long version;

    public DataItemImpl() {
        version = 1;
    }

    @Override
    public KT getKey() {
        return key;
    }

    @Override
    public void setKey(KT key) {
        this.key = key;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public void setVersion(long version) {
        this.version = version;
    }
}
