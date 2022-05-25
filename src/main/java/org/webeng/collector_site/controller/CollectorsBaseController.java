package org.webeng.collector_site.controller;

import org.webeng.collector_site.data.dao.CollectorsDataLayer;
import org.webeng.framework.controller.AbstractBaseController;
import org.webeng.framework.data.DataLayer;

import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.sql.SQLException;

public abstract class CollectorsBaseController extends AbstractBaseController {

    @Override
    protected DataLayer createDataLayer(DataSource ds) throws ServletException {
        try {
            return new CollectorsDataLayer(ds);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

}
