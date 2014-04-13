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

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("complexSelectorBean")
@Scope(ScopeType.EVENT)
public class ComplexSelectorBeanBean extends BaseActionListener implements Serializable {

    private static final long serialVersionUID = 1L;

    protected List<Map<String, Serializable>> getComplexValues(String key) {
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
}
