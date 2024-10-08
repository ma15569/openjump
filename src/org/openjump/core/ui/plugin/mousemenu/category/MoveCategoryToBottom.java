/*
 * Created on 04.05.2005 for PIROL
 *
 * CVS header information:
 *  $RCSfile: MoveCategoryToBottom.java,v $
 *  $Revision: 1.2 $
 *  $Date: 2005/09/13 08:45:58 $
 *  $Source: D:/CVS/cvsrepo/pirolPlugIns/plugIns/CategoryTools/MoveCategoryToBottom.java,v $
 */
package org.openjump.core.ui.plugin.mousemenu.category;

import java.util.Collection;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;

import com.vividsolutions.jump.I18N;
import com.vividsolutions.jump.workbench.WorkbenchContext;
import com.vividsolutions.jump.workbench.model.Category;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.EnableCheckFactory;
import com.vividsolutions.jump.workbench.plugin.MultiEnableCheck;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.GUIUtil;
import com.vividsolutions.jump.workbench.ui.images.IconLoader;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

/**
 * 
 * PlugIn class that adds the option to move a selected category to the bottom position 
 * in the layer name panel to the category context menu. 
 *
 * @author Ole Rahn
 * @author FH Osnabr&uuml;ck - University of Applied Sciences Osnabr&uuml;ck,
 * Project: PIROL (2005),
 * Subproject: Daten- und Wissensmanagement
 * 
 */
public class MoveCategoryToBottom extends AbstractPlugIn {

    public boolean execute(PlugInContext context) throws Exception {
        CategoryMover cm = new CategoryMover(context);
        
        Collection cats = context.getLayerNamePanel().getSelectedCategories();
        
        if (cats.size() > 1 || cats.size() <= 0){
        	String s = I18N.getInstance().get("org.openjump.core.ui.plugin.mousemenu.category.MoveCategoryOneDown.Only-a-single-category-can-be-moved");
            context.getWorkbenchFrame().warnUser(s);
            return false;
        }
        
        Object[] catsArray = cats.toArray();
        
        cm.moveCategoryToBottom((Category)catsArray[0]);
        
        return true;
    }
    
    public Icon getIcon() {
        return IconLoader.icon("bullet_arrow_bottom.png");
    }
    
    public String getName(){
    	return 	I18N.getInstance().get("org.openjump.core.ui.plugin.mousemenu.category.MoveCategoryToBottom.Move-Category-To-Bottom");
    }
    
    public void initialize(PlugInContext context) throws Exception {
        super.initialize(context);
        
        JPopupMenu layerNamePopupMenu = context.getWorkbenchContext().getWorkbench().getFrame().getCategoryPopupMenu();
        FeatureInstaller featInst = context.getFeatureInstaller();
        
        featInst.addPopupMenuItem(layerNamePopupMenu,
                this, this.getName() + "...", false,
                GUIUtil.toSmallIcon((ImageIcon) this.getIcon()),
                MoveCategoryToBottom.createEnableCheck(context.getWorkbenchContext()));
    }
    
    
    
    public static MultiEnableCheck createEnableCheck(final WorkbenchContext workbenchContext) {

    	EnableCheckFactory checkFactory = EnableCheckFactory.getInstance(workbenchContext);
        MultiEnableCheck multiEnableCheck = new MultiEnableCheck();
        
        multiEnableCheck.add( checkFactory.createAtLeastNCategoriesMustBeSelectedCheck(1) );
        multiEnableCheck.add( checkFactory.createExactlyNCategoriesMustBeSelectedCheck(1) );
        
        return multiEnableCheck;
	}


}
