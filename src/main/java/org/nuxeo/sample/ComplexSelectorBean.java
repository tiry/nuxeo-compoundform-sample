/*
 * (C) Copyright ${year} Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     <a href="mailto:tdelprat@nuxeo.com">Tiry</a>
 */

package org.nuxeo.sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.renderkit.RendererUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.directory.Session;
import org.nuxeo.ecm.directory.api.DirectoryService;
import org.nuxeo.ecm.platform.ui.web.component.list.UIEditableList;
import org.nuxeo.runtime.api.Framework;

@Name("complexSelectorBean")
@Scope(ScopeType.EVENT)
public class ComplexSelectorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String selectedValue;

    protected List<Map<String, Serializable>> getComplexValues(String key) throws Exception {

        DirectoryService ds = Framework.getLocalService(DirectoryService.class);

        List<Map<String, Serializable>> result = new ArrayList<>();

        Session session = ds.open("nasaDataSets");

        try {

            // add filter to query on the target JSON WebService based directory
            Map<String, Serializable> filter = new HashMap<>();
            filter.put("category", key);

            DocumentModelList items = session.query(filter);

            // since I did not update the studio project : do the mapping by hand
            for (DocumentModel item : items) {
                Map<String, Serializable> entry = new HashMap<String, Serializable>();
                entry.put("identifier", (String) item.getProperty("nasads", "id"));
                entry.put("protocol", (String) item.getProperty("nasads", "slug"));
                entry.put("domain", (String) item.getProperty("nasads", "url"));
                entry.put("registrantName", (String) item.getProperty("nasads", "category"));
                entry.put("description", (String) item.getProperty("nasads", "title"));
                result.add(entry);
            }

        } finally {
            session.close();
        }
        return result;
    }

    /*
     * ####################### everything below this line should be in the base class ###################################
     * ##################################################################################################################
     * ######### this code is just here as a workaround a classloader issue in nuxeo-ide hot-reload with Seam beans
     */

    /****** remove inheritance to avoid IDE problem */
    private static final Log log = LogFactory.getLog(ComplexSelectorBean.class);

    protected void cleanUpList(UIEditableList listComponent) {
        for (int i = listComponent.getRowCount()-1; i >=0; i-- ) {
            listComponent.removeValue(i);
        }
    }

    protected String selectedScope;

    public void setSelectedScope(String scope) {
        selectedScope =scope;
    }

    public void setSelectedValue(String value) throws Exception {

        log.error("scope=" + selectedScope);
        selectedValue = value;

        UIComponent root = FacesContext.getCurrentInstance().getViewRoot();

        UIComponent widgetContainer = RendererUtils.getInstance().findComponentFor(root, selectedScope);

        UIEditableList listComponent = findEditableList(widgetContainer);

        cleanUpList(listComponent);

        if (value!=null) {
            List<Map<String, Serializable>> newEntries = getComplexValues(value.toString());

            for (Map<String, Serializable> newEntry : newEntries) {
                listComponent.addValue(newEntry);
            }
        }

    }

    public String getSelectedValue() {
        return selectedValue;
    }

    protected UIEditableList findEditableList(UIComponent component) {

        if (component.getClass().equals(UIEditableList.class) ) {
            return (UIEditableList) component;
        }
        for (UIComponent child : component.getChildren()) {
            UIEditableList list = findEditableList(child);
            if (list!=null) {
                return list;
            }
        }
        return null;
    }

}
