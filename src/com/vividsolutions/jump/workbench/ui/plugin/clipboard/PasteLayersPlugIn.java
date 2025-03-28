/*
 * The Unified Mapping Platform (JUMP) is an extensible, interactive GUI
 * for visualizing and manipulating spatial features with geometry and attributes.
 *
 * Copyright (C) 2003 Vivid Solutions
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * For more information, contact:
 *
 * Vivid Solutions
 * Suite #1A
 * 2328 Government Street
 * Victoria BC  V8T 5G5
 * Canada
 *
 * (250)385-6040
 * www.vividsolutions.com
 */
package com.vividsolutions.jump.workbench.ui.plugin.clipboard;

import com.vividsolutions.jump.I18N;
import com.vividsolutions.jump.util.StringUtil;
import com.vividsolutions.jump.workbench.WorkbenchContext;
import com.vividsolutions.jump.workbench.model.Category;
import com.vividsolutions.jump.workbench.model.Layerable;
import com.vividsolutions.jump.workbench.plugin.EnableCheck;
import com.vividsolutions.jump.workbench.plugin.EnableCheckFactory;
import com.vividsolutions.jump.workbench.plugin.MultiEnableCheck;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.GUIUtil;
import com.vividsolutions.jump.workbench.ui.images.IconLoader;

import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.JComponent;

/**
* 
* Lets user paste layers from the clipboard.
* 
*/

public class PasteLayersPlugIn extends LayerableClipboardPlugIn {
    
    public static final String MUST_NOT_BE_EMPTY = I18N.getInstance().get("ui.plugin.PasteLayersPlugIn.clipboard-must-not-be-empty");
    public static final String MUST_BE_LAYERS = I18N.getInstance().get("ui.plugin.PasteLayersPlugIn.clipboard-must-contain-layers");
  
    //Note: Need to copy the data twice: once when the user hits Copy, so she is
    //free to modify the original afterwards, and again when the user hits Paste,
    //so she is free to modify the first copy then hit Paste again. [Jon Aquino]
    public PasteLayersPlugIn() {
    }
    
    public String getNameWithMnemonic() {
        return StringUtil.replace(getName(), "P", "&P", false);
    }    

    public boolean execute(PlugInContext context) throws Exception {
        Transferable transferable =
            GUIUtil.getContents(Toolkit.getDefaultToolkit().getSystemClipboard());

        if (!transferable
            .isDataFlavorSupported(
                CollectionOfLayerablesTransferable.COLLECTION_OF_LAYERABLES_FLAVOR)) {
            return false;
        }

        Collection layerables =
            (Collection) transferable.getTransferData(
                CollectionOfLayerablesTransferable.COLLECTION_OF_LAYERABLES_FLAVOR);
        //Cache selected category because selection will change (to layer) after adding first layer
        //if no other layers exist. [Jon Aquino]
        Category selectedCategory =
            ((Category) context.getLayerNamePanel().getSelectedCategories().iterator().next());
        for (Iterator i = layerables.iterator(); i.hasNext();) {
            Layerable layerable = (Layerable) i.next();
            Layerable clone = cloneLayerable(layerable);
            clone.setLayerManager(context.getLayerManager());            
            context.getLayerManager().addLayerable(selectedCategory.getName(), clone);
            clone.setName(context.getLayerManager().uniqueLayerName(clone.getName()));            
        }

        return true;
    }

    public MultiEnableCheck createEnableCheck(WorkbenchContext workbenchContext) {
        EnableCheckFactory checkFactory = EnableCheckFactory.getInstance(workbenchContext);

        return new MultiEnableCheck()
            .add(checkFactory.createWindowWithLayerNamePanelMustBeActiveCheck())
            .add(checkFactory.createExactlyNCategoriesMustBeSelectedCheck(1))
            .add(new EnableCheck() {
                public String check(JComponent component) {
                    Transferable transferable = GUIUtil.getContents(
                        Toolkit.getDefaultToolkit().getSystemClipboard());
                    if (transferable == null) {
                        return MUST_NOT_BE_EMPTY;
                    }
                    if (!transferable.isDataFlavorSupported(
                                CollectionOfLayerablesTransferable.COLLECTION_OF_LAYERABLES_FLAVOR)) {
                        return MUST_BE_LAYERS;
                    }
                    return null;
                }
            });
    }
    
    public Icon getIcon(){
      return IconLoader.icon("famfam/page_white_put.png");
    }
}
