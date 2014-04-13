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
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.platform.ui.web.api.NavigationContext;
import org.nuxeo.ecm.platform.ui.web.component.list.UIEditableList;

@Name("complexSelectorBean")
@Scope(ScopeType.EVENT)
public class ComplexSelectorBeanBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(ComplexSelectorBeanBean.class);

    @In(create = true, required = false)
    protected transient CoreSession documentManager;

    @In(create = true)
    protected NavigationContext navigationContext;

    @In(create = true, required = false)
    protected transient FacesMessages facesMessages;

    protected Object value;


    protected void cleanUpList(UIEditableList listComponent) {
        //listComponent.resetValue();

        /*
        while (listComponent.getRowCount()>0) {
            //listComponent.removeValue(0);
            listComponent.getEditableModel().removeValue(0);
            //listComponent.getEditableModel().recordValueModified(index, newValue)
        }*/

        /*
        for (int i = listComponent.getRowCount()-1; i >=0; i-- ) {
            listComponent.removeValue(i);
        }*/
    }

    public void valueChanged(ValueChangeEvent evt) {

        value = evt.getNewValue();
        String sourceId = evt.getComponent().getId();

        UIEditableList listComponent = findEditableList(evt.getComponent().getParent());

        cleanUpList(listComponent);

        List<Map<String, Serializable>> newEntries = getValues(value.toString());

        for (Map<String, Serializable> newEntry : newEntries) {
            listComponent.addValue(newEntry);
        }
    }

    protected List<Map<String, Serializable>> getValues(String key) {
        List<Map<String, Serializable>> result = new ArrayList<>();
        for (int i = 0; i <3; i++) {
            Map<String, Serializable> entry = new HashMap<String, Serializable>();
            entry.put("entryLabel", "Label " + key + " " + i);
            entry.put("entryName", "Name " + key + " " + i);
            entry.put("entryTyp", "Type " + key + " " + i);
            result.add(entry);
        }
        return result;
    }

    protected UIEditableList findEditableList(UIComponent component) {

        if (component.getClass().equals(UIEditableList.class)) {
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

    protected void dumpTree(String prefix, UIComponent component) {
        log.error(prefix + component.getId() + "[" + component.getClass().getSimpleName() + "]" );
        for (UIComponent child : component.getChildren()) {
            dumpTree(prefix + ":" + component.getId(), child);
        }
    }

    public String getTS() {
        if (value!=null) {
            return "" + System.currentTimeMillis() + "--" + value.toString();
        } else {
            return "" + System.currentTimeMillis();
        }
    }

}
