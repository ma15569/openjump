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
package com.vividsolutions.jump.workbench.model;

import org.locationtech.jts.util.Assert;
import com.vividsolutions.jump.util.LangUtil;

/**
 * Default implementation of the Layerable interface.
 * 
 * @see Layerable
 */
public abstract class AbstractLayerable implements Layerable, LayerManagerProxy {

  private LayerManager layerManager;
  private String name;
  private boolean visible = true;
  private boolean editable = false;
  private boolean selectable = true;
  private boolean readonly = false;
  private boolean scaleDependentRenderingEnabled = false;

  // When working with scale, the max is less than the min. [Jon Aquino
  // 2005-03-01]
  private Double minScale = null;
  private Double maxScale = null;

  /**
   * Called by Java2XML
   */
  public AbstractLayerable() {
  }

  public AbstractLayerable(String name, LayerManager layerManager) {
    Assert.isTrue(name != null);
    Assert.isTrue(layerManager != null);
    // We got side effects with Layer#setLayerManager(layerManager) :
    // a layerListener is added to the layerManager, then replaced it is
    // replaced by a new layerListener during Layer initialization, and
    // can never be removed.
    //setLayerManager(layerManager);
    this.layerManager = layerManager;

    // Can't fire events because this Layerable hasn't been added to the
    // LayerManager yet. [Jon Aquino]
    boolean firingEvents = layerManager.isFiringEvents();
    layerManager.setFiringEvents(false);

    try {
      setName(layerManager.uniqueLayerName(name));
    } finally {
      layerManager.setFiringEvents(firingEvents);
    }
  }

  @Override
  public void setLayerManager(LayerManager layerManager) {
    this.layerManager = layerManager;
  }

  @Override
  public LayerManager getLayerManager() {
    return layerManager;
  }

  public void fireLayerChanged(LayerEventType type) {
    if (getLayerManager() == null) {
      // layerManager is null when Java2XML creates the object. [Jon
      // Aquino]
      return;
    }

    getLayerManager().fireLayerChanged(this, type);
  }

  /**
   * The only time #fireAppearanceChanged must be called is when a party
   * modifies an attribute of one of the Styles, because Styles don't notify
   * their layer when they change. But if a party adds or removes a feature, or
   * applies an EditTransaction to a feature, #fireAppearanceChanged will be
   * called automatically. This event will be ignored if
   * LayerManager#isFiringEvents is false
   */
  public void fireAppearanceChanged() {
    fireLayerChanged(LayerEventType.APPEARANCE_CHANGED);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
    fireLayerChanged(LayerEventType.METADATA_CHANGED);
  }

  public Task getTask() {
    if (layerManager != null) {
      return layerManager.getTask();
    } else {
      return null;
    }
  }

  // <<TODO:REFACTORING>> Move Visible to LayerSelection, since it should be a
  // property of the view, not the model [Jon Aquino]
  // isVisible has been there from the beginning and I can't see a better place [mmichaud]
  @Override
  public boolean isVisible() {
    return visible;
  }

  @Override
  public void setVisible(boolean visible) {
    if (this.visible == visible) {
      return;
    }
    this.visible = visible;
    fireLayerChanged(LayerEventType.VISIBILITY_CHANGED);
  }

  /**
   * Editability is not enforced; all parties are responsible for heeding this
   * flag.
   */
  @Override
  public void setEditable(boolean editable) {
    if (this.editable == editable) {
      return;
    }

    this.editable = editable;
    fireLayerChanged(LayerEventType.METADATA_CHANGED);
  }

  @Override
  public boolean isEditable() {
    return editable;
  }

  /**
   * @return true if this layer should always be 'readonly' I.e.: The layer
   *         should never have the editable field set to true.
   */
  @Override
  public boolean isReadonly() {
    return readonly;
  }

  /**
   * Set whether this layer can be made editable.
   */
  @Override
  public void setReadonly(boolean value) {
    readonly = value;
  }

  /**
   * @return true if features in this layer can be selected.
   */
  @Override
  public boolean isSelectable() {
    return selectable;
  }

  /**
   * Set whether or not features in this layer can be selected.
   * 
   * @param value true if features in this layer can be selected
   */
  @Override
  public void setSelectable(boolean value) {
    selectable = value;
  }

  @Override
  public String toString() {
    return getName();
  }

  @Override
  public Double getMaxScale() {
    return maxScale;
  }

  @Override
  public Layerable setMaxScale(Double maxScale) {
    if (LangUtil.bothNullOrEqual(this.maxScale, maxScale)) {
      return this;
    }
    this.maxScale = maxScale;
    fireAppearanceChanged();
    return this;
  }

  @Override
  public Double getMinScale() {
    return minScale;
  }

  @Override
  public Layerable setMinScale(Double minScale) {
    if (LangUtil.bothNullOrEqual(this.minScale, minScale)) {
      return this;
    }
    this.minScale = minScale;
    fireAppearanceChanged();
    return this;
  }

  @Override
  public boolean isScaleDependentRenderingEnabled() {
    return scaleDependentRenderingEnabled;
  }

  @Override
  public Layerable setScaleDependentRenderingEnabled(
      boolean scaleDependentRenderingEnabled) {
    if (this.scaleDependentRenderingEnabled == scaleDependentRenderingEnabled) {
      return this;
    }
    this.scaleDependentRenderingEnabled = scaleDependentRenderingEnabled;
    fireAppearanceChanged();
    return this;
  }

}