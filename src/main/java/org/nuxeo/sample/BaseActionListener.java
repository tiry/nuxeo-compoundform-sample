package org.nuxeo.sample;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.platform.ui.web.component.list.UIEditableList;

public abstract class BaseActionListener {

    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(ComplexSelectorBeanBean.class);

    public BaseActionListener() {
        super();
    }

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

        Object value = evt.getNewValue();

        UIEditableList listComponent = findEditableList(evt.getComponent().getParent());

        cleanUpList(listComponent);

        if (value!=null) {
            List<Map<String, Serializable>> newEntries = getComplexValues(value.toString());

            for (Map<String, Serializable> newEntry : newEntries) {
                listComponent.addValue(newEntry);
            }
        }
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

    protected abstract List<Map<String, Serializable>> getComplexValues(String key);
}