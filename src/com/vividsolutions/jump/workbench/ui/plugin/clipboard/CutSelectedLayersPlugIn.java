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

import javax.swing.ImageIcon;

import com.vividsolutions.jump.util.StringUtil;
import com.vividsolutions.jump.workbench.WorkbenchContext;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.MacroPlugIn;
import com.vividsolutions.jump.workbench.plugin.MultiEnableCheck;
import com.vividsolutions.jump.workbench.plugin.PlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.images.IconLoader;
import com.vividsolutions.jump.workbench.ui.plugin.RemoveSelectedLayersPlugIn;


public class CutSelectedLayersPlugIn extends MacroPlugIn {
    public CutSelectedLayersPlugIn() {
        super(new PlugIn[] {
                new CopySelectedLayersPlugIn(), new RemoveSelectedLayersPlugIn()
            });
    }

    public void initialize(PlugInContext context) throws Exception {
      super.initialize(context);
    }

    public static final ImageIcon ICON = IconLoader.icon("famfam/page_white_get.png");
    
    public String getNameWithMnemonic() {
        return StringUtil.replace(getName(), "t", "&t", false);
    }    

    public String getName() {
        return AbstractPlugIn.createName(getClass());
    }

    public MultiEnableCheck createEnableCheck(WorkbenchContext workbenchContext) {
        return new MultiEnableCheck().add(((CopySelectedLayersPlugIn) plugIns[0]).createEnableCheck(
                workbenchContext)).add(((RemoveSelectedLayersPlugIn) plugIns[1]).createEnableCheck(
                workbenchContext));
    }
}
