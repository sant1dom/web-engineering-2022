package org.webeng.collector_site.controller;

import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.framework.data.DataException;
import org.webeng.framework.result.DataModelFiller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giuse
 */
public class DummyModelFiller implements DataModelFiller {

    @Override
    public void fillDataModel(Map datamodel, HttpServletRequest request, ServletContext context) {
        //datamodel.put("current_timestamp", Calendar.getInstance().getTime());
        try {
            datamodel.put("latest_collection", ((CollectorsDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioni());
        } catch (DataException ex) {
            Logger.getLogger(DummyModelFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
